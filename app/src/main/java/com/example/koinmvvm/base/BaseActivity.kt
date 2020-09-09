package com.example.koinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.R
import com.example.koinmvvm.constants.THEME_DARK
import com.example.koinmvvm.constants.THEME_LIGHT
import com.example.koinmvvm.extensions.*
import com.example.koinmvvm.listeners.BaseFragmentListener
import com.example.koinmvvm.listeners.SnackBarMessagesListeners
import com.example.koinmvvm.preferences.CommonPreferences
import org.koin.android.ext.android.get

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    BaseFragmentListener, SnackBarMessagesListeners {

    var commonPreferences: CommonPreferences = get()

    abstract fun fullscreenActivity(): Boolean
    abstract fun transparentActivity(): Boolean
    abstract fun changeStatusBarColor(): Int
    abstract fun setContentView(): Int
    abstract fun setBindingVariable(): Int
    abstract fun setViewModel(): V
    abstract fun setLifeCycleOwner(): LifecycleOwner
    abstract fun initialization()

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}

    private var snackBarMessagesListeners: SnackBarMessagesListeners? = null

    private lateinit var mViewModelBinding: T
    private var mViewModel: V? = null

    fun getViewModelDataBinding(): T = mViewModelBinding

    var selectedThemeStyle: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if (fullscreenActivity())
            this.setupFullScreenActivity()

        if (transparentActivity())
            this.setupTransparentActivity()

        this.setupStatusBarColor(changeStatusBarColor())

        super.onCreate(savedInstanceState)
        getSavedTheme()
        setTheme(selectedThemeStyle)

        performDataBinding()

        snackBarMessagesListeners = this
    }

    private fun performDataBinding() {
        mViewModelBinding = DataBindingUtil.setContentView(this, setContentView())
        this.mViewModel = if (mViewModel == null) setViewModel() else mViewModel
        mViewModelBinding.setVariable(setBindingVariable(), mViewModel)
        mViewModelBinding.lifecycleOwner = setLifeCycleOwner()
        this.initialization()
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBarMessagesListeners = null
    }

    fun getSavedTheme() {
        when (commonPreferences.themeId) {
            THEME_LIGHT -> selectedThemeStyle = R.style.AppTheme_Light
            THEME_DARK -> selectedThemeStyle = R.style.AppTheme_Dark
        }
    }

    override fun onFailure(message: String) {
        message.showFailureSnackBarView(window.decorView.findViewById(android.R.id.content))
    }

    override fun onSuccess(message: String) {
        message.showSuccessSnackBarView(window.decorView.findViewById(android.R.id.content))
    }

    override fun onWarning(message: String) {
        message.showWarningSnackBarView(window.decorView.findViewById(android.R.id.content))
    }
}