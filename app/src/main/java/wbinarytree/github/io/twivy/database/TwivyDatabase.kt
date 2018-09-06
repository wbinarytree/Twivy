package wbinarytree.github.io.twivy.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import wbinarytree.github.io.twivy.TwivyApp
import wbinarytree.github.io.twivy.database.converter.DateTypeConverter
import wbinarytree.github.io.twivy.database.daos.TweetDao
import wbinarytree.github.io.twivy.model.TweetDB

@Database(
    entities = [TweetDB::class],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class TwivyDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao

    companion object {

        private const val DATABASE_NAME = "TwivyDatabase"

        @JvmStatic
        val database by lazy {
            Room.databaseBuilder(
                TwivyApp.instance,
                TwivyDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }
    }
}