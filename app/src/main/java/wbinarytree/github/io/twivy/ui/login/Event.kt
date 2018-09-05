package wbinarytree.github.io.twivy.ui.login

import android.content.Intent

sealed class Action {
    object Init : Action()

    object Login : Action()

    class OAuthResultAction(val result: Intent) : Action()
}

sealed class LoginUiModel() {
    class LoginResult() : LoginUiModel()

    class OAuthResult(val url: String) : LoginUiModel()

    class Error(val e: Throwable? = null) : LoginUiModel()

    class Loading(val isLoading: Boolean) : LoginUiModel()
}