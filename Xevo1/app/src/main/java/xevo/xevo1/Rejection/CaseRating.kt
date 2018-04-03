package xevo.xevo1.Rejection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_case_rating.*
import xevo.xevo1.R
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Util.Extra
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
        getCaseDetails(ref, caseId)
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
        updateCase_bool(true, ref, caseId, "isRated")
        consultant?.let { updateRating(it, ref, rating) }
        if (rating < 3.0F) {
            if (caseDetails != null) {
                Log.d(TAG, caseDetails?.wasRejected.toString())
                if (caseDetails?.wasRejected!!) {
                    updateCase_float(caseDetails?.rating!!, ref, caseId, "oldRating")
                    updateCase_float(rating, ref, caseId, "rating")
                    startNewActivity(Apology::class.java, 2)
                }
                else {
                    updateCase_float(rating, ref, caseId, "rating")
                    updateCase_bool(true, ref, caseId, "wasRejected")
                    updateCase_value(Status.REJECTED, ref, caseId, "status")
                    caseDetails?.status=Status.REJECTED
                    addCaseToSubject(caseDetails!!, ref)
                    startNewActivity(RejectionForm::class.java, extras = arrayOf<Extra>(
                            Extra("caseId", caseId), Extra("consultantId", consultantId)
                    ))
                }
//                val intent = Intent(this, RejectionForm::class.java)
//                intent.putExtra("caseId", caseId)
//                intent.putExtra("consultantId", consultantId)
//                startActivity(intent)
//                updateCase_bool(true, ref, caseId, "wasRejected")
            }
        }
        else {
            updateCase_float(rating, ref, caseId, "rating")
            startNewActivity(ThankYouForRating::class.java, 2)
        }
        finish()
    }
}
