package xevo.xevo1.Rejection

import android.content.Context
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
//    var old_rating : Float = 0.0F
    val TAG = "Rating screen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_rating)
        caseId = intent.getStringExtra("caseId")
        rating_bar = ratingBar
        submit_button = submit
        submit_button.setOnClickListener( {view -> submit()})
    }

    private fun getStars() : Float {
        return rating_bar?.rating
    }

    private fun submit() {
        val ref = FirebaseDatabase.getInstance().getReference()
        updateCase_float(getStars(), ref, caseId, "rating")
        updateCase_bool(true, ref, caseId, "isRated")
        Log.d(TAG, getStars().toString())
        Log.d(TAG, caseId)
    }

    fun updateCase_float(value : Float, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }
    fun updateCase_bool(value : Boolean, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }
}
