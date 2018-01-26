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
import kotlinx.android.synthetic.main.nav_header.*
import android.content.Intent
import android.net.Uri
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import xevo.xevo1.R.id.appBarLayout
import xevo.xevo1.R.id.collapse_toolbar
import xevo.xevo1.Util.ResourceTransformation
import java.util.*
import android.R.id.edit
import android.app.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import xevo.xevo1.enums.Consultant

/**
 * Main Activity. We go here after the login screen and this handles
 * all of the basic navigation and basic screens. The other screens are
 * fragments that we load into the frame view with FragmentManager. All
 * fragments must be subclasses of XevoFragment.
 */
class Main : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        CaseListFragment.OnFragmentInteractionListener,
        ChooseQuestionFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        ConsultantQuestionList.OnFragmentInteractionListener,
        AnswerCategoryFragment.OnFragmentInteractionListener {

    private val TAG = "MainActivity"
    private var appBarExpanded = true // is the appbar expanded
    private var drawPlus = false // draw the plus in the toolbar
    private lateinit var currentFragment: XevoFragment // fragment that is currently being shown
    private var fragmentStack: Stack<XevoFragment> = Stack() // keeps track of the back stack
    private lateinit var handler: Handler
    private lateinit var drawerLayout: DrawerLayout
    lateinit var database : FirebaseDatabase
    val userId = FirebaseAuth.getInstance().currentUser!!.uid

    private val ACTIVITY_CONSULTANT_APPLICATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Check availability of google play services
        database = FirebaseDatabase.getInstance()

        val myRef = database.reference

        refreshMessageToken()

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

        // Load profile information into the NavigationDrawer
        val user = FirebaseAuth.getInstance().currentUser!!
        val view = navView.getHeaderView(0)
        view.nav_user_name.text = user.displayName
        view.nav_email.text = user.email
        if (user.photoUrl != null) {
            Glide.with(this).load(user.photoUrl).into(view.imageView)
        } else {
            view.imageView.setImageURI(ResourceTransformation.drawableToUri(resources, R.drawable.ic_menu_camera))
        }

        updateIsConsultant(Consultant.NONE)
        myRef.child("Users/%s/isConsultant/".format(userId)).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val isConsultant = dataSnapshot!!.getValue(Consultant::class.java)
                if (isConsultant != null) {
                    updateIsConsultant(isConsultant)
                }
            }

            override fun onCancelled(error: DatabaseError?) {}
        })


        // fab listener
        newCaseButton.setOnClickListener { _ -> onAddPressed() }

        // used to update the toolbar when the appbar is retracted
        appBarLayout.addOnOffsetChangedListener { _, _ ->
            invalidateOptionsMenu()
            appBarExpanded = newCaseButton.visibility == View.VISIBLE
        }

        // load first fragment
        currentFragment = CaseListFragment.newInstance()
        setFragment(currentFragment, true)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (!fragmentStack.empty()){
            // handles pressing back with fragments
            setFragment(fragmentStack.pop(), false)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Called when we invalidate the options menu. Used for
     * showing and hiding the plus icon in the toolbar.
     */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (!appBarExpanded && drawPlus) {
            menu.add("Add")
                    .setIcon(R.drawable.ic_plus_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return super.onPrepareOptionsMenu(menu)
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
        Log.d(TAG, "Id: %d".format(item.itemId))

        when (item.itemId) {
            R.id.action_settings -> return true
            else -> { }
        }

        when (item.title) {
            "Add" -> onAddPressed() //TODO: Replace with android strings
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                setFragment(CaseListFragment.newInstance(), true)
            }

            R.id.nav_question -> {
                setFragment(ChooseQuestionFragment.newInstance(), true)
            }

            R.id.nav_settings -> {
                setFragment(SettingsFragment.newInstance(), true)
            }

            R.id.nav_answer_question -> {
                setFragment(AnswerCategoryFragment.newInstance(), true)
            }

            R.id.nav_register_as_consultant -> {
                startActivityForResult(Intent(this, ConsultantRegistrationActivity::class.java), ACTIVITY_CONSULTANT_APPLICATION)
            }

            R.id.nav_admin_applications -> {
                startActivity(Intent(this, AdminActivity::class.java))
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ACTIVITY_CONSULTANT_APPLICATION -> {
                when(resultCode) {
                    Activity.RESULT_OK -> updateIsConsultant(Consultant.PENDING)
                    else -> updateIsConsultant(Consultant.NONE)
                }
            }
        }
    }

    override fun onFragmentInteraction() {
    }

    /**
     * Called from [CaseListFragment]. Needs to be implemnted.
     */
    //TODO: A real string should be returned here
    override fun getCategoryString() : String {
        return ""
    }

    private fun onAddPressed() {
        setFragment(ChooseQuestionFragment.newInstance(), true)
    }

    private fun refreshMessageToken() {
        val pref = getSharedPreferences(getString(R.string.fcm), Context.MODE_PRIVATE)
        val token = pref.getString("fcm", null); // getting String
        if (token != null) {
            val ref = FirebaseDatabase.getInstance().getReference(
                    getString(R.string.db_users) + userId + "/device")
            ref.setValue(token)
        }
        // If token is null, then it is probably being generated right now,
        // so FirebaseDeviceIdService will handle the upload.
    }

    private fun updateIsConsultant(isConsultant: Consultant) {
//        if (isConsultant) {
//            nav_view.menu.setGroupVisible(R.id.is_consultant, true)
//            nav_view.menu.setGroupVisible(R.id.is_not_consultant, false)
////            currentFragment = AnswerCategoryFragment.newInstance()
//        }
//        else {
////            currentFragment = CaseListFragment.newInstance()
//            nav_view.menu.setGroupVisible(R.id.is_consultant, false)
//            nav_view.menu.setGroupVisible(R.id.is_not_consultant, true)
//        }

        when(isConsultant) {
            Consultant.NONE -> {
                nav_view.menu.setGroupVisible(R.id.is_admin, false)
                nav_view.menu.setGroupVisible(R.id.is_consultant, false)
                nav_view.menu.setGroupVisible(R.id.is_not_consultant, true)
                nav_view.menu.setGroupVisible(R.id.is_pending_consultant, false)
            }
            Consultant.PENDING -> {
                nav_view.menu.setGroupVisible(R.id.is_admin, false)
                nav_view.menu.setGroupVisible(R.id.is_consultant, false)
                nav_view.menu.setGroupVisible(R.id.is_not_consultant, false)
                nav_view.menu.setGroupVisible(R.id.is_pending_consultant, true)
            }
            Consultant.VERIFIED -> {
                nav_view.menu.setGroupVisible(R.id.is_admin, false)
                nav_view.menu.setGroupVisible(R.id.is_consultant, true)
                nav_view.menu.setGroupVisible(R.id.is_not_consultant, false)
                nav_view.menu.setGroupVisible(R.id.is_pending_consultant, false)
            }
            Consultant.ADMIN -> {
                nav_view.menu.setGroupVisible(R.id.is_admin, true)
                nav_view.menu.setGroupVisible(R.id.is_consultant, true)
                nav_view.menu.setGroupVisible(R.id.is_not_consultant, false)
                nav_view.menu.setGroupVisible(R.id.is_pending_consultant, false)
            }
        }
    }

    /**
     * Loads a given [XevoFragment] instance into the
     * frame view and sets the title of the view to
     * [XevoFragment.title].
     */
    private fun setFragment(frag: XevoFragment, addToBackStack: Boolean) {
        drawerLayout.closeDrawers()

        // if frag is already being shown, don't do anything
//        for (f in supportFragmentManager.fragments) {
//            if (f.tag.equals(frag.fragmentTag)) {
//                return
//            }
//        }

        // Open fragment with runnable to ensure that there is not
        // lag when switching views
        val pendingRunnable = Runnable {
            if (addToBackStack) fragmentStack.push(currentFragment)
            currentFragment = frag
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.frameView, frag, frag.fragmentTag)
                    .commit()
        }
        handler.post(pendingRunnable)

        // change the activity title to match the fragment title
        collapse_toolbar.title = getString(frag.title)
        appBarLayout.setExpanded(frag.expandable, true)
        drawPlus = frag.expandable
    }
}
