package xevo.xevo1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_read_question.*
import kotlinx.android.synthetic.main.fragment_question.*

class ReadQuestion : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    var questionID : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_question)
        questionID = intent.getStringExtra("questionID")

        question_details.setMovementMethod(ScrollingMovementMethod())
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
