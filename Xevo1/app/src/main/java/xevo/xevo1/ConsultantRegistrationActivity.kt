package xevo.xevo1

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_consultant_registration.*
import kotlinx.android.synthetic.main.content_consultant_registration.*
import xevo.xevo1.enums.Consultant
import xevo.xevo1.models.ConsultantApplication

class ConsultantRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultant_registration)

        // add close button
        setSupportActionBar(consultantRegistrationToolbar)
        @TargetApi(21)
        consultantRegistrationToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        consultantRegistrationToolbar.setNavigationOnClickListener { _ ->
            returnResult(Activity.RESULT_CANCELED)
        }

        consultantApplyButton.setOnClickListener(ConsultantApplyButtonClickListener(resources, cardNumberInput, this))
    }

    override fun onBackPressed() = returnResult(Activity.RESULT_CANCELED)

    private fun returnResult(result: Int) {
        setResult(result, Intent())
        finish()
    }

    class ConsultantApplyButtonClickListener(val resources: Resources, val input: TextInputEditText, val activity: ConsultantRegistrationActivity) : View.OnClickListener {
        override fun onClick(view: View?) {
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = FirebaseDatabase.getInstance().reference
            ref.child(resources.getString(R.string.db_pending_app) + userId).setValue(ConsultantApplication(input.text.toString()), { error, dataRef ->
                if (error != null) {
                    //TODO: Deal with errors
                    println(error.code)
                    println(error.message)
                    activity.returnResult(Activity.RESULT_CANCELED)
                } else {
                    activity.returnResult(Activity.RESULT_OK)
                }
            })

            ref.child(resources.getString(R.string.db_users) + userId + "/isConsultant/").setValue(Consultant.PENDING)

        }

    }
}
