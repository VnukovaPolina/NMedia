package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:55"
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likesButton.setImageResource(R.drawable.favorited_24)
            } else {
                likesButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            likesCount.text = receiveStringFromNumber(post.likes)

            likesButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likesButton.setImageResource(
                    if (post.likedByMe) R.drawable.favorited_24 else R.drawable.baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount.text = receiveStringFromNumber(post.likes)
            }

            sharesIcon.setImageResource(R.drawable.baseline_share_24)
            sharesCount.text = post.shares.toString()
            sharesIcon.setOnClickListener {
                post.shares++
                sharesCount.text = receiveStringFromNumber(post.shares)
            }

            viewsIcon.setImageResource(R.drawable.eye_icon_24)
            viewsCount.text = receiveStringFromNumber(post.views)
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