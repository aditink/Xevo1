package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_consultant_question_list.*
import xevo.xevo1.models.CaseData


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConsultantQuestionList.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConsultantQuestionList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultantQuestionList : XevoFragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    public override val title: Int = R.string.nav_answer_case
    public override val fragmentTag: String = "answer_case"
    public override val expandable: Boolean = false

    lateinit var categorySpinner : Spinner
    val TAG : String = "ConsultantQuestions"
    private lateinit var handler: Handler

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        categorySpinner = category as Spinner
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultant_question_list, container, false)
        handler = Handler()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handler = Handler()
        categorySpinner = category_spinner as Spinner
        //Eventually get from database
        var categoryList : List<String> =  arrayListOf<String>("category1", "category2", "category3")
        updateCategorySpinner(categoryList)
    }

    private fun updateCategorySpinner(categories : List<String>) {
        val adapter = ArrayAdapter(activity, R.layout.spinner_item, categories)
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);

        setFragment(CaseListFragment.newInstance(ReadQuestion::class.java))
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun setFragment(frag: XevoFragment) {
        // Open fragment with runnable to ensure that there is not
        // lag when switching views
        val pendingRunnable = Runnable {
            fragmentManager!!.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.case_list, frag, frag.fragmentTag)
                    .commit()
        }
        handler.post(pendingRunnable)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConsultantQuestionList.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): ConsultantQuestionList {
            val fragment = ConsultantQuestionList()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
