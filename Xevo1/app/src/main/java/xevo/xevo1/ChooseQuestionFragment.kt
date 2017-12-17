package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_choose_question.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChooseQuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChooseQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseQuestionFragment : XevoFragment() {

    public override val title: Int = R.string.nav_question
    public override val fragmentTag: String = "question"

    private var titleId : Int? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            titleId = arguments.getInt(TITLE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_choose_question, container, false)

        // setup button listeners
        val quickHitButton = v.quick_hit_button
        quickHitButton.setOnClickListener {
            val intent = Intent(mContext, QuickHit::class.java)
            startActivity(intent)
        }

        val talkAboutItButton = v.talk_about_it_button
        talkAboutItButton.setOnClickListener {
            val intent = Intent(mContext, TalkAboutIt::class.java)
            startActivity(intent)
        }

        val professionalOpinionButton = v.professional_opinion_button
        professionalOpinionButton.setOnClickListener {
            val intent = Intent(mContext, ProfessionalOpinion::class.java)
            startActivity(intent)
        }
        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mContext = context
            mListener = context
            context.onFragmentInteraction("hi there")
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
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(msg: String)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val TITLE_ID = "titleId"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChooseQuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(title_id: Int): ChooseQuestionFragment {
            val fragment = ChooseQuestionFragment()
            val args = Bundle()
            args.putInt(TITLE_ID, title_id)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
