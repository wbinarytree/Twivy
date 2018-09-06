package wbinarytree.github.io.twivy.ui.feed

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.wbinarytree.github.kotlinutilsrecyclerview.GenericAdapter
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.item_tweet.*
import wbinarytree.github.io.twivy.R
import wbinarytree.github.io.twivy.glide.GlideApp
import wbinarytree.github.io.twivy.model.TweetDB
import wbinarytree.github.io.twivy.ui.base.BaseUiActivity
import wbinarytree.github.io.twivy.ui.base.getViewModel

class FeedActivity : BaseUiActivity<Action, FeedUiModel, FeedTranslator>() {
    override fun render(ui: FeedUiModel) {
        when (ui) {
            is FeedUiModel.Loading          -> {
//                refresh.isRefreshing = ui.isLoading
            }

            is FeedUiModel.TweetResult      -> {
                bindTweets(ui.tweets)
            }
            is FeedUiModel.TweetPagedResult -> {
                bindPagedTweets(ui.tweets)
            }
        }
    }

    private fun bindPagedTweets(tweets: PagedList<TweetDB>) {
        val adapter = TweetPagingAdapter { tweet ->
            GlideApp.with(im_photo)
                .load(tweet.user?.profileImageUrlHttps)
                .centerCrop()
                .into(im_photo)
            tv_screen_name.text = tweet.user?.screenName
            tv_tweet_text.text = tweet.text
        }
        adapter.submitList(tweets)
        rv_feeds.adapter = adapter

    }

    private fun bindTweets(tweets: List<TweetDB>) {
        val adapter = GenericAdapter(R.layout.item_tweet, tweets) { tweet, _ ->
            GlideApp.with(im_photo)
                .load(tweet.user?.profileImageUrlHttps)
                .centerCrop()
                .into(im_photo)
            tv_screen_name.text = tweet.user?.screenName
            tv_tweet_text.text = tweet.text
        }
        rv_feeds.adapter = adapter
    }

    override fun translator(): FeedTranslator {
        return getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        rv_feeds.layoutManager = LinearLayoutManager(this)
        Action.Init.send()
    }
}
