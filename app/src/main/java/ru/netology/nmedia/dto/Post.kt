package ru.netology.nmedia.dto

data class Post (
    val id : Long = 0L,
    val content : String = "",
    val published : String = "",
    val author : String = "",
    var likedByMe : Boolean = false,
    var likes : Long = 1099,
    var shares : Long = 561,
    var views : Long = 100_345
)