package wbinarytree.github.io.twivy.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import wbinarytree.github.io.twivy.TwivyApp
import wbinarytree.github.io.twivy.di.RepositoryComponent

/**
 * Created by yaoda on 1/30/18.
 */
abstract class BaseTranslator<R, U> : AndroidViewModel(TwivyApp.instance), RepositoryComponent.Injectable {
    val actions: Subject<R> = PublishSubject.create<R>()
//    abstract val result: Observable<U>

    val state: Observable<U> = actions.flatMap { source ->
        Observable.just(source)
            .publish { it.reduce() }
    }
        .share()
        .distinctUntilChanged()


    abstract fun Observable<R>.reduce(): Observable<U>

    open fun sendAction(action: Observable<R>):Disposable {
        // to make sure subject won't receive onError or onComplete from the action
        return action.subscribe(actions::onNext)
    }

    open fun getUiModel(): Observable<U> {
        return state
    }

    companion object Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val t = super.create(modelClass)
            if (t is RepositoryComponent.Injectable) {
                t.inject(TwivyApp.repoComponent)
            }
            return t
        }

    }
}


inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(): VM {
    return ViewModelProviders.of(this, BaseTranslator).get(VM::class.java)
}

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(id: String): VM {
    return ViewModelProviders.of(this, BaseTranslator).get(id, VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.getViewModel(id: String): VM {
    return ViewModelProviders.of(activity!!, BaseTranslator).get(id, VM::class.java)
}
