package xevo.xevo1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.os.CountDownTimer
import android.support.design.widget.TextInputEditText
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_answer_question.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.enums.Status
import java.util.HashMap


class AnswerQuestionActivity : AppCompatActivity() {

    lateinit var countDownTimer : CountDownTimer
    lateinit var clock : TextView
    lateinit var caseId : String
    lateinit var answerEditText : TextInputEditText
    var caseDetails : CaseDetails? = null

    val TAG = "AnswerQuestionActivity"
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    var database : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    var submit_clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_question)
        caseId = intent.getStringExtra("caseId")

        answerEditText = answer_edit_text
        clock = clock_text_view

        var valueEventListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(CaseDetails::class.java)
                if (obj!= null) {
                    caseDetails = obj
                    if (submit_clicked) {
                        uploadAnswer()
                    }
                }
            }
        }
        var databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference(
                getString(R.string.db_cases) + caseId)
        databaseReference.addListenerForSingleValueEvent(valueEventListener)

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
                uploadAnswer()
                val intent = Intent(this, Main::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
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

    /**
     * Do all database updates necessary when question is answered
     */
    fun uploadAnswer() {
        val childUpdates = HashMap<String, Object>()
        //TODO: Modify the caseDetails object itself and push
        childUpdates.put(getString(R.string.db_cases)+caseId + "/consultant", userId as Object)
//        childUpdates.put(getString(R.string.db_cases)+caseId + "/answer", answerEditText.text as Object)
//        childUpdates.put(getString(R.string.db_cases)+caseId + "/status", Status.ANSWERED as Object)
        var caseOverview : CaseOverview = CaseOverview(caseDetails)
//        childUpdates.put(getString(R.string.db_cases_by_subject) + caseId, caseOverview as Object)
        //TODO delete from  subject table by specifying null in childUpdates
        childUpdates.put(getString(R.string.db_cases_by_users) +  userId + "/" + getString(
                R.string.db_answers)+ caseDetails?.caseId, caseOverview as Object)

        database.updateChildren(childUpdates as Map<String, Any>)
    }
}
