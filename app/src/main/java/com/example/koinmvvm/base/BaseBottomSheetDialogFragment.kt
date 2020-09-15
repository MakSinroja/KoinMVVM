package com.example.koinmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("UNCHECKED_CAST")
abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding, V : BaseViewModel> :
    BottomSheetDialogFragment() {

    var mainBaseActivity: BaseActivity<T, V>? = null

    abstract fun setContentView(): Int
    abstract fun setBindingVariable(): Int
    abstract fun setViewModel(): V
    abstract fun setLifeCycleOwner(): LifecycleOwner
    abstract fun initialization()

    private lateinit var rootView: View
    private lateinit var mViewModelDataBinding: T
    private var mViewModel: V? = null

    fun getViewModelDataBinding(): T = mViewModelDataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mainBaseActivity = context as BaseActivity<T, V>?
            mainBaseActivity?.onFragmentAttached()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainBaseActivity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModelDataBinding =
            DataBindingUtil.inflate(inflater, setContentView(), container, false)
        rootView = mViewModelDataBinding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mViewModel = if (mViewModel == null) setViewModel() else mViewModel
        mViewModelDataBinding.setVariable(setBindingVariable(), mViewModel)
        mViewModelDataBinding.lifecycleOwner = setLifeCycleOwner()
        mViewModelDataBinding.executePendingBindings()
        this.initialization()
    }
}