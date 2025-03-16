package ru.netology.nmedia.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post


interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
}


class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("QueryPermissionsNeeded")
    fun bind(post: Post) {
        binding.apply {
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_resource_file)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.delete -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
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

            if (post.video != null) {
                video.visibility = View.VISIBLE
                video.setImageResource(R.drawable.metalfamily)
                playButton.visibility = View.VISIBLE
                val videoUri = Uri.parse(post.video)
                video.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, videoUri)
                    if (intent.resolveActivity(itemView.context.packageManager) != null) {
                        itemView.context.startActivity(intent)
                    } else {
                        Toast.makeText(itemView.context, "No such app", Toast.LENGTH_SHORT).show()
                    }
                }
                playButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, videoUri)
                    if (intent.resolveActivity(itemView.context.packageManager) != null) {
                        itemView.context.startActivity(intent)
                    } else {
                        Toast.makeText(itemView.context, "No such app", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                video.visibility = View.GONE
                playButton.visibility = View.GONE
            }

            likesButton.text = receiveStringFromNumber(post.likes)
            likesButton.isChecked = post.likedByMe
            likesButton.setOnClickListener{
                onInteractionListener.onLike(post)
            }

            sharesButton.setOnClickListener { onInteractionListener.onShare(post) }
            sharesButton.text = receiveStringFromNumber(post.shares)

            viewsIcon.setImageResource(R.drawable.eye_icon_24)
            viewsCount.text = receiveStringFromNumber(post.views)
        }
    }

    fun receiveStringFromNumber(num: Long): String {
        when (num) {
            in 0..999 -> return num.toString()
            in 1_000..10_000 -> {
                if (num % 1000 < 100) {
                    return (num.toInt() / 1000).toString() + "K"
                } else {
                    return (num.toInt() / 1000).toString() + "." + ((num % 1000) / 100).toInt()
                        .toString() + "K"
                }
            }

            in 10_001..999_999 -> return (num / 1000).toInt().toString() + "K"
            in 1_000_000..999_999_999 -> return (num / 1_000_000).toInt()
                .toString() + "." + ((num % 1_000_000) / 100_000).toString() + "M"

            else -> return ""
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

