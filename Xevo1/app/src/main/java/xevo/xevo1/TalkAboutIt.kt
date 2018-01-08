package xevo.xevo1

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_talk_about_it.*
import kotlinx.android.synthetic.main.content_talk_about_it.*
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.XevoSubject
import xevo.xevo1.models.AskQuestionActivity

/**
 * Minimal Activity for Talk About It
 */
class TalkAboutIt : AskQuestionActivity() {

    val CASE_TYPE : CaseType = CaseType.DEEP_DIVE
    //TODO replace with subject selection system
    val SUBJECT : XevoSubject = XevoSubject.PHYSICS
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var whatsUp : EditText
    lateinit var shortDescription : EditText
    lateinit var submitButton : Button
    private val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_about_it)
        setSupportActionBar(toolbar)

        whatsUp = whatsupEditText
        shortDescription = shortDescEditText
        submitButton = buttonSubmit

        submitButton.setOnClickListener({view : View ->
            createCase(whatsUp.text.toString(), shortDescription.text.toString(), ref,
                    CASE_TYPE, userId, SUBJECT, this)
            //go back home
            val intent = Intent(this, Main::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })
    }

}
