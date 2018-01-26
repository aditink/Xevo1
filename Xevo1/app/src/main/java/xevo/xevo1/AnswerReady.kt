package xevo.xevo1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_answer_ready.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import android.animation.ObjectAnimator
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_answer_ready.view.*
import xevo.xevo1.R.id.question_details_text_view


class AnswerReady : AppCompatActivity() {

    var caseId : String? = null
    lateinit var databaseReference : DatabaseReference
    val TAG : String = "ANSWER_READY_ACTIVITY"
    lateinit var caseDetails : CaseDetails
    lateinit var questionDetailsTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_ready)
        caseId = intent.getStringExtra("caseId")
        Log.d(TAG, "Case Id : " + caseId)

        if (caseId != null) {
        }
    }
}
