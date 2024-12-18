package ru.netology.nmedia.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

private val empty = Post(
    id = 0L,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun saveContent(textContent: String) {
        edited.value?.let {
            repository.save(it, textContent)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun undoEdit() {
        edited.value = empty
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun deleteById(id: Long) = repository.deleteById(id)
}