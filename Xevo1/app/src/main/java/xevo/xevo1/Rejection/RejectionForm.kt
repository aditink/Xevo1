package xevo.xevo1.Rejection

import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_rejection_form.*
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.QuestionSubmitted
import xevo.xevo1.R
import xevo.xevo1.R.id.rejection_explanation_edittext
import xevo.xevo1.R.id.submit_button
import xevo.xevo1.Util.XevoActivity
import xevo.xevo1.enums.Status

class RejectionForm : DatabaseInteractor() {

    override val TAG: String
        get() = "Rejection Form"
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
        updateCase_value(rejection, ref, caseId, "rejectionExplanation")
        updateCase_value(Status.REJECTED, ref, caseId, "status")
        startNewActivity(ThankYouForRejection::class.java, 1)
    }
}
