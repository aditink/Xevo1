package xevo.xevo1.AskQuestion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_quick_hit.*
import kotlinx.android.synthetic.main.content_quick_hit.*
import xevo.xevo1.enums.CaseType
import xevo.xevo1.models.CategoryData
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import xevo.xevo1.QuestionSubmitted
import xevo.xevo1.R

/**
 * Minimal Activity for Quick Hit
 */
class QuickHit : AskQuestionActivity() {

    //TODO replace with subject selection system
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference;
    private lateinit var categoryAdapter : ArrayAdapter<CategoryData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_hit)

        val caseType = intent.getSerializableExtra("questionType") as CaseType

        setSupportActionBar(quickHitToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        quickHitTitle.text = getString(R.string.title_activity_quick_hit)

        categoryAdapter = ArrayAdapter(this, R.layout.spinner_item)
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item)
        val categoryMap = hashMapOf<String, CategoryData>()
        autoComplete.setAdapter(categoryAdapter)
        autoComplete.threshold = 0
        //category_spinner.adapter = categoryAdapter

        when (caseType) {
            CaseType.QUICK_HIT -> {
                difficultWrapper.visibility = View.GONE
//                shortDescEditTextWrapper.layoutParams.height = R.dimen.quick_hit_size
            }
            CaseType.DEEP_DIVE, CaseType.HEAVY_LIFT -> {

            }
        }

        buttonSubmit.setOnClickListener({view : View ->
            val autoCompleteText = autoComplete.text.toString()
            if (categoryMap.contains<String, CategoryData>(autoCompleteText)) {
                val selectedItem = categoryMap[autoCompleteText]!!
                if (selectedItem.dbString != "") {
                    createCase(whatsupEditText.text.toString(), shortDescEditText.text.toString(), database,
                        caseType, userId, selectedItem, this)

                // Go to answer submitted screen. if back pressed, this shouldn't appear.
                val intent = Intent(this, QuestionSubmitted::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
//                openPayment(2)
                }
                           } else {
                AlertDialog.Builder(this)
                        .setIcon(R.drawable.xevo_logo)
                        .setTitle("Invalid Category")
                        .setMessage("This category does not exist. Please choose a different category.")
                        .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> {} })
                        .show()
            }
        })

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {

//                categoryAdapter.add(CategoryData("Choose Category", "", 0, false, ""))
                dataSnapshot!!.children.map {
                    val data = it.getValue<CategoryData>(CategoryData::class.java)!!
                    data.dbString = it.key
                    categoryMap[data.displayString] = data
                    categoryAdapter.add(data)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        }

        database.child(resources.getString(R.string.db_subjects)).addListenerForSingleValueEvent(dataListener)
    }
}
