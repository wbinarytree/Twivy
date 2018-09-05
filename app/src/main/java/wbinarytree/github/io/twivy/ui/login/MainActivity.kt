package wbinarytree.github.io.twivy.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import wbinarytree.github.io.twivy.R
import wbinarytree.github.io.twivy.ui.base.BaseUiActivity
import wbinarytree.github.io.twivy.ui.base.getViewModel

class MainActivity : BaseUiActivity<Action, LoginUiModel, LoginTranslator>() {
    override fun render(ui: LoginUiModel) {
        when (ui) {
            is LoginUiModel.OAuthResult -> {
                startOAuth(ui.url)
            }
            is LoginUiModel.Loading     -> {
                progress_circle.visibility = if (ui.isLoading) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }
        }
    }

    private fun startOAuth(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun translator(): LoginTranslator {
        return getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Action.Init.send()
        btn_login.setOnClickListener {
            Action.Login.send()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            Action.OAuthResultAction(intent).send()
//            TwitterCore.getInstance().sessionManager
//            val code = intent.data.getQueryParameter("code")
//            val error: String? = intent.data.getQueryParameter("error")
//            checkCallback(intent)
        }
    }
}
