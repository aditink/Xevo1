package xevo.xevo1.Rejection

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_case_rating.*
import xevo.xevo1.R
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.enums.Status

class CaseRating : DatabaseInteractor() {
    lateinit var rating_bar : RatingBar
    lateinit var submit_button : Button
    lateinit var caseId : String
    lateinit var consultantId : String
    val ref = FirebaseDatabase.getInstance().reference
    var rating = 0.0F
    override val TAG: String
        get() = "Rating Screen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_rating)
        caseId = intent.getStringExtra("caseId")
        consultantId = intent.getStringExtra("consultantId")
        getConsultantData(ref, consultantId)
        rating_bar = ratingBar
        submit_button = submit
        submit_button.setOnClickListener( {view -> submit()})
    }

    private fun getStars() : Float {
        return rating_bar.rating
    }

    private fun submit() {
        val ref = FirebaseDatabase.getInstance().getReference()
        rating = getStars()
        updateCase_float(rating, ref, caseId, "rating")
        updateCase_bool(true, ref, caseId, "isRated")
        updateCase_bool(true, ref, caseId, "wasRejected")
        consultant?.let { updateRating(it, ref, rating) }
        if (rating < 3.0F) {
            val intent = Intent(this, RejectionForm::class.java)
            intent.putExtra("caseId", caseId)
            intent.putExtra("consultantId", consultantId)
            startActivity(intent)
        }
        else {
            //TODO: Add a thank you for rating! screen
        }
        finish()
    }
}
