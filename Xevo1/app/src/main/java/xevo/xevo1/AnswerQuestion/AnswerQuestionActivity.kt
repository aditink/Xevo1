package xevo.xevo1.AnswerQuestion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.TextInputEditText
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_answer_question.*
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.Main
import xevo.xevo1.R
import xevo.xevo1.enums.Status
import java.util.HashMap


class AnswerQuestionActivity : DatabaseInteractor() {

    lateinit var countDownTimer : CountDownTimer
    lateinit var clock : TextView
    lateinit var caseId : String
    lateinit var answerEditText : TextInputEditText
    val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override val TAG = "AnswerQuestionActivity"
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    var database : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var submit_clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_question)
        caseId = intent.getStringExtra("caseId")

        answerEditText = answer_edit_text
        clock = clock_text_view

        getCaseDetails(databaseReference, caseId)

        countDownTimer = object : CountDownTimer(1800000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds_left = millisUntilFinished/1000
                var minutes_left = seconds_left/60
                var seconds_display = seconds_left % 60
                clock.setText(minutes_left.toString() + ":" + String.format("%02d", seconds_display))
            }

            override fun onFinish() {
                clock.setText("Time Up!")
                cancelTimer()
            }
        }.start()

        submit_answer_button.setOnClickListener { view : View ->
            submit_clicked = true
            if (caseDetails != null) {
                if (caseDetails?.wasRejected!!) {
                    uploadAnswer(caseDetails!!, userId, caseId, databaseReference,
                            answer_edit_text.text.toString(), Status.REANSWERED,
                            oldAnswer = caseDetails?.answer!!)
                }
                else {
                    uploadAnswer(caseDetails!!, userId, caseId, databaseReference,
                            answer_edit_text.text.toString())
                }
                startNewActivity(Main::class.java, 2)
            }
        }
    }

    private fun cancelTimer() {
        if(countDownTimer!=null)
            countDownTimer.cancel();
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }
}
