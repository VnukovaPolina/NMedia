package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()

        viewModel.post.observe(this) {
            post ->
            with(binding) {
                avatar.setImageResource(R.drawable.netology)
                author.text = post.author
                published.text = post.published
                content.text = post.content
                menu.setImageResource(R.drawable.baseline_more_vert_24)
                if (post.likedByMe) {
                    likesButton.setImageResource(R.drawable.favorited_24)
                } else {
                    likesButton.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                likesCount.text = receiveStringFromNumber(post.likes)

                sharesButton.setImageResource(R.drawable.baseline_share_24)
                sharesCount.text = receiveStringFromNumber(post.shares)

                viewsIcon.setImageResource(R.drawable.eye_icon_24)
                viewsCount.text = receiveStringFromNumber(post.views)
            }
        }

        binding.likesButton.setOnClickListener {
            viewModel.like()
        }

        binding.sharesButton.setOnClickListener {
            viewModel.share()
        }
    }
}

fun receiveStringFromNumber (num: Long) : String {
    when (num) {
        in 0..999  -> return num.toString()
        in 1_000 .. 10_000 -> {
            if (num % 1000 < 100) {
                return (num.toInt() / 1000).toString() + "K"
            }
            else {
                return (num.toInt() / 1000).toString() + "." + ((num % 1000)/100).toInt().toString() + "K"
            }
        }
        in 10_001..999_999 -> return (num/1000).toInt().toString() + "K"
        in 1_000_000..999_999_999 -> return (num/1_000_000).toInt().toString() + "." + ((num % 1_000_000)/100_000).toString() + "M"
        else -> return ""
    }
}