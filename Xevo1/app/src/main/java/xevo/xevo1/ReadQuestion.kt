package xevo.xevo1

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_read_question.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.enums.Status
import java.util.HashMap

class ReadQuestion : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    val TAG : String = "Read Question"
    var caseId : String? = null
    lateinit var acceptButton : Button
    lateinit var databaseReference : DatabaseReference
    lateinit var caseDetails : CaseDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_question)
        caseId = intent.getStringExtra("caseId")

        question_details.setMovementMethod(ScrollingMovementMethod())

        acceptButton = accept_button
        acceptButton.setOnClickListener() { view : View ->   onAccept() }

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
                    updateUI()
                }
            }
        }
        databaseReference.addListenerForSingleValueEvent(valueEventListener)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onAccept() {
        databaseReference=FirebaseDatabase.getInstance().getReference()
        val childUpdates = HashMap<String, Object?>()
        childUpdates.put(getString(R.string.db_cases) + caseId + "/status", Status.BEING_ANSWERED as Object)
        childUpdates.put(getString(R.string.db_cases_by_subject) + caseDetails.subject.dbString + caseId, null)
        databaseReference.updateChildren(childUpdates as Map<String, Any>)

        val intent = Intent(this, AnswerQuestionActivity::class.java)
        intent.putExtra("caseId", caseId)
        startActivity(intent)
    }

    fun updateUI() {
        val intent = Intent(this, Main::class.java)
        var headerFragment : ProfileAndString = supportFragmentManager.findFragmentById(
                R.id.question_title) as ProfileAndString
        headerFragment.setText(caseDetails.title)
        question_details.setText(caseDetails.description)
    }
}
