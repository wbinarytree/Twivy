package wbinarytree.github.io.twivy.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


/**
 * Created by yaoda on 1/30/18.
 */
@SuppressLint("Registered")
abstract class BaseUiActivity<A, R, T : BaseTranslator<A, R>> : BaseActivity() {

    protected lateinit var translator: T
    //    private val actions: Subject<A> = PublishSubject.create<A>().toSerialized()
    private val actions: Subject<A> = PublishSubject.create()
    protected val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translator = translator()
        translator.sendAction(actions).addTo(disposables)
        translator.getUiModel()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                render(it)
            }
            .addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    abstract fun render(ui: R)

    abstract fun translator(): T


    fun A.send() {
        actions.onNext(this)
    }
}