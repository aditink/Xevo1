package xevo.xevo1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_case_list.view.*
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.models.CaseAdapter
import xevo.xevo1.models.CaseData
import java.util.*
import com.google.firebase.database.GenericTypeIndicator
import kotlin.collections.HashMap


/**
 * A [XevoFragment] subclass.
 * Activities that contain this fragment must implement the
 * [CaseListFragment.OnFragmentInteractionListener] interface
 * to allow for communication from Fragment to the Activity.
 * Use the [CaseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaseListFragment : XevoFragment() {

    private val TAG = "CaseListFragment"
    private var mListener: OnFragmentInteractionListener? = null
    private var mContext: Context? = null

    public override val title: Int = R.string.nav_case_list
    public override val fragmentTag: String = "case_list"
    public override val expandable: Boolean = true

    var caseDetailClass : Class<*>? = null
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    var database : DatabaseReference? = null
    lateinit var questionList : MutableCollection<CaseOverview>
    val t = object : GenericTypeIndicator<HashMap<String, CaseOverview>>() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val user = FirebaseAuth.getInstance().currentUser!!

        val v : View = inflater.inflate(R.layout.fragment_case_list, container, false)

        if (database == null) {
            database = FirebaseDatabase.getInstance().getReference(
                    getString(R.string.db_cases_by_users) + userId + "/" +
                            getString(R.string.db_questions))
        }
        v.recyclerView.layoutManager = LinearLayoutManager(mContext)

        var postListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError : DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(t)
                if (obj != null) {
                    questionList = obj.values
                    loadList(v)
                }
            }
        };

        database?.addValueEventListener(postListener);

        // load list
//        val listItems: List<CaseData> = List<CaseData>(20) { id: Int ->
//            when (id) {
//                0 -> CaseData(CaseType.QUICK_HIT, "What is the meaning of life?", "I need a better answer than '42'", "Super hard", null, caseId = Math.random().toInt())
//                1 -> CaseData(CaseType.TALK_ABOUT_IT, "Talk About It", "Description", "Super hard", null, caseId = Math.random().toInt())
//                else -> CaseData(CaseType.PROFESSIONAL, "Professional", "Description", "super easy", null, caseId = Math.random().toInt())
//            }
//        }


        return v

    }

    fun loadList(v : View) {
        val listItems: List<CaseData> = questionList.map { caseOverview : CaseOverview -> CaseData(caseOverview) }
        val adapter = CaseAdapter(listItems) { data:CaseData -> questionDetails(data) }
        v.recyclerView.adapter = adapter
    }

    fun questionDetails(case : CaseData) {
        if (caseDetailClass != null) {
            val intent = Intent(mContext, caseDetailClass)
            intent.putExtra("caseId", case.caseId)
            startActivity(intent)
        }
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
         * @return A new instance of fragment CaseListFragment.
         */
        fun newInstance(): CaseListFragment {
            val fragment = CaseListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(detailsClass : Class<*>): CaseListFragment {
            val fragment = CaseListFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.caseDetailClass = detailsClass
            return fragment
        }

        fun newInstance(detailsClass : Class<*>, ref : DatabaseReference): CaseListFragment {
            val fragment = CaseListFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.caseDetailClass = detailsClass
            fragment.database = ref
            return fragment
        }
    }

}// Required empty public constructor
