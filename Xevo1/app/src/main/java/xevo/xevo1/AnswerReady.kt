package xevo.xevo1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_answer_ready.*

class AnswerReady : AppCompatActivity() {

    var caseId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_ready)

        setSupportActionBar(answerReadyToolbar)

        caseId = intent.getStringExtra("caseId")
        xevo_logo_image_view.setOnClickListener() {view -> displayAnswer(caseId) }
    }
    private fun displayAnswer(caseId : String?) {
        if (caseId != null) {
            val intent = Intent(this, DisplayCase::class.java)
            intent.putExtra("caseId", caseId)
            startActivity(intent)
        }
    }
}
