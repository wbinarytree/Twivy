package wbinarytree.github.io.twivy.repos.impl

import android.content.Intent
import android.os.Bundle
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.internal.TwitterApi
import com.twitter.sdk.android.core.internal.network.UrlUtils
import com.twitter.sdk.android.core.internal.oauth.OAuth1aService
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants
import com.twitter.sdk.android.core.internal.oauth.OAuthResponse
import io.reactivex.Observable
import wbinarytree.github.io.twivy.repos.AuthManager

object TwitterAuthManager : AuthManager {

    private var cachedRequestToken: TwitterAuthToken? = null
    override fun handleOAuthResult(data: Intent): Observable<String> {
        return Observable.create<OAuthResponse> { emitter ->
            val params = UrlUtils.getQueryParams(data.data.query, false)
            val bundle = Bundle(params.size)
            for ((key, value) in params) {
                bundle.putString(key, value)
            }
            val verifier = bundle.getString(OAuthConstants.PARAM_VERIFIER)
            if (verifier != null) {
                // Step 3. Convert the request token to an access token.
                service.requestAccessToken(
                    object : Callback<OAuthResponse>() {
                        override fun success(result: Result<OAuthResponse>?) {
                            val response = result?.data
                            if (response != null) {
                                emitter.onNext(response)
                                emitter.onComplete()
                            }
                        }

                        override fun failure(exception: TwitterException?) {
                            exception?.printStackTrace()
                            if (exception != null) {
                                emitter.tryOnError(exception)
                            } else {
                                emitter.tryOnError(UnknownError())
                            }
                        }
                    },
                    cachedRequestToken, verifier
                )
            }
        }
            .map {
                val token = it.authToken.token
                val tokenSecret = it.authToken.secret
                val userId = it.userId
                val screenName = it.userName
                TwitterSession(TwitterAuthToken(token, tokenSecret), userId, screenName)
            }
            .doOnNext { sessionManager.activeSession = it }
            .map { it.toString() }
    }

    private val service by lazy {
        OAuth1aService(TwitterCore.getInstance(), TwitterApi())
    }

    private val sessionManager by lazy { TwitterCore.getInstance().sessionManager }


    override fun oauthLogin(): Observable<String> {
        return Observable.create<TwitterAuthToken> { emitter ->
            service.requestTempToken(object : Callback<OAuthResponse>() {
                override fun success(result: Result<OAuthResponse>?) {
                    val authToken = result?.data?.authToken
                    if (authToken != null) {
                        cachedRequestToken = authToken

                        emitter.onNext(authToken)
                        emitter.onComplete()
                    }
                }

                override fun failure(exception: TwitterException?) {
                    exception?.printStackTrace()
                    if (exception != null) {
                        emitter.tryOnError(exception)
                    } else {
                        emitter.tryOnError(UnknownError())
                    }
                }

            })
        }
            .map { token -> service.getAuthorizeUrl(token) }
    }

    private const val EXTRA_TOKEN = "tk"
    private const val EXTRA_TOKEN_SECRET = "ts"
    private const val EXTRA_SCREEN_NAME = "screen_name"
    private const val EXTRA_USER_ID = "user_id"
    private const val EXTRA_AUTH_ERROR = "auth_error"

}