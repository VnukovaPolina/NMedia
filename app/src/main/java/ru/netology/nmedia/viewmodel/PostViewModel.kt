package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInFile
import ru.netology.nmedia.repository.PostRepositoryInMemory

private val empty = Post(
    id = 0L,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInFile(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun undoEdit() {
        edited.value = empty
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == null) {
            edited.value = empty
            return
        }
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun deleteById(id: Long) = repository.deleteById(id)
}