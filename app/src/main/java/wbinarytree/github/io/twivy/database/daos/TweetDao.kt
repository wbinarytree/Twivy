package wbinarytree.github.io.twivy.database.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import wbinarytree.github.io.twivy.model.TweetDB

@Dao
interface TweetDao : BaseDao<TweetDB> {


    @Query("SELECT * FROM tweetdb")
    fun getTweets(): DataSource.Factory<Int, TweetDB>
}