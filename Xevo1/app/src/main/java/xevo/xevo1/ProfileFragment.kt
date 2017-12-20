package xevo.xevo1

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.fragment_profile.view.*
import xevo.xevo1.models.Profile
import xevo.xevo1.R.id.imageView
import com.myhexaville.smartimagepicker.ImagePicker
import android.support.annotation.NonNull
import android.content.Intent





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
    private var imagePicker: ImagePicker? = null

    public override val title: Int = R.string.nav_profile
    public override val fragmentTag: String = "profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val mUserData = FirebaseDatabase.getInstance().reference!!.child("Users").child(userId)
        mUserData.addListenerForSingleValueEvent( object:ValueEventListener {
            public override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData  = dataSnapshot.getValue(Profile::class.java)
                user_name.text = "%s %s".format(userData?.firstName, userData?.lastName)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        val v = inflater!!.inflate(R.layout.fragment_profile, container, false)

        imagePicker = ImagePicker(this.activity,
                this,
                { imageUri ->
                    profile_image.setImageURI(imageUri)
                })
                .setWithImageCrop(1, 1)

        v.profile_image!!.setOnClickListener { _ ->
            imagePicker?.choosePicture(true)
        }

        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
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
