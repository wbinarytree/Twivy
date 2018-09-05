package wbinarytree.github.io.twivy.repos

import android.arch.paging.PagedList
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable

interface TweetRepository {
    companion object {
        const val DEFAULT_PAGING = 20
    }

    fun getRecentTimeline(count: Int = DEFAULT_PAGING): Observable<List<Tweet>>

    fun getNextPage(id: Long, count: Int = DEFAULT_PAGING): Observable<List<Tweet>>

    fun getPagingList(): Observable<PagedList<Tweet>>
}