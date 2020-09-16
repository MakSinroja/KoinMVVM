package com.example.koinmvvm.ui.newsPage.bookmarkedNewsPage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.koinmvvm.R
import com.example.koinmvvm.constants.ARTICLE_TITLE
import com.example.koinmvvm.constants.URL
import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.databinding.AdapterNewsPageBinding
import com.example.koinmvvm.extensions.loadArticleImage
import com.example.koinmvvm.extensions.shareContent
import com.example.koinmvvm.listeners.NewsArticleListeners
import com.example.koinmvvm.ui.newsPage.newsWebView.NewsWebViewActivity
import com.google.android.material.card.MaterialCardView
import org.jetbrains.anko.startActivity

class BookmarkedNewsPageAdapter(private val context: Context) : PagerAdapter() {

    var articleList = mutableListOf<ArticlesEntity>()

    var newsArticleListeners: NewsArticleListeners? = null

    override fun getCount(): Int = articleList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as MaterialCardView
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
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
                "Publish on - ${article.publishAt}"

            articleTitle.setOnClickListener {
                (context as BookmarkedNewsPageActivity).startActivity<NewsWebViewActivity>(
                    ARTICLE_TITLE to article.title,
                    URL to article.url
                )
            }

            bookmarkImage.setImageResource(R.drawable.ic_vector_bookmark)

            bookmarkImage.setOnClickListener {
                newsArticleListeners?.isFavouriteArticle(null, article, true)
            }

            shareNews.setOnClickListener {
                context.shareContent(article.url)
            }

            if ((articleList.size - 1) == position) {
                nextArticleLayout.visibility = View.GONE
            } else {
                nextArticleLayout.visibility = View.VISIBLE
                nextArticleTitle.text = articleList[position + 1].title

                nextArticleLayout.setOnClickListener {
                    newsArticleListeners?.showNextArticleStory(position + 1)
                }
            }
        }

        container.addView(adapterNewsPagerBinding.root)

        return adapterNewsPagerBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as MaterialCardView)
    }
}