package xevo.xevo1

import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_display_case.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import android.text.method.ScrollingMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_display_case.*
import xevo.xevo1.enums.Status


class DisplayCase : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    var caseId : String? = null;
    lateinit var databaseReference : DatabaseReference
    val TAG = "DISPLAY_CASE"
    lateinit var caseDetails : CaseDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_case)

        setSupportActionBar(displayCaseToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        caseId = intent.getStringExtra("caseId")
        if(caseId != null)
            getCaseDetails(caseId as String)
        else
        {
            val headerFragment : ProfileAndString = supportFragmentManager.findFragmentById(
                    R.id.question_title) as ProfileAndString
            headerFragment.setText("Oops! Something went wrong...")
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateUI(caseDetails : CaseDetails) {
        val headerFragment : ProfileAndString = supportFragmentManager.findFragmentById(
                R.id.question_title) as ProfileAndString
        headerFragment.setText(caseDetails.title)
        question_details.setText(caseDetails.description)
        if (caseDetails.status == Status.ANSWERED) {
            answer.setText(caseDetails.answer)
            question_details.setMovementMethod(ScrollingMovementMethod())
            answer.movementMethod = ScrollingMovementMethod()
        }
        else {
            question_details.visibility = View.GONE
            answer.setText(caseDetails.description)
            answer.movementMethod = ScrollingMovementMethod()
        }
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
//                    question_details.setText(caseDetails.description)
                    updateUI(caseDetails)
                }
            }
        }
        databaseReference.addListenerForSingleValueEvent(valueEventListener)
    }
}
