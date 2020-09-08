package com.example.koinmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.preferences.CommonPreferences

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    var mainBaseActivity: BaseActivity<T, V>? = null

    lateinit var commonPreferences: CommonPreferences

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
            mainBaseActivity = context as BaseActivity<T, V>
            mainBaseActivity?.onFragmentAttached()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainBaseActivity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBaseActivity?.apply {
            getSavedTheme()
            setTheme(selectedThemeStyle)
        }

        activity?.let {
            commonPreferences = CommonPreferences(it.applicationContext)
        }
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

    fun getBaseActivity(): BaseActivity<T, V>? = mainBaseActivity
}