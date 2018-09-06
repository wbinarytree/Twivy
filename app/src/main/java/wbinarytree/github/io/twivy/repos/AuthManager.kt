package wbinarytree.github.io.twivy.repos

import android.content.Intent
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.Observable

interface AuthManager {
    fun oauthLogin(): Observable<String>

    fun handleOAuthResult(data: Intent): Observable<String>

    fun currentSession():Observable<TwitterSession>
}