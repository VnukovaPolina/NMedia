package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPost2Binding

class NewPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNewPost2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText : EditText = findViewById(R.id.content)

        val intent = intent
        val receivedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (receivedText != null) {
            if (receivedText.isNotBlank()) {
                editText.setText(receivedText)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("updatedText", text)
                setResult(RESULT_OK, resultIntent)
            }
            finish()
        }
    }

}

object newPostContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit) = Intent(context, NewPost::class.java)
    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra("updatedText")
}