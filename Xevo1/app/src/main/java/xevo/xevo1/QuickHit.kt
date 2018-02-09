package xevo.xevo1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_quick_hit.*
import kotlinx.android.synthetic.main.content_quick_hit.*
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.XevoSubject

/**
 * Minimal Activity for Quick Hit
 */
class QuickHit : AskQuestionActivity() {

    val CASE_TYPE : CaseType = CaseType.QUICK_HIT
    //TODO replace with subject selection system
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var whatsUp : EditText
    lateinit var shortDescription : EditText
    lateinit var submitButton : Button
    lateinit var categorySpinner : Spinner
    private val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_hit)

        setSupportActionBar(quickHitToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        quickHitTitle.text = getString(R.string.title_activity_quick_hit)

        whatsUp = whatsupEditText
        shortDescription = shortDescEditText
        submitButton = buttonSubmit
        categorySpinner = category_spinner as Spinner

        var categoryList : List<XevoSubject> = XevoSubject.values().toList()
        //var categoryList : List<String> =  arrayListOf<String>("category1", "category2", "category3")
        updateCategorySpinner(categoryList)

        submitButton.setOnClickListener({view : View ->
            createCase(whatsUp.text.toString(), shortDescription.text.toString(), ref,
                    CASE_TYPE, userId, categorySpinner.selectedItem as XevoSubject, this)

            //go to answer submitted screen. if back pressed, this shouldn't appear.
            val intent = Intent(this, QuestionSubmitted::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })
    }

    private fun updateCategorySpinner(categories : List<XevoSubject>) {
        val adapter = ArrayAdapter(this, R.layout.spinner_item, categories)
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);
    }
}
