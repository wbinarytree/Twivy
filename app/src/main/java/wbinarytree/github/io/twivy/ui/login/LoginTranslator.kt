package wbinarytree.github.io.twivy.ui.login

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.rxkotlin.ofType
import wbinarytree.github.io.twivy.di.RepositoryComponent
import wbinarytree.github.io.twivy.repos.AuthManager
import wbinarytree.github.io.twivy.ui.base.BaseTranslator
import javax.inject.Inject

class LoginTranslator : BaseTranslator<Action, LoginUiModel>() {
    @Inject
    lateinit var authManager: AuthManager

    override fun Observable<Action>.reduce(): Observable<LoginUiModel> {
        return Observable.mergeArray(
            ofType<Action.Login>().login(),
            ofType<Action.Init>().init(),
            ofType<Action.OAuthResultAction>().handleOAuth()
        )
            .onErrorReturn { LoginUiModel.Error(it) }
    }

    override fun inject(component: RepositoryComponent) {
        component.inject(this)
    }

    private fun Observable<Action.Login>.login(): ObservableSource<LoginUiModel> {
        return flatMap {
            authManager.oauthLogin()
        }
            .map<LoginUiModel> { LoginUiModel.OAuthResult(it) }
            .startWith(LoginUiModel.Loading(true))
            .concatWith(Observable.just(LoginUiModel.Loading(false)))
    }

    private fun Observable<Action.Init>.init(): ObservableSource<LoginUiModel> {
        return flatMap { _ ->
            authManager.currentSession()
                .map<LoginUiModel> { LoginUiModel.LoginResult }
                .onErrorReturn { LoginUiModel.NeedLogin }
                .startWith(LoginUiModel.Loading(true))
                .concatWith(Observable.just(LoginUiModel.Loading(false)))
        }

    }

    private fun Observable<Action.OAuthResultAction>.handleOAuth(): ObservableSource<LoginUiModel> {
        return flatMap { action ->
            authManager.handleOAuthResult(action.result)
                .map<LoginUiModel> { LoginUiModel.LoginResult }
                .startWith(LoginUiModel.Loading(true))
                .concatWith(Observable.just(LoginUiModel.Loading(false)))
        }

    }
}


