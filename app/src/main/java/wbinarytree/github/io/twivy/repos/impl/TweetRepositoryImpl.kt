package wbinarytree.github.io.twivy.repos.impl

import android.arch.paging.PagedList
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import wbinarytree.github.io.twivy.repos.TweetRepository
import wbinarytree.github.io.twivy.utils.toNonNullList

object TweetRepositoryImpl : TweetRepository {
    override fun getPagingList(): Observable<PagedList<Tweet>> {
        return Observable.empty()
    }

    private val apiClient by lazy { TwitterCore.getInstance().apiClient }
    override fun getRecentTimeline(count: Int): Observable<List<Tweet>> {
        return Observable.fromCallable {
            val response = apiClient.statusesService.homeTimeline(
                count,
                null,
                null,
                null,
                null,
                null,
                null
            ).execute()
            response.body().toNonNullList()
        }
            .subscribeOn(Schedulers.io())
    }

    override fun getNextPage(id: Long, count: Int): Observable<List<Tweet>> {
        return Observable.fromCallable {
            val response = apiClient.statusesService.homeTimeline(
                count,
                null,
                id,
                null,
                null,
                null,
                null
            ).execute()
            response.body().toNonNullList()
        }
            .subscribeOn(Schedulers.io())
    }

}