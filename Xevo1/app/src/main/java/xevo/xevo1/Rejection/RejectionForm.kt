package xevo.xevo1.Rejection

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_rejection_form.*
import xevo.xevo1.QuestionSubmitted
import xevo.xevo1.R

class RejectionForm : AppCompatActivity() {

    lateinit var caseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejection_form)
        caseId = intent.getStringExtra("caseId")
        submit_button.setOnClickListener({ view -> submit() })
    }

    fun submit() {
        val ref = FirebaseDatabase.getInstance().getReference()
        val rejection = rejection_explanation_edittext.text.toString()
        updateCase_string(rejection, ref, caseId, "rejectionExplanation")
        startNewActivity(ThankYouForRejection::class.java)
    }

    fun updateCase_string(value: String, ref: DatabaseReference, caseId: String, field: String) {
            ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }

    private fun startNewActivity(activity_name : Class<*>){
        val intent = Intent(this, activity_name)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
