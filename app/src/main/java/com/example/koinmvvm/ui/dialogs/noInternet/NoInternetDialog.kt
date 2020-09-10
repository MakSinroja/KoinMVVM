package com.example.koinmvvm.ui.dialogs.noInternet

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseBottomSheetDialogFragment
import com.example.koinmvvm.databinding.DialogNoInternetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoInternetDialog :
    BaseBottomSheetDialogFragment<DialogNoInternetBinding, NoInternetDialogViewModel>() {

    companion object {
        var TAG = NoInternetDialog::class.java.toString()
        var noInternetDialogInstance: NoInternetDialog? = null

        fun getInstance(): NoInternetDialog {
            if (noInternetDialogInstance == null) {
                noInternetDialogInstance = NoInternetDialog()
            }
            return noInternetDialogInstance as NoInternetDialog
        }
    }

    private val model: NoInternetDialogViewModel by viewModel()

    override fun setContentView(): Int = R.layout.dialog_no_internet

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): NoInternetDialogViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
    }

    private fun performViewModelVariableBinding() {
        model.noInternetDialog = this@NoInternetDialog
        model.initialization()
    }

    private fun setListeners() {

    }
}