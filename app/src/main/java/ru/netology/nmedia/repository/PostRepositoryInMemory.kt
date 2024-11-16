package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {
    private var post = Post(
        id = 1,
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:55",
        likedByMe = false,
        likes = 1099,
        shares = 561,
        views = 100_345
    )

    private val data = MutableLiveData(post)

    override fun getPost(): LiveData<Post> = data

    override fun like() {
        if (post.likedByMe) {
            post = post.copy(likedByMe = !post.likedByMe, likes = post.likes - 1)
        } else {
            post = post.copy(likedByMe = !post.likedByMe, likes = post.likes + 1)
        }
        data.value = post
    }

    override fun share() {
        post = post.copy(shares = post.shares + 1)
        data.value = post
    }
}