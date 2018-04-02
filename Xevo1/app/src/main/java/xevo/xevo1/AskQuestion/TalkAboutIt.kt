package xevo.xevo1.AskQuestion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_talk_about_it.*
import kotlinx.android.synthetic.main.content_talk_about_it.*
import xevo.xevo1.R
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.XevoSubject

/**
 * Minimal Activity for Talk About It
 */
class TalkAboutIt : AskQuestionActivity() {

    val CASE_TYPE : CaseType = CaseType.DEEP_DIVE
    //TODO replace with subject selection system
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var whatsUp : EditText
    lateinit var shortDescription : EditText
    lateinit var submitButton : Button
    lateinit var categorySpinner : Spinner
    private val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_about_it)

        setSupportActionBar(talkToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        talkTitle.text = getString(R.string.title_activity_talk_about_it)

        whatsUp = whatsupEditText
        shortDescription = shortDescEditText
        submitButton = buttonSubmit
        categorySpinner = category_spinner as Spinner

        var categoryList : List<XevoSubject> = XevoSubject.values().toList()
        //var categoryList : List<String> =  arrayListOf<String>("category1", "category2", "category3")
        updateCategorySpinner(categoryList)

//        submitButton.setOnClickListener({view : View ->
//            createCase(whatsUp.text.toString(), shortDescription.text.toString(), ref,
//                    CASE_TYPE, userId, categorySpinner.selectedItem as XevoSubject, this)
//            //go back home
//            val intent = Intent(this, QuestionSubmitted::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            finish()
//        })
    }

    private fun updateCategorySpinner(categories : List<XevoSubject>) {
        val adapter = ArrayAdapter(this, R.layout.spinner_item, categories)
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);
    }
}
