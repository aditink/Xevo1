package xevo.xevo1.Rejection

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_evaluate_rejection.*
import xevo.xevo1.AnswerQuestion.AnswerQuestionActivity
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.R
import xevo.xevo1.Util.Extra
import xevo.xevo1.enums.Status

class EvaluateRejection : DatabaseInteractor() {

    override val TAG = "EVALUATE_REJECTION"
    lateinit var caseId : String
    val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_rejection)
        caseId = intent.getStringExtra("caseId")
        getCaseDetails(databaseReference, caseId)
        submit_button.setOnClickListener( { view -> submit() })
    }

    /** Must be called only after getCaseDetails has initialised caseDetails*/
    override fun updateUiWithCaseDetails() {
        rejection_explanation_content.text = caseDetails?.rejectionExplanation
    }

    private fun submit() {
        if (caseDetails != null) {
            val isOriginalConsultant = userId.equals(caseDetails?.consultant)
            if (isOriginalConsultant) {
                updateCase_value(rejection_review_comments.text.toString(),
                        databaseReference, caseId, "originalConsultantReaction")
            } else {
                updateCase_value(rejection_review_comments.text.toString(), databaseReference, caseId,
                        "moderatorReaction")
            }
            var choice = agree_with_rejection_group.checkedRadioButtonId
            if (choice == -1) {
                //TODO : warning to select option
            } else {
                Log.d(TAG, choice.toString())
                if (choice == R.id.rejection_agree) {
                    startNewActivity(AnswerQuestionActivity::class.java, 1,
                            arrayOf<Extra>(Extra("caseId", caseId)))
                } else {
                    if (isOriginalConsultant) {
                        updateSubject(CaseOverview(caseDetails!!), databaseReference, caseDetails!!)
                    } else {
                        //Notify user that their rejection was not deemed acceptable.
                        updateCase_value(Status.REJECTION_REJECTED, databaseReference, caseId,
                                "status")
                        finish()
                    }
                }
            }
        }
        else {
            getCaseDetails(databaseReference, caseId)
        }
    }
}
