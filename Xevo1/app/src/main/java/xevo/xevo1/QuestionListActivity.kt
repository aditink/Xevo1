package xevo.xevo1

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_question_list.*
import xevo.xevo1.AnswerCategoryFragment.Companion.CATEGORY_DATA
import xevo.xevo1.models.CategoryData

class QuestionListActivity : AppCompatActivity(),
        CaseListFragment.OnFragmentInteractionListener {

    private val TAG = "QuestionListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        // get categoryData that this activity was started with
        val categoryData: CategoryData = intent.getParcelableExtra(CATEGORY_DATA)

        // add close button
        setSupportActionBar(questionListToolbar)
        @TargetApi(21)
        questionListToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        questionListToolbar.setNavigationOnClickListener { _ ->
            finish()
        }

        title = categoryData.displayString
        questionListToolbar.setBackgroundColor(Color.parseColor("#%s".format(categoryData.color)))

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

    override fun onProfileImageUpdated() {
    }
}
