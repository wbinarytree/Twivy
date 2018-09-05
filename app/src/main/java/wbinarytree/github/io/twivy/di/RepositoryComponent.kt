package wbinarytree.github.io.twivy.di

import dagger.Component
import wbinarytree.github.io.twivy.ui.feed.FeedTranslator
import wbinarytree.github.io.twivy.ui.login.LoginTranslator

/**
 * Created by yaoda on 1/30/18.
 */
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun inject(loginTranslator: LoginTranslator)

    fun inject(loginTranslator: FeedTranslator)

    interface Injectable {
        fun inject(component: RepositoryComponent)
    }
}