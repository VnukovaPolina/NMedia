package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newOrEditPostLauncher = registerForActivityResult(newOrEditPostContract) { content ->
            if (content == null) {
                viewModel.undoEdit()
                return@registerForActivityResult
            }
            viewModel.changeContent(content)
            viewModel.save()
        }

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                newOrEditPostLauncher.launch(post.content)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }
                val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(chooser)
            }

            override fun onRemove(post: Post) {
                viewModel.deleteById(post.id)
            }

            override fun onPlayVideo(post: Post) {
                val  videoUri = Uri.parse(post.video)
                val intent = Intent(Intent.ACTION_VIEW, videoUri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "No such app", Toast.LENGTH_SHORT).show()
                }
            }

        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val new = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (new) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.add.setOnClickListener {
            newOrEditPostLauncher.launch("")
        }

    }
}