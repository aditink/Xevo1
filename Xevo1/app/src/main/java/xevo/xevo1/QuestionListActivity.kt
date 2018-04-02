package xevo.xevo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_question_list.*
import xevo.xevo1.AnswerCategoryFragment.Companion.CATEGORY_DATA
import xevo.xevo1.AnswerQuestion.ReadQuestion
import xevo.xevo1.models.CategoryData

class QuestionListActivity : AppCompatActivity(),
        CaseListFragment.OnFragmentInteractionListener {

    private val TAG = "QuestionListActivity"
    private lateinit var categoryString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        // get categoryData that this activity was started with
        val categoryData: CategoryData = intent.getParcelableExtra(CATEGORY_DATA)

        categoryString = categoryData.dbString

        // add close button
        setSupportActionBar(questionListToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        questionListTitle.text = categoryData.displayString

        val databaseReference = if (categoryData.dbString != "") {
            FirebaseDatabase.getInstance().getReference(
                    "%s%s".format(getString(R.string.db_cases_by_subject), categoryData.dbString))
        } else {
            FirebaseDatabase.getInstance().getReference(getString(R.string.db_cases))
        }

        val frag: XevoFragment = CaseListFragment.newInstance(ReadQuestion::class.java, databaseReference)

        // load list as fragment
        val pendingRunnable = Runnable {
            supportFragmentManager!!.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.questionListFrame, frag, frag.fragmentTag)
                    .commit()
        }
        Handler().post(pendingRunnable)
    }

    override fun getCategoryString(): String {
        return categoryString
    }
}
