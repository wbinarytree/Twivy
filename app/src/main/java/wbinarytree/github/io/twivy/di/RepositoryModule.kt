package wbinarytree.github.io.twivy.di

import dagger.Module
import dagger.Provides
import wbinarytree.github.io.twivy.repos.AuthManager
import wbinarytree.github.io.twivy.repos.TweetRepository
import wbinarytree.github.io.twivy.repos.impl.TweetRepositoryImpl
import wbinarytree.github.io.twivy.repos.impl.TwitterAuthManager

/**
 * Created by yaoda on 1/30/18.
 */
@Module
object RepositoryModule {

    @Provides
    fun provideAuthManager(): AuthManager = TwitterAuthManager

    @Provides
    fun provideTweetRepository(): TweetRepository = TweetRepositoryImpl
}