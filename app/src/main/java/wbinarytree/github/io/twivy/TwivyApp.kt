package wbinarytree.github.io.twivy

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import wbinarytree.github.io.twivy.di.DaggerRepositoryComponent
import wbinarytree.github.io.twivy.di.RepositoryComponent
import wbinarytree.github.io.twivy.di.RepositoryModule


class TwivyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        repoComponent = DaggerRepositoryComponent.builder().repositoryModule(RepositoryModule)
            .build()
        instance = this
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
            .debug(true)
            .build()
        Twitter.initialize(config)

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val customClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()

        val activeSession = TwitterCore.getInstance()
            .sessionManager.activeSession

        val customApiClient: TwitterApiClient
        if (activeSession != null) {
            customApiClient = TwitterApiClient(activeSession, customClient)
            TwitterCore.getInstance().addApiClient(activeSession, customApiClient)
        } else {
            customApiClient = TwitterApiClient(customClient)
            TwitterCore.getInstance().addGuestApiClient(customApiClient)
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: Application
        @JvmStatic
        lateinit var repoComponent:RepositoryComponent

        private const val CONSUMER_KEY = "ogc6F1YRRpBslfZRodww09gUM"
        private const val CONSUMER_SECRET = "OMrZUarL4fLGVYlQObyH3F0raQ7SGGzM9OyMrej0zV42Yn6z6k"
    }
}