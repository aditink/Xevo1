package xevo.xevo1

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

/**
 * Main Activity. We go here after the login screen and this handles
 * all of the basic navigation and basic screens. The other screens are
 * fragments that we load into the frame view with FragmentManager. All
 * fragments must be subclasses of XevoFragment.
 */
class Main : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        ChooseQuestionFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

    private lateinit var handler: Handler
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        handler = Handler()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        setFragment(ProfileFragment.newInstance())
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.choose_question, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                setFragment(ProfileFragment.newInstance())
            }

            R.id.nav_question -> {
                setFragment(ChooseQuestionFragment.newInstance())
            }

            R.id.nav_settings -> {
                setFragment(SettingsFragment.newInstance())
            }
        }

        return true
    }

    override fun onFragmentInteraction() {
    }

    private fun setFragment(frag: XevoFragment) {
        // if frag is already being shown, don't do anything
        for (f in supportFragmentManager.fragments) {
            if (f.tag.equals(frag.fragmentTag)) {
                drawerLayout.closeDrawers()
                return
            }
        }

        // Open fragment with runnable to ensure that there is not
        // lag when switching views
        val pendingRunnable = Runnable {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.main_frame, frag, frag.fragmentTag)
            fragmentTransaction.commit()
        }

        // change the activity title to match the fragment title
        supportActionBar!!.setTitle(frag.title)

        drawerLayout.closeDrawers()
        handler.post(pendingRunnable)

    }
}
