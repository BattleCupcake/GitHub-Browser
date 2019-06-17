package com.githubbrowser.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.githubbrowser.utils.Events
import com.githubbrowser.R
import com.githubbrowser.database.User
import com.githubbrowser.extensions.loadRoundedImage
import com.squareup.picasso.Picasso

class UsersAdapter(items: List<User>) : BaseRecyclerAdapter<UsersAdapter.RepositoryHolder, User>(items) {

    private var mListener: RepeatButtonClickListener? = null

    abstract class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view)

    class RepositoryItemHolder(val view: View) : RepositoryHolder(view) {
        private val textView: TextView = view.findViewById(R.id.user_login)
        private val userAvatarView: ImageView = view.findViewById(R.id.user_avatar)

        fun bind(user: User) {
            textView.text = user.login
            Picasso.get().loadRoundedImage(user.avatarUrl, userAvatarView, R.drawable.ic_user)
        }
    }

    class RepositoryLoadingHolder(val view: View) : RepositoryHolder(view)

    class RepositoryErrorHolder(val view: View) : RepositoryHolder(view) {
        private val repeatButton = view.findViewById<ImageButton>(R.id.repeat_button)

        fun setRepeatButtonClickListener(listener: RepeatButtonClickListener?) {
            repeatButton.setOnClickListener {
                listener?.onRepeatButtonClick()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).type == Events.LOADING.name) {
            return Events.LOADING.ordinal
        }
        if (getItem(position).type == Events.ERROR.name) {
            return Events.ERROR.ordinal
        }

        return Events.CONTENT.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        when (viewType) {
            Events.LOADING.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                return RepositoryLoadingHolder(view)
            }
            Events.ERROR.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_error, parent, false)
                return RepositoryErrorHolder(view)
            }
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        return RepositoryItemHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (getItemViewType(position)) {
            Events.CONTENT.ordinal -> {
                val user = getItem(position)
                (holder as RepositoryItemHolder).bind(user)
            }
            Events.ERROR.ordinal -> {
                (holder as RepositoryErrorHolder).setRepeatButtonClickListener(mListener)
            }
        }
    }

    fun isLastItemUser(): Boolean {
        val user = getLastItem()
        return user?.type != Events.LOADING.name && user?.type != Events.ERROR.name
    }

    fun setOnRepeatButtonClickListener(listener: RepeatButtonClickListener) {
        mListener = listener
    }

    interface RepeatButtonClickListener {
        fun onRepeatButtonClick()
    }
}