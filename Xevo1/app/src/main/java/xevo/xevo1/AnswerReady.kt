package xevo.xevo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AnswerReady : AppCompatActivity() {

    var caseId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_ready)
        caseId = intent.getStringExtra("caseId")
    }
}
