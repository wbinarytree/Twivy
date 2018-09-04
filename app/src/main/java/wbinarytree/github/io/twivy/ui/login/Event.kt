package wbinarytree.github.io.twivy.ui.login

sealed class Action {
    object Init : Action()

    object Login : Action()
}

sealed class LoginUiModel() {
    class LoginResult() : LoginUiModel()

    class OAuthResult(val url: String) : LoginUiModel()

    class Error(val e: Throwable?) : LoginUiModel()

    class Loading(val isLoading: Boolean) : LoginUiModel()
}