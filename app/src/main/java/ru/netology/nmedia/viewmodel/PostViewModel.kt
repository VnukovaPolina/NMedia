package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()

    val post = repository.getPost()

    fun like() {
        repository.like()
    }

    fun share() {
        repository.share()
    }

}