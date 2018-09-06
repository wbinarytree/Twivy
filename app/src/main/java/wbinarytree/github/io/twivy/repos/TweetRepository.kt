package wbinarytree.github.io.twivy.repos

import android.arch.paging.PagedList
import io.reactivex.Observable
import wbinarytree.github.io.twivy.model.TweetDB

interface TweetRepository {
    companion object {
        const val DEFAULT_PAGING = 20
    }

    fun getRecentTimeline(count: Int = DEFAULT_PAGING): Observable<List<TweetDB>>

    fun getNextPage(id: Long, count: Int = DEFAULT_PAGING): Observable<List<TweetDB>>

    fun getPagingList(): Observable<PagedList<TweetDB>>
}