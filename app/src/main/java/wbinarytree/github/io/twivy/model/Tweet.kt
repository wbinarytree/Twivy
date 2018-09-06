package wbinarytree.github.io.twivy.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class TweetDB(
    @PrimaryKey
    val id: Long,
    val createdAt: String,
    val text: String,
    @Embedded(prefix = "user_")
    val user: User?
)