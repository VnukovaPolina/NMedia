package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        }, {
            viewModel.shareById(it.id)
        }, {
            viewModel.deleteById(it.id)
        }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val new = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (new) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, R.string.empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.saveContent(text)

            binding.content.setText("")
            binding.content.clearFocus()
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