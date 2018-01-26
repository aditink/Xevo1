package xevo.xevo1

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_display_answer.*
import kotlinx.android.synthetic.main.content_display_answer.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails

class DisplayAnswer : AppCompatActivity(),
        ProfileAndString.OnFragmentInteractionListener {

    var caseId : String? = null
    lateinit var databaseReference : DatabaseReference
    val TAG : String = "ANSWER_READY_ACTIVITY"
    lateinit var caseDetails : CaseDetails
    lateinit var questionDetailsTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_answer)
        setSupportActionBar(toolbar)

        caseId = intent.getStringExtra("caseId")
        Log.d(TAG, "Case Id : " + caseId)

        if(caseId != null) {
            getQuestionDetails(caseId!!)
            questionDetailsTextView = question_details_text_view
//            if (questionDetailsTextView.maxLines > 4) {
            expand_collapse_text_view.visibility = View.VISIBLE
//            }
            expand_collapse_text_view.setOnClickListener(View.OnClickListener { collapseOrExpandTextView() })
        }
        //TODO handle the other case
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun collapseOrExpandTextView() {
        val collapsedMaxLines = 4
        var questionDetailsTextView = question_details_text_view
        if (questionDetailsTextView.getMaxLines() >= collapsedMaxLines) {
            collapseTextView(questionDetailsTextView, collapsedMaxLines)
        }
        else {
            expandTextView(questionDetailsTextView)
        }
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

    private fun expandTextView(tv: TextView) {
        tv.maxLines = Int.MAX_VALUE
        val animation = ObjectAnimator.ofInt(tv, "maxLines", tv.lineCount)
        animation.setDuration(200).start()
    }

    private fun collapseTextView(tv: TextView, numLines: Int) {
        val animation = ObjectAnimator.ofInt(tv, "maxLines", numLines)
        animation.setDuration(200).start()
    }

}
