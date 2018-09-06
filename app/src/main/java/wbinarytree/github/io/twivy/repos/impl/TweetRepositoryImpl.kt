package wbinarytree.github.io.twivy.repos.impl

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import wbinarytree.github.io.twivy.database.TwivyDatabase
import wbinarytree.github.io.twivy.model.TweetDB
import wbinarytree.github.io.twivy.model.toDb
import wbinarytree.github.io.twivy.repos.TweetRepository
import wbinarytree.github.io.twivy.repos.TweetRepository.Companion.DEFAULT_PAGING
import wbinarytree.github.io.twivy.utils.toNonNullList
import java.text.SimpleDateFormat
import java.util.*

object TweetRepositoryImpl : TweetRepository {
    override fun refreshPage(): Observable<Boolean> {
        return Observable.fromCallable {
            database.tweetDao().deleteAll()
            true
        }
    }

    private val database = TwivyDatabase.database
    private val disposables = CompositeDisposable()
    private var factory: DataSource.Factory<Int, TweetDB>? = null
    override fun getPagingList(): Observable<PagedList<TweetDB>> {
        val callback = object : PagedList.BoundaryCallback<TweetDB>() {
            override fun onZeroItemsLoaded() {
                getInitTweets()
            }

            override fun onItemAtFrontLoaded(itemAtFront: TweetDB) {
                //don't load anything
            }

            override fun onItemAtEndLoaded(itemAtEnd: TweetDB) {
                getNextPage(itemAtEnd)
            }
        }
        val factory = database.tweetDao().getTweets()
        this.factory = factory
        return RxPagedListBuilder<Int, TweetDB>(
            factory, DEFAULT_PAGING
        )
            .setBoundaryCallback(callback)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .doOnDispose {
                disposables.clear()
            }
    }

    private fun getInitTweets() {
        getRecentTimeline()
            .subscribe({
                Log.d("get", it.toString())
            }, {
                it.printStackTrace()
            })
            .addTo(disposables)
    }

    private fun getNextPage(tweetDB: TweetDB) {
        getNextPage(tweetDB.id)
            .subscribe({
                Log.d("get", it.toString())
            }, {
                it.printStackTrace()
            })
            .addTo(disposables)
    }

    private val apiClient by lazy { TwitterCore.getInstance().apiClient }
    override fun getRecentTimeline(count: Int): Observable<List<TweetDB>> {
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
            .map { tweet -> tweet.map { it.toDb() } }
            .doOnNext { database.tweetDao().insert(*it.toTypedArray()) }
            .subscribeOn(Schedulers.io())
    }

    override fun getNextPage(id: Long, count: Int): Observable<List<TweetDB>> {
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
            .map { tweet -> tweet.map { it.toDb() } }
            .doOnNext { database.tweetDao().insert(*it.toTypedArray()) }
            .subscribeOn(Schedulers.io())
    }

    val DATE_TIME_RFC822 = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH)

}