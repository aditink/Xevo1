package xevo.xevo1.Rejection

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_evaluate_rejection.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
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
        updateCase(Status.REJECTED, databaseReference, caseId, "status")
        if (userId.equals(caseDetails.consultant)) {
            updateCase(rejection_review_comments.text, databaseReference, caseId, "originalConsultantReaction")
        }
        else {
            updateCase(rejection_review_comments.text, databaseReference, caseId, "moderatorReaction")
        }
        var choice = agree_with_rejection_group.checkedRadioButtonId
        if (choice == -1) {
            //TODO : warning to select option
        }
        else {
            
        }
    }

    fun updateCase(newValue: Any, ref: DatabaseReference, caseId: String, field: String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(newValue)
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
