package com.example.koinmvvm.ui.dialogs.exitApp

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseBottomSheetDialogFragment
import com.example.koinmvvm.databinding.DialogExitAppBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExitAppDialog : BaseBottomSheetDialogFragment<DialogExitAppBinding, ExitAppViewModel>() {

    companion object {
        var TAG = ExitAppDialog::class.java.toString()
        var exitAppDialogInstance: ExitAppDialog? = null
        fun getInstance(): ExitAppDialog {
            if (exitAppDialogInstance == null) {
                exitAppDialogInstance = ExitAppDialog()
            }
            return exitAppDialogInstance as ExitAppDialog
        }
    }

    private val model: ExitAppViewModel by viewModel()

    override fun setContentView(): Int = R.layout.dialog_exit_app

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): ExitAppViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = this@ExitAppDialog

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
    }

    private fun performViewModelVariableBinding() {
        model.exitAppDialog = this@ExitAppDialog
        model.initialization()
    }

    private fun setListeners() {}
}