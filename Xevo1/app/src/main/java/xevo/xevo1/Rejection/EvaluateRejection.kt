package xevo.xevo1.Rejection

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_evaluate_rejection.*
import xevo.xevo1.AnswerQuestionActivity
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.QuestionSubmitted
import xevo.xevo1.R
import xevo.xevo1.enums.Status

class EvaluateRejection : AppCompatActivity() {

    val TAG = "EVALUATE_REJECTION"
    lateinit var caseId : String
    lateinit var caseDetails : CaseDetails
    lateinit var databaseReference : DatabaseReference
    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_rejection)
        caseId = intent.getStringExtra("caseId")
        getCaseDetails(caseId)
        submit_button.setOnClickListener( { view -> submit() })
    }

    /** Must be called only after getCaseDetails has initialised caseDetails*/
    private fun updateUI() {
        rejection_explanation_content.setText(caseDetails.rejectionExplanation)
    }

    private fun submit() {
        val isOriginalConsultant = userId.equals(caseDetails.consultant)
        if (isOriginalConsultant) {
            updateCase(rejection_review_comments.text.toString(), databaseReference, caseId, "originalConsultantReaction")
        }
        else {
            updateCase(rejection_review_comments.text.toString(), databaseReference, caseId, "moderatorReaction")
        }
        var choice = agree_with_rejection_group.checkedRadioButtonId
        if (choice == -1) {
            //TODO : warning to select option
        }
        else {
            Log.d(TAG, choice.toString())
            if (choice == R.id.rejection_agree) {
                val intent = Intent(this, AnswerQuestionActivity::class.java)
                intent.putExtra("caseId", caseId)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            else {
                if (isOriginalConsultant) {
                    updateSubject(CaseOverview(caseDetails), databaseReference)
                }
                else {
                    //Notify user that their rejection was not deemed acceptable.
                    updateCase(Status.ANSWERED, databaseReference, caseId, "status")
                    finish()
                }
            }
        }
    }

    fun updateCase(newValue: Any, ref: DatabaseReference, caseId: String, field: String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(newValue)
    }

    fun updateSubject(caseOverview : CaseOverview, ref: DatabaseReference) {
        ref.child(this.getString(R.string.db_cases_by_subject)).child(caseDetails.subject).child(caseDetails.caseId).setValue(caseOverview)
    }

    fun getCaseDetails(caseId: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference(
                getString(R.string.db_cases) + caseId)
        var valueEventListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(CaseDetails::class.java)
                if (obj!= null) {
                    caseDetails = obj
                    updateUI()
                }
            }
        }
        databaseReference.addValueEventListener(valueEventListener)
    }
}
