package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        }, {
            viewModel.shareById(it.id)
        }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
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