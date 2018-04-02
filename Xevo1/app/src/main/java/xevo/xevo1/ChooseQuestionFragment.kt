package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_choose_question.view.*
import xevo.xevo1.AskQuestion.QuickHit
import xevo.xevo1.enums.CaseType


/**
 * A [XevoFragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChooseQuestionFragment.OnFragmentInteractionListener] interface
 * to allow for communication from Fragment to the Activity.
 * Use the [ChooseQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseQuestionFragment : XevoFragment() {

    public override val title: Int = R.string.nav_question
    public override val fragmentTag: String = "question"

    private var mListener: OnFragmentInteractionListener? = null
    private var mContext: Context? = null
    public override val expandable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the list_case_item for this fragment
        val v = inflater.inflate(R.layout.fragment_choose_question, container, false)

        // setup button listeners
        val quickHitButton = v.quick_hit_button
        quickHitButton.setOnClickListener {
            val intent = Intent(mContext, QuickHit::class.java)
            intent.putExtra("questionType", CaseType.QUICK_HIT)
            startActivity(intent)
        }

        val talkAboutItButton = v.talk_about_it_button
        talkAboutItButton.setOnClickListener {
            val intent = Intent(mContext, QuickHit::class.java)
            intent.putExtra("questionType", CaseType.DEEP_DIVE)
            startActivity(intent)
        }

        val professionalOpinionButton = v.professional_opinion_button
        professionalOpinionButton.setOnClickListener {
            val intent = Intent(mContext, QuickHit::class.java)
            intent.putExtra("questionType", CaseType.HEAVY_LIFT)
            startActivity(intent)
        }

        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mContext = context
            mListener = context
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
        fun onFragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ChooseQuestionFragment.
         */
        fun newInstance(): ChooseQuestionFragment {
            val fragment = ChooseQuestionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
