package wbinarytree.github.io.twivy.repos

import io.reactivex.Observable

interface AuthManager {
    fun oauthLogin(): Observable<String>
}