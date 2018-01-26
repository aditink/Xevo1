package xevo.xevo1

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_read_question.*
import kotlinx.android.synthetic.main.content_read_question.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.enums.Status
import java.util.HashMap
import android.support.v4.app.NavUtils
import xevo.xevo1.Database.DatabaseModels.CaseOverview


class ReadQuestion : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    val TAG : String = "Read Question"
//    var caseId : String? = null
    lateinit var caseData : CaseOverview
    lateinit var acceptButton : Button
    lateinit var databaseReference : DatabaseReference
    lateinit var subjectDbString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_question)
        caseData = intent.getParcelableExtra<CaseOverview>("caseId")
        subjectDbString = intent.getStringExtra("subjectId")

        setSupportActionBar(readQuestionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        question_details.setMovementMethod(ScrollingMovementMethod())

        acceptButton = accept_button
        acceptButton.setOnClickListener() { _ : View -> onAccept() }

        val headerFragment : ProfileAndString = supportFragmentManager.findFragmentById(
                R.id.question_title) as ProfileAndString
        headerFragment.setText(caseData.title)
        question_details.setText(caseData.description)

        println("Subject Db String" + subjectDbString)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "hi")
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onAccept() {
        databaseReference=FirebaseDatabase.getInstance().reference
        val childUpdates = HashMap<String, Any?>()

        //TODO: get rid of this if statement, this is here for the moment to not break things by accepting a question through the All category
        if (subjectDbString != "") {
            childUpdates.put(getString(R.string.db_cases) + caseData.caseId + "/status", Status.BEING_ANSWERED as Object)
            childUpdates.put(getString(R.string.db_cases_by_subject) + subjectDbString + caseData.caseId, null)
            databaseReference.updateChildren(childUpdates as Map<String, Any>)
        }

        val intent = Intent(this, AnswerQuestionActivity::class.java)
        intent.putExtra("caseId", caseData.caseId)
        startActivity(intent)
    }
}
