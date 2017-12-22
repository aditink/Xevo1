package xevo.xevo1

import android.content.ContentResolver
import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.nav_header_choose_question.*
import xevo.xevo1.models.Profile
import android.support.annotation.NonNull
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_choose_question.view.*


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

    private val TAG = "MainActivity"

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

        updateNavViewData(navView.getHeaderView(0))

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

    override fun onProfileImageUpdated() {
        val user = FirebaseAuth.getInstance().currentUser!!
        Glide.with(this).load(user.photoUrl).into(imageView)
    }

    private fun updateNavViewData(view: View) {
        val user = FirebaseAuth.getInstance().currentUser!!
        view.nav_user_name.text = user.displayName
        view.nav_email.text = user.email
        if (user.photoUrl != null) {
            Glide.with(this).load(user.photoUrl).into(view.imageView)
        } else {
            view.imageView.setImageURI(drawableToUri(R.drawable.ic_menu_camera))
        }

    }

    private fun drawableToUri(drawableId: Int): Uri {
        return Uri.parse("%s://%s/%s/%s".format(ContentResolver.SCHEME_ANDROID_RESOURCE,
                resources.getResourcePackageName(drawableId),
                resources.getResourceTypeName(drawableId),
                resources.getResourceEntryName(drawableId)))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (f in fragments) {
                if (f is ProfileFragment)
                    (f as? ProfileFragment)?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}
