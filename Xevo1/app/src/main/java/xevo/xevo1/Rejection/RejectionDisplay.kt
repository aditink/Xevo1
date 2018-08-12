package xevo.xevo1.Rejection

import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_rejection_display.*
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.R

class RejectionDisplay : DatabaseInteractor() {

    override val TAG = "Rejection display"
    lateinit var caseId : String
    val ref = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejection_display)
        caseId = intent.getStringExtra("caseId")
        getCaseDetails(ref, caseId)
    }

    override fun updateUiWithCaseDetails() {
        super.updateUiWithCaseDetails()
        rejection_reason_text_view.setText(caseDetails?.rejectionExplanation)
    }
}
