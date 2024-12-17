package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.receiveStringFromNumber

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnDeleteListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener : OnShareListener,
    private val onDeleteListener: OnDeleteListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onDeleteListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onDeleteListener: OnDeleteListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            menu.setImageResource(R.drawable.baseline_more_vert_24)
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_resource_file)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.remove -> {
                                onDeleteListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            avatar.setImageResource(R.drawable.netology)
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likesButton.setImageResource(
                if (post.likedByMe) R.drawable.favorited_24 else R.drawable.baseline_favorite_border_24
            )
            likesCount.text = receiveStringFromNumber(post.likes)
            likesButton.setOnClickListener{
                onLikeListener(post)
            }

            sharesButton.setImageResource(R.drawable.baseline_share_24)
            sharesButton.setOnClickListener { onShareListener(post) }
            sharesCount.text = receiveStringFromNumber(post.shares)

            viewsIcon.setImageResource(R.drawable.eye_icon_24)
            viewsCount.text = receiveStringFromNumber(post.views)
        }
    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

