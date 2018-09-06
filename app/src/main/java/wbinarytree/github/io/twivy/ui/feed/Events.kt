package wbinarytree.github.io.twivy.ui.feed

import android.arch.paging.PagedList
import wbinarytree.github.io.twivy.model.TweetDB

sealed class Action {
    object Init : Action()

    object Refresh:Action()
}

sealed class FeedUiModel() {
    class TweetResult(val tweets: List<TweetDB>) : FeedUiModel()

    class TweetPagedResult(val tweets: PagedList<TweetDB>) : FeedUiModel()

    class Loading(val isLoading: Boolean) : FeedUiModel()

}
