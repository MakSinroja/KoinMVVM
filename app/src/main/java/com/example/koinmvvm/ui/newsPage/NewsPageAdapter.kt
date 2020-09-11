package com.example.koinmvvm.ui.newsPage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.koinmvvm.databinding.AdapterNewsPageBinding
import com.example.koinmvvm.extensions.loadArticleImage
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateToString
import com.google.android.material.card.MaterialCardView
import java.util.*

class NewsPageAdapter(private val context: Context) : PagerAdapter() {

    var articleList = mutableListOf<Articles>()

    override fun getCount(): Int = articleList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as MaterialCardView
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val adapterNewsPagerBinding: AdapterNewsPageBinding = AdapterNewsPageBinding.inflate(
            LayoutInflater.from(context), container, false
        )

        adapterNewsPagerBinding.apply {

            val article = articleList[position]

            articleImage.loadArticleImage(context, article.urlToImage)

            articleTitle.text = article.title

            if (article.description.isNullOrEmpty())
                articleDescription.visibility = View.GONE
            else {
                articleDescription.visibility = View.VISIBLE
                articleDescription.text = article.description
            }

            if (article.content.isNullOrEmpty())
                articleContent.visibility = View.GONE
            else {
                articleContent.visibility = View.VISIBLE
                articleContent.text = article.content
            }

            articlePublishOn.text =
                "Publish on - ${convertDateToString(article.publishAt ?: Date())}"
        }

        container.addView(adapterNewsPagerBinding.root)

        return adapterNewsPagerBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as MaterialCardView)
    }
}