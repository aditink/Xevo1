package xevo.xevo1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_consultant_questions.*
import kotlinx.android.synthetic.main.content_consultant_questions.*

class ConsultantQuestions : AppCompatActivity() {

    lateinit var categorySpinner : Spinner

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
    }

    private fun updateCategorySpinner(categories : List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

}
