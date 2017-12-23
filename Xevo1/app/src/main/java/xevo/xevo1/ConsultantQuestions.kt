package xevo.xevo1


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_consultant_questions.*
import kotlinx.android.synthetic.main.content_consultant_questions.*


class ConsultantQuestions : AppCompatActivity(),
        QuestionFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    lateinit var categorySpinner : Spinner
    val TAG : String = "ConsultantQuestions"

    override fun onFragmentInteraction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultant_questions)
        setSupportActionBar(toolbar)
        categorySpinner = category as Spinner
    }

    override fun onStart() {
        super.onStart()

        //Eventually get from database
        var categoryList : List<String> =  arrayListOf<String>("category1", "category2", "category3")
        updateCategorySpinner(categoryList)

        var testFragment : QuestionFragment = QuestionFragment.newInstance()
        addQuestion(testFragment)
    }

    private fun updateCategorySpinner(categories : List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private fun addQuestion(question : QuestionFragment) {
        Log.d(TAG, "adding fragment");

        supportFragmentManager
                .beginTransaction()
                .add(questionsLayout.id, question, question.tag)
                .commit()
    }

}
