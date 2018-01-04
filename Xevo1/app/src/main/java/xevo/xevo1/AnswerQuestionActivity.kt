package xevo.xevo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.os.CountDownTimer
import android.support.design.widget.TextInputEditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_answer_question.*


class AnswerQuestionActivity : AppCompatActivity() {

    lateinit var countDownTimer : CountDownTimer
    lateinit var clock : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_question)

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
