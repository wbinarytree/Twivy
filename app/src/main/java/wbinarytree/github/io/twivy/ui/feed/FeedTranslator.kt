package wbinarytree.github.io.twivy.ui.feed

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.rxkotlin.ofType
import wbinarytree.github.io.twivy.di.RepositoryComponent
import wbinarytree.github.io.twivy.repos.TweetRepository
import wbinarytree.github.io.twivy.ui.base.BaseTranslator
import javax.inject.Inject

class FeedTranslator : BaseTranslator<Action, FeedUiModel>() {
    @Inject
    lateinit var tweetRepo: TweetRepository

    override fun Observable<Action>.reduce(): Observable<FeedUiModel> {
        return Observable.mergeArray(
            ofType<Action.Init>().init()
        )
    }

    override fun inject(component: RepositoryComponent) {
        component.inject(this)
    }

    private fun Observable<Action.Init>.init(): ObservableSource<FeedUiModel> {
        return flatMap { _ ->
            tweetRepo.getRecentTimeline()
                .map<FeedUiModel> { FeedUiModel.TweetResult(it) }
                .startWith(FeedUiModel.Loading(true))
                .concatWith(Observable.just(FeedUiModel.Loading(false)))
        }

    }
}