package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.content_quick_hit.*
import kotlinx.android.synthetic.main.fragment_question.*

/**
 * QuestionFragment. Subclass of [Fragment].
 * Card that represents essential information of a question
 * Used while presenting a list of questions
 */
class QuestionFragment : XevoFragment(),
    View.OnClickListener {

    public override val fragmentTag = "test"
    public override val title: Int = R.string.question_fragment
    var questionId : String = "placeholder"
    private var mListener: OnFragmentInteractionListener? = null
    public override val expandable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_question, container, false)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var button : Button = question_fragment_button
        button.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(activity, ReadQuestion::class.java)
        intent.putExtra("QuestionID", questionId)
        startActivity(intent)
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
        fun newInstance(questionID : String): QuestionFragment {
            val fragment = QuestionFragment()
            fragment.questionId = questionID
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}