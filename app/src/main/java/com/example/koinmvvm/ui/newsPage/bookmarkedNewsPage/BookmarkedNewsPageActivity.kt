package com.example.koinmvvm.ui.newsPage.bookmarkedNewsPage

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.databinding.ActivityBookmarkedNewsPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkedNewsPageActivity :
    BaseActivity<ActivityBookmarkedNewsPageBinding, BookmarkedNewsPageViewModel>() {

    private val model: BookmarkedNewsPageViewModel by viewModel()

    override fun fullscreenActivity(): Boolean = false

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.themeStatusBarColor

    override fun setContentView(): Int = R.layout.activity_bookmarked_news_page

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): BookmarkedNewsPageViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = this@BookmarkedNewsPageActivity

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.failureMessage.removeObservers(this@BookmarkedNewsPageActivity)
        model.successMessage.removeObservers(this@BookmarkedNewsPageActivity)
        model.warningMessage.removeObservers(this@BookmarkedNewsPageActivity)
    }

    private fun performViewModelVariableBinding() {
        model.bookmarkedNewsPageActivity = this@BookmarkedNewsPageActivity
        model.initialization()
    }

    private fun setListeners() {
        model.failureMessage.observe(this@BookmarkedNewsPageActivity, {
            onFailure(it)
        })

        model.successMessage.observe(this@BookmarkedNewsPageActivity, {
            onSuccess(it)
        })

        model.warningMessage.observe(this@BookmarkedNewsPageActivity, {
            onWarning(it)
        })
    }
}