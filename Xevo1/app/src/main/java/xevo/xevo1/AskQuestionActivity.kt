package xevo.xevo1

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.R
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.Status
import xevo.xevo1.enums.XevoSubject
import java.util.HashMap
import android.databinding.adapters.NumberPickerBindingAdapter.setValue



/**
 * Created by aditi on 1/8/18.
 */
open class AskQuestionActivity : AppCompatActivity() {

    fun createCase(title : String, description : String, ref : DatabaseReference,
                   caseType : CaseType, userId : String, SUBJECT : XevoSubject, ctx : Context) {
        var TIMESTAMP = ServerValue.TIMESTAMP
        var caseKey = ref.child(ctx.getString(R.string.db_cases)).push().key

        //to add to case table
        var caseDetails : CaseDetails = CaseDetails(TIMESTAMP, caseType, title, description,
                "", userId, Status.UNANSWERED, SUBJECT, caseKey)

        //add to case_data_by_user and _by_subject
//        var caseOverview : CaseOverview = CaseOverview(TIMESTAMP, caseType, title, description, caseKey)
        var caseOverview : CaseOverview = CaseOverview(caseType, title, description, caseKey)
//
        val childUpdates = HashMap<String, Object>()
        childUpdates.put(ctx.getString(R.string.db_cases)+caseKey, caseDetails as Object)
        childUpdates.put(ctx.getString(R.string.db_cases_by_subject) + SUBJECT.dbString + caseKey, caseOverview as Object)
        childUpdates.put(ctx.getString(R.string.db_questions) + userId + "/" + caseKey, caseOverview as Object)

        ref.updateChildren(childUpdates as Map<String, Any>)
    }
}