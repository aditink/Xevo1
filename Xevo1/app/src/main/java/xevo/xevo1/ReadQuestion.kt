package xevo.xevo1

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_read_question.*

class ReadQuestion : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    var caseId : String? = null
    lateinit var acceptButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_question)
        caseId = intent.getStringExtra("caseId")

        question_details.setMovementMethod(ScrollingMovementMethod())

        acceptButton = accept_button
        acceptButton.setOnClickListener() { view : View ->   onAccept() }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onAccept() {
        val intent = Intent(this, AnswerQuestionActivity::class.java)
        intent.putExtra("caseId", caseId)
        startActivity(intent)
    }
}
