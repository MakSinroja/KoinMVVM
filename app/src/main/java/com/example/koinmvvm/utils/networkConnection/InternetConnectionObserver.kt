package com.example.koinmvvm.utils.networkConnection

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

@Suppress("DEPRECATION")
class InternetConnectionObserver constructor(val context: Context) : LiveData<Boolean>() {

    var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    private var connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var networkCallback: NetworkCallback

    init {
        networkCallback = NetworkCallback(this)
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                networkCallback
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val builder = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
            }
            else -> {
                context.registerReceiver(networkReceiver, intentFilter)
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            updateConnection()
        }
    }

    fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)
    }

    inner class NetworkCallback(private val observer: InternetConnectionObserver) :
        ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            observer.postValue(true)
        }

        override fun onLost(network: Network) {
            observer.postValue(false)
        }
    }
}