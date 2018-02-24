package xevo.xevo1

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat.getDrawable
import kotlinx.android.synthetic.main.activity_consultant_registration.*

import kotlinx.android.synthetic.main.activity_register_consultant.*
import kotlinx.android.synthetic.main.fragment_register_consultant.*
import kotlinx.android.synthetic.main.fragment_register_consultant.view.*

class RegisterConsultant : AppCompatActivity(),
    ExpertiseFragment.OnFragmentInteractionListener {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var numPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_consultant)

        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.consultantRegistrationToolbar)
        // add close button
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        @TargetApi(21)
//        toolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
//        toolbar.setNavigationOnClickListener { _ ->
//            returnResult(Activity.RESULT_CANCELED)
//        }
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        fab.setOnClickListener { view -> addExpertise() }

    }

    private fun addExpertise() {
        mSectionsPagerAdapter?.instantiateItem(container, ++numPages)
        mSectionsPagerAdapter?.notifyDataSetChanged()
//        mSectionsPagerAdapter?.finishUpdate(container)
    }

    override fun onBackPressed() = returnResult(Activity.RESULT_CANCELED)

    private fun returnResult(result: Int) {
        setResult(result, Intent())
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_register_consultant, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }



    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == (0)) {
                return ExpertiseFragment.newInstance(true)
            }
            return ExpertiseFragment.newInstance(false) //position + 1)
        }

//        fun getItem(position: Int, isLast : Boolean): Fragment {
//            // getItem is called to instantiate the fragment for the given page.
//            return ExpertiseFragment.newInstance(isLast) //position + 1)
//        }

        override fun getCount(): Int {
            // Total pages.
            return numPages
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_register_consultant, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
