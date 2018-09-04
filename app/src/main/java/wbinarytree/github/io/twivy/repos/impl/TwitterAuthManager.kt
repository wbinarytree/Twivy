package wbinarytree.github.io.twivy.repos.impl

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.internal.TwitterApi
import com.twitter.sdk.android.core.internal.oauth.OAuth1aService
import com.twitter.sdk.android.core.internal.oauth.OAuthResponse
import io.reactivex.Observable
import wbinarytree.github.io.twivy.repos.AuthManager

object TwitterAuthManager : AuthManager {
    private val service by lazy {
        OAuth1aService(TwitterCore.getInstance(), TwitterApi())
    }


    override fun oauthLogin(): Observable<String> {
        return Observable.create<TwitterAuthToken> { emitter ->
            service.requestTempToken(object : Callback<OAuthResponse>() {
                override fun success(result: Result<OAuthResponse>?) {
                    val authToken = result?.data?.authToken
                    if (authToken != null) {
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

}