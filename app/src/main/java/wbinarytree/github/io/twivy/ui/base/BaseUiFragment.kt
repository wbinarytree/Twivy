package wbinarytree.github.io.twivy.ui.base

import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

/**
 * Created by yaoda on 1/30/18.
 */
abstract class BaseUiFragment<A, R, T : BaseTranslator<A, R>> : BaseFragment() {

    protected lateinit var translator: T
    protected val actions: PublishSubject<A> = PublishSubject.create()
    protected val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        translator = translator()
        translator.sendAction(actions).addTo(disposables)
        translator.getUiModel()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                render(it)
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    abstract fun render(ui: R)

    abstract fun translator(): T

    fun A.send() {
        actions.onNext(this)
    }
}