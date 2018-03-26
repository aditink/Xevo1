package xevo.xevo1.Rejection

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_case_rating.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.Database.DatabaseModels.User
import xevo.xevo1.QuestionSubmitted
import xevo.xevo1.R
import xevo.xevo1.databaseFunctions
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.Status
import xevo.xevo1.models.CategoryData
import java.util.HashMap

class CaseRating : AppCompatActivity() {
    lateinit var rating_bar : RatingBar
    lateinit var submit_button : Button
    lateinit var caseId : String
    lateinit var consultantId : String
    lateinit var consultant : User
    lateinit var consultant_ref : DatabaseReference
    val ref = FirebaseDatabase.getInstance().reference
    var rating = 0.0F
//    var old_rating : Float = 0.0F
    val TAG = "Rating screen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_rating)
        caseId = intent.getStringExtra("caseId")
        consultantId = intent.getStringExtra("consultantId")
        getConsultantData()
        rating_bar = ratingBar
        submit_button = submit
        submit_button.setOnClickListener( {view -> submit()})
    }

    private fun getStars() : Float {
        return rating_bar?.rating
    }

    private fun submit() {
        val ref = FirebaseDatabase.getInstance().getReference()
        rating = getStars()
        updateCase_float(rating, ref, caseId, "rating")
        updateCase_bool(true, ref, caseId, "isRated")
        updateRating()
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

    fun updateCase_float(value : Float, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }
    fun updateCase_bool(value : Boolean, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }

    /**
         * Must be called after consultant_ref has been set by getConsultantData()
        */
        fun updateRating() {
                val num_cases = consultant.casesAnswered
                if (num_cases == 0) {
                        consultant_ref.child("rating").setValue(rating)
                        consultant_ref.child("casesAnswered").setValue(1)
                    }
                else {
                        consultant_ref.child("casesAnswered").setValue(num_cases+1)
                        val new_rating = ((consultant.rating*num_cases) + rating)/(num_cases + 1.0F)
                        consultant_ref.child("rating").setValue(new_rating)
                    }
            }
        fun getConsultantData() {
                consultant_ref = ref.child(this.getString(R.string.db_users)).child(consultantId)
                var valueEventListener : ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError?) {
                                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
                            }

                        override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                var obj = dataSnapshot?.getValue(User::class.java)
                                if (obj!= null) {
                                        consultant = obj
                                    }
                            }
                    }
                consultant_ref.addListenerForSingleValueEvent(valueEventListener)
            }
}
