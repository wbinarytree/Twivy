package wbinarytree.github.io.twivy.ui.feed

import com.twitter.sdk.android.core.models.Tweet

sealed class Action {
    object Init : Action()
}

sealed class FeedUiModel() {
    class TweetResult(val tweets: List<Tweet>) : FeedUiModel()

    class Loading(val isLoading: Boolean) : FeedUiModel()

}
