package xevo.xevo1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_quick_hit.*
import kotlinx.android.synthetic.main.content_quick_hit.*
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.XevoSubject
import xevo.xevo1.models.CategoryData

/**
 * Minimal Activity for Quick Hit
 */
class QuickHit : AskQuestionActivity() {

    val CASE_TYPE : CaseType = CaseType.QUICK_HIT
    //TODO replace with subject selection system
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference;
    private lateinit var categoryAdapter : ArrayAdapter<CategoryData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_hit)

        setSupportActionBar(quickHitToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        quickHitTitle.text = getString(R.string.title_activity_quick_hit)

        categoryAdapter = ArrayAdapter(this, R.layout.spinner_item)
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item)
        category_spinner.adapter = categoryAdapter

//        var categoryList : List<XevoSubject> = XevoSubject.values().toList()
        //var categoryList : List<String> =  arrayListOf<String>("category1", "category2", "category3")
//        updateCategorySpinner(categoryList)

        buttonSubmit.setOnClickListener({view : View ->
            createCase(whatsupEditText.text.toString(), shortDescEditText.text.toString(), database,
                    CASE_TYPE, userId, category_spinner.selectedItem as CategoryData, this)

            //go to answer submitted screen. if back pressed, this shouldn't appear.
            val intent = Intent(this, QuestionSubmitted::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {

                dataSnapshot!!.children.map {
                    val data = it.getValue<CategoryData>(CategoryData::class.java)!!
                    data.dbString = it.key
                    categoryAdapter.add(data)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        }

        database.child(resources.getString(R.string.db_subjects)).addListenerForSingleValueEvent(dataListener)
    }

//    private fun updateCategorySpinner(categories : List<XevoSubject>) {
//        val adapter = ArrayAdapter(this, R.layout.spinner_item, categories)
//        adapter.setDropDownViewResource(R.layout.spinner_item);
//        category_spinner.adapter = adapter;
//    }
}
