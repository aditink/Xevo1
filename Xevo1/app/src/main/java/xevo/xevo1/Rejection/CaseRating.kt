package xevo.xevo1.Rejection

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import kotlinx.android.synthetic.main.activity_case_rating.*
import xevo.xevo1.R

class CaseRating : AppCompatActivity() {
    lateinit var rating_bar : RatingBar
    lateinit var submit_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_rating)
        rating_bar = ratingBar
        submit_button = submit
        submit_button.setOnClickListener( {view -> submit()})
    }

    private fun getStars() : Int {
        return rating_bar?.numStars
    }

    private fun submit() {
    }
}
