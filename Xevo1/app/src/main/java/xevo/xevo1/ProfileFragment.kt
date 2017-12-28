package xevo.xevo1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import android.util.Log
import kotlinx.android.synthetic.main.fragment_profile.view.*
import com.myhexaville.smartimagepicker.ImagePicker
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.app_bar_main.*
import xevo.xevo1.models.CaseAdapter
import xevo.xevo1.models.CaseData
import xevo.xevo1.models.CaseType

/**
 * A [XevoFragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to allow for communication from Fragment to the Activity.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : XevoFragment() {

    private val TAG = "ProfileFragment"
    private var mListener: OnFragmentInteractionListener? = null
    private var mContext: Context? = null

    public override val title: Int = R.string.nav_profile
    public override val fragmentTag: String = "profile"
    public override val expandable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val user = FirebaseAuth.getInstance().currentUser!!

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        v.recyclerView.layoutManager = LinearLayoutManager(mContext)

        // load list
        val listItems: List<CaseData> = List<CaseData>(20) { id: Int ->
            when (id) {
                0 -> CaseData(CaseType.QUICK_HIT, "What is the meaning of life?", "I need a better answer than '42'", "Super hard", null)
                1 -> CaseData(CaseType.TALK_ABOUT_IT, "Talk About It", "Description", "Super hard", null)
                else -> CaseData(CaseType.PROFESSIONAL, "Professional", "Description", "super easy", null)
            }
        }

        val adapter = CaseAdapter(listItems) { data:CaseData -> println("%s %s".format(data.title, data.description)) }
        v.recyclerView.adapter = adapter

        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
            mContext = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun onProfileImageUpdated()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProfileFragment.
         */
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
