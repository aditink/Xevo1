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
    private var imagePicker: ImagePicker? = null
//    private var recyclerView: RecyclerView? = null

    public override val title: Int = R.string.nav_profile
    public override val fragmentTag: String = "profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val user = FirebaseAuth.getInstance().currentUser!!

        // Inflate the list_case_item for this fragment


        val v = inflater.inflate(R.layout.fragment_profile, container, false)

//        v.recyclerView.setHasFixedSize(true)
        v.recyclerView.layoutManager = LinearLayoutManager(mContext)

        // load list
        val listItems: List<CaseData> = List<CaseData>(20) { id: Int ->
            when (id) {
                0 -> CaseData("Title", "Description")
                else -> CaseData("Else", "Description")
            }
        }

        val adapter = CaseAdapter(listItems) { data:CaseData -> println("%s %s".format(data.title, data.description)) }
        v.recyclerView.adapter = adapter
        
        // Load user data (photo and displayName)
//        v.user_name.text = user.displayName!!
//        if (user.photoUrl != null) {
//            Glide.with(this).load(user.photoUrl).into(v.profile_image)
//        }

        // Create the image picker
        imagePicker = ImagePicker(this.activity,
                this,
                { imageUri ->
                    updateProfileImage(imageUri)
                })
                .setWithImageCrop(1, 1)

//        v.profile_image!!.setOnClickListener { _ ->
//            imagePicker?.choosePicture(true)
//        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker?.handleActivityResult(resultCode, requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker?.handlePermission(requestCode, grantResults)
    }

    /**
     * Updates the profileImage that is displayed
     * and updates the photoUrl for the user stored
     * with Firebase.
     */
    private fun updateProfileImage(imageUri: Uri) {
//        profile_image.setImageURI(imageUri)

        val user = FirebaseAuth.getInstance().currentUser!!
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUri)
                .build()

        user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mListener?.onProfileImageUpdated()
                        Log.d(TAG, "Updated profile image successfully")
                    }
                }
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
