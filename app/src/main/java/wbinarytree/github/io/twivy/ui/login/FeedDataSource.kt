package wbinarytree.github.io.twivy.ui.login

import android.arch.paging.PageKeyedDataSource
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Tweet

class FeedDataSource : PageKeyedDataSource<Long, Tweet>() {

    private val apiClient by lazy { TwitterCore.getInstance().apiClient }

    override fun loadInitial(params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Tweet>) {
        val response = apiClient.statusesService.homeTimeline(
            20,
            null,
            null,
            null,
            null,
            null,
            null
        ).execute()

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Tweet>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Tweet>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
