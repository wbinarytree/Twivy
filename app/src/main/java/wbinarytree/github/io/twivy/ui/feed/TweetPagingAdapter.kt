package wbinarytree.github.io.twivy.ui.feed

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wbinarytree.github.kotlinutilsrecyclerview.InnerHolder
import wbinarytree.github.io.twivy.R
import wbinarytree.github.io.twivy.model.TweetDB

class TweetPagingAdapter(
    inline val binder: InnerHolder.(TweetDB) -> Unit) : PagedListAdapter<TweetDB, InnerHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(containter: ViewGroup, position: Int): InnerHolder {
        val context = containter.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_tweet, containter, false)
        return InnerHolder(view)

    }

    override fun onBindViewHolder(p0: InnerHolder, p1: Int) {
        val tweetDB = getItem(p1)
        if (tweetDB != null) {
            binder.invoke(p0, tweetDB)
        }
    }

//    on

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<TweetDB>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(old: TweetDB, new: TweetDB): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: TweetDB, new: TweetDB): Boolean =
                old == new
        }
    }
}