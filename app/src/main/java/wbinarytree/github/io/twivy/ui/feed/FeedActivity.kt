package wbinarytree.github.io.twivy.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import kotlinx.android.synthetic.main.activity_feed.*
import wbinarytree.github.io.twivy.R
import wbinarytree.github.io.twivy.ui.base.BaseUiActivity
import wbinarytree.github.io.twivy.ui.base.getViewModel

class FeedActivity : BaseUiActivity<Action, FeedUiModel, FeedTranslator>() {
    override fun render(ui: FeedUiModel) {
        when (ui) {
            is FeedUiModel.Loading     -> {
                refresh.isRefreshing = ui.isLoading
            }

            is FeedUiModel.TweetResult -> {
                bindTweets(ui.tweets)
            }
        }
    }

    private fun bindTweets(tweets: List<Tweet>) {
        val searchTimeline = UserTimeline.Builder()
            .maxItemsPerRequest(50)
            .build()

        val adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
            .setTimeline(searchTimeline)
            .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
            .build()
        rv_feeds.adapter = adapter
    }

    override fun translator(): FeedTranslator {
        return getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        rv_feeds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        Action.Init.send()
    }
}
