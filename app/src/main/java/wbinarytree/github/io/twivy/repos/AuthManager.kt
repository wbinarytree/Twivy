package wbinarytree.github.io.twivy.repos

import android.content.Intent
import io.reactivex.Observable

interface AuthManager {
    fun oauthLogin(): Observable<String>

    fun handleOAuthResult(data: Intent): Observable<String>
}