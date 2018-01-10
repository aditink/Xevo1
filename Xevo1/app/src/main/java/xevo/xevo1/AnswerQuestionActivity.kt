package xevo.xevo1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.os.CountDownTimer
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_answer_question.*


class AnswerQuestionActivity : AppCompatActivity() {

    lateinit var countDownTimer : CountDownTimer
    lateinit var clock : TextView
    lateinit var caseId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_question)
        caseId = intent.getStringExtra("caseId")

        clock = clock_text_view

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
            uploadAnswer()
            val intent = Intent(this, Main::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
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
     * TODO
     */
    fun uploadAnswer() {

    }
}
