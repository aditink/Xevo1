package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat.getColor
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_answer_category.view.*
import kotlinx.android.synthetic.main.fragment_case_list.view.*
import xevo.xevo1.models.CategoryAdapter
import xevo.xevo1.models.CategoryData
import android.util.TypedValue
import com.google.firebase.database.*
import xevo.xevo1.Util.ResourceTransformation

const val CATEGORY_DATA = "xevo.xevo1.CATEGORY_DATA"

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
    private var mListener: OnFragmentInteractionListener? = null
    private var mContext: Context? = null

    public override val title: Int = R.string.nav_categories
    public override val fragmentTag: String = "answer_category"
    public override val expandable: Boolean = false

    private var categoryList: MutableList<CategoryData> = mutableListOf()
    var database : DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_answer_category, container, false)

        v.categoryRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        v.categoryRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, ResourceTransformation.dpToPx(resources, 2), true))
        v.categoryRecyclerView.itemAnimator = DefaultItemAnimator()

        database = FirebaseDatabase.getInstance().reference

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                categoryList.clear()
                dataSnapshot!!.children.mapNotNullTo(categoryList) {
                    Log.d(TAG, it.toString())

                    val data = it.getValue<CategoryData>(CategoryData::class.java)
                    data?.dbString = it.key
                    data
                }
                updateList(v)
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        }

        database!!.child("Subjects").addValueEventListener(dataListener)


        return v
    }

    private fun updateList(view: View) {
        val adapter = CategoryAdapter(categoryList, { item ->
            startActivity(Intent(mContext, QuestionListActivity::class.java).apply {
                putExtra(CATEGORY_DATA, item)
            })
        })
        view.categoryRecyclerView.adapter = adapter
        Log.d(TAG, categoryList.toString())
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

    class GridSpacingItemDecoration(val spanCount: Int, val spacing: Int, val includeEdge: Boolean): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            val position = parent!!.getChildAdapterPosition(view)
            val col = position % spanCount

            if (includeEdge) {
                outRect?.left = spacing - col * spacing / spanCount
                outRect?.right = (col + 1) * spacing / spanCount
                if (position < spanCount) { // top edge
                    outRect?.top = spacing
                }
                outRect?.bottom = spacing
            } else {
                outRect?.left = col * spacing / spanCount
                outRect?.right = spacing - (col + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect?.top = spacing
                }
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
