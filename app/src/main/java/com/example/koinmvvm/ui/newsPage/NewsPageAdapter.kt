package com.example.koinmvvm.ui.newsPage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.viewpager.widget.PagerAdapter
import com.example.koinmvvm.R
import com.example.koinmvvm.constants.ARTICLE_TITLE
import com.example.koinmvvm.constants.URL
import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.database.repositories.articles.ArticlesRepository
import com.example.koinmvvm.databinding.AdapterNewsPageBinding
import com.example.koinmvvm.extensions.loadArticleImage
import com.example.koinmvvm.listeners.NewsArticleListeners
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.ui.newsPage.newsWebView.NewsWebViewActivity
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateToString
import com.google.android.material.card.MaterialCardView
import org.jetbrains.anko.startActivity
import java.util.*

class NewsPageAdapter(private val context: Context) : PagerAdapter() {

    var articleList = mutableListOf<Articles>()

    var newsArticleListeners: NewsArticleListeners? = null

    lateinit var articlesRepository: ArticlesRepository

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
                "Publish on - ${convertDateToString(article.publishAt ?: Date())}"

            if ((articleList.size - 1) == position) {
                nextArticleLayout.visibility = View.GONE
            } else {
                nextArticleLayout.visibility = View.VISIBLE
                nextArticleTitle.text = articleList[position + 1].title

                nextArticleLayout.setOnClickListener {
                    newsArticleListeners?.let { listener ->
                        listener.showNextArticleStory(position + 1)
                    }
                }
            }

            articleTitle.setOnClickListener {
                (context as NewsPageActivity).startActivity<NewsWebViewActivity>(
                    ARTICLE_TITLE to article.title,
                    URL to article.url
                )
            }

            val articlesEntity: LiveData<ArticlesEntity?> =
                articlesRepository.isBookmarkArticle(article.title)

            articlesEntity.observe((context as NewsPageActivity), { entity ->

                bookmarkImage.setImageResource(R.drawable.ic_vector_not_bookmark)

                if (entity != null)
                    bookmarkImage.setImageResource(R.drawable.ic_vector_bookmark)

                bookmarkImage.setOnClickListener(null)

                bookmarkImage.setOnClickListener {
                    newsArticleListeners?.let { listener ->
                        listener.isFavouriteArticle(article, entity, entity != null)
                    }
                }
            })
        }

        container.addView(adapterNewsPagerBinding.root)

        return adapterNewsPagerBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as MaterialCardView)
    }
}