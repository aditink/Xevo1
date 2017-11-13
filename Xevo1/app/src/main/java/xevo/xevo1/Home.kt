package xevo.xevo1

import android.support.v4.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity(),
                xevo.xevo1.ChooseQuestionFragment.OnFragmentInteractionListener {

    //Because we sue support libraries, initialise these:
    var fragmentManager: android.support.v4.app.FragmentManager = supportFragmentManager
    var fragmentTransaction: android.support.v4.app.FragmentTransaction = fragmentManager.beginTransaction()

    val choose_question_fragment: Fragment = ChooseQuestionFragment()

    public override fun onFragmentInteraction(uri: Uri) {
        //Leaving this empty for now
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, choose_question_fragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
