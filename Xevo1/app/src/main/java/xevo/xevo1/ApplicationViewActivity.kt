package xevo.xevo1

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_application_view.*
import kotlinx.android.synthetic.main.content_application_view.*
import xevo.xevo1.AdminActivity.Companion.APPLICATION_DATA
import xevo.xevo1.enums.Consultant
import xevo.xevo1.models.ApplicationData

class ApplicationViewActivity : AppCompatActivity() {

    lateinit var applicationData: ApplicationData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_view)

        title = "Application"

        applicationData = intent.getParcelableExtra(APPLICATION_DATA)

        // add close button
        setSupportActionBar(applicationViewToolbar)
        @TargetApi(21)
        applicationViewToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        applicationViewToolbar.setNavigationOnClickListener { _ ->
            returnResult(Activity.RESULT_CANCELED)
        }

        applicationViewName.text = "%s %s".format(applicationData.firstName, applicationData.lastName)
        applicationViewEmail.text = applicationData.email

        val ref = FirebaseDatabase.getInstance().reference

        approveApplicationButton.setOnClickListener { v ->
            ref.child("Users/%s/isConsultant/".format(applicationData.userId)).setValue(Consultant.VERIFIED, { error, _ ->
                if (error == null) {
                    ref.child("PendingApplications/%s/".format(applicationData.userId)).removeValue()
                    returnResult(Activity.RESULT_OK)
                } else {
                    returnResult(Activity.RESULT_CANCELED)
                }
            })
        }
    }

    override fun onBackPressed() = returnResult(Activity.RESULT_CANCELED)

    private fun returnResult(result: Int) {
        val returnIntent = Intent(this, AdminActivity::class.java).apply {
            putExtra(APPLICATION_DATA, applicationData)
        }
        setResult(result, returnIntent)
        finish()
    }
}
