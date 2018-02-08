package xevo.xevo1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DisplayCase : AppCompatActivity(),
    ProfileAndString.OnFragmentInteractionListener {

    var caseId : String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_case)
        intent.getStringExtra("caseId")
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
