package xevo.xevo1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_answer_ready.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails

class AnswerReady : AppCompatActivity(),
        ProfileAndString.OnFragmentInteractionListener {

    var caseId : String? = null
    lateinit var databaseReference : DatabaseReference
    val TAG : String = "ANSWER_READY_ACTIVITY"
    lateinit var caseDetails : CaseDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_ready)
        caseId = intent.getStringExtra("caseId")
        Log.d(TAG, "Case Id : " + caseId)

        if(caseId != null) {
            getQuestionDetails(caseId!!)
        }
        //TODO handle the other case
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getQuestionDetails(caseId : String) {
        databaseReference = FirebaseDatabase.getInstance().getReference(
                getString(R.string.db_cases) + caseId)
        var valueEventListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
                Log.d(TAG, "error")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(CaseDetails::class.java)
                if (obj!= null) {
                    Log.d(TAG, "Case details got")
                    caseDetails = obj
//                    question_details.setText(caseDetails.description)
                    updateUI(caseDetails)
                }
            }
        }
        databaseReference.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun updateUI(caseDetails: CaseDetails) {
        Log.d(TAG, "UI update called")
        var headerFragment : ProfileAndString = supportFragmentManager.findFragmentById(
                R.id.question_title) as ProfileAndString
        headerFragment.setText(caseDetails.title)
        question_details_text_view.setText(caseDetails.description)
        answer_display_text_view.setText(caseDetails.answer)
    }
}
