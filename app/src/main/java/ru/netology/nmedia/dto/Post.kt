package ru.netology.nmedia.dto

data class Post (
    val id : Long = 0L,
    val content : String = "",
    val published : String = "",
    val author : String = "",
    val likedByMe : Boolean = false,
    val likes : Long = 1099,
    val shares : Long = 561,
    val views : Long = 100_345
)