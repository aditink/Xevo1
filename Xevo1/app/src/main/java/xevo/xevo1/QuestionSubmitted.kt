package xevo.xevo1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_question_submitted.*

class QuestionSubmitted : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_submitted)
        tick_image_view.setOnClickListener() {view -> goBack()}
    }
    private fun goBack() {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
        finish()
    }
}
