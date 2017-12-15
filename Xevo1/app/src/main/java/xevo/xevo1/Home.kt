package xevo.xevo1

import android.content.Intent
import android.support.v4.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity(),
                xevo.xevo1.ChooseQuestionFragment.OnFragmentInteractionListener,
                xevo.xevo1.ProfileFragment.OnFragmentInteractionListener,
                xevo.xevo1.Settings.OnFragmentInteractionListener {

    // Because we sue support libraries, initialise these:
    var fragmentManager: android.support.v4.app.FragmentManager = supportFragmentManager
    var fragmentTransaction: android.support.v4.app.FragmentTransaction = fragmentManager.beginTransaction()

    val choose_question_fragment: Fragment = ChooseQuestionFragment()
    val home_fragment: Fragment = ProfileFragment()
    val settings_fragment: Fragment = Settings()

    public override fun onFragmentInteraction(uri: Uri) {
        // Leaving this empty for now
    }

    // On Click methods:
    fun openQuickHitPage(view: View) {
        val intent = Intent(this, QuickHit::class.java)
        startActivity(intent)
    }


    fun openTalkAboutItPage(view: View) {
        val intent = Intent(this, TalkAboutIt::class.java)
        startActivity(intent)
    }


    fun openProfessionalOpinionPage(view: View) {
        val intent = Intent(this, ProfessionalOpinion::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, home_fragment).commit()
    }
}
