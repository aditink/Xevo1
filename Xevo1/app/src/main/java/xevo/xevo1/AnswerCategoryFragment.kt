package xevo.xevo1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_answer_category.view.*
import kotlinx.android.synthetic.main.fragment_case_list.view.*
import xevo.xevo1.models.CategoryAdapter
import xevo.xevo1.models.CategoryData


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnswerCategoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnswerCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnswerCategoryFragment : XevoFragment() {

    private val TAG = "AnswerCategoryFragment"
    private var mContext: Context? = null

    public override val title: Int = R.string.nav_case_list
    public override val fragmentTag: String = "answer_category"
    public override val expandable: Boolean = false

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_answer_category, container, false)

        v.categoryRecyclerView.layoutManager = GridLayoutManager(mContext, 2)

        val testList: List<CategoryData> = List<CategoryData>(3) { id: Int ->
            when (id) {
                0 -> CategoryData("Mathematics", null)
                1 -> CategoryData("Physics", null)
                2 -> CategoryData("Computer Science", null)
                else -> CategoryData("All", null)
            }
        }

        v.categoryRecyclerView.adapter = CategoryAdapter(testList, { item -> Log.d(TAG, item.category)})

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
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment AnswerCategoryFragment.
         */
        fun newInstance(): AnswerCategoryFragment {
            val fragment = AnswerCategoryFragment()
            return fragment
        }
    }
}// Required empty public constructor
