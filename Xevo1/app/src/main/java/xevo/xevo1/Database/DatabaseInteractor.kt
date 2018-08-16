package xevo.xevo1.Database

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.*
import xevo.xevo1.Database.DatabaseModels.CaseDetails
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.Database.DatabaseModels.User
import xevo.xevo1.R
import xevo.xevo1.Util.XevoActivity
import xevo.xevo1.enums.CaseType
import xevo.xevo1.enums.Status
import xevo.xevo1.models.CategoryData
import java.util.HashMap

/**
 * Created by aditi on 4/1/18.
 */
abstract class DatabaseInteractor : XevoActivity() {

    abstract val TAG : String

    //consultant from getConsultantData stored here
    var consultant : User? = null
    //caseDetails from getCaseDetails stored here
    var caseDetails : CaseDetails? = null

//    var tableNames : List<String> = listOf(
//            getString(R.string.db_cases_by_subject),
//            getString(R.string.db_cases),
//            getString(R.string.db_subjects),
//            getString(R.string.db_users),
//            getString(R.string.db_answers),
//            getString(R.string.db_questions),
//            getString(R.string.db_cases_by_users),
//            getString(R.string.db_pending_app)
//            )

    //Uploading functions

    /**
     * Do all database updates necessary when question is answered
     */
    fun uploadAnswer(caseDetails : CaseDetails, consultant : String, caseId : String,
                     database: DatabaseReference, answer : String,
                     newStatus: Status = Status.ANSWERED, oldAnswer : String = "") {
        caseDetails!!.status = newStatus
        caseDetails!!.answer = answer
        caseDetails!!.consultant = consultant
        caseDetails!!.oldAnswer = oldAnswer
        caseDetails!!.isRated = false

        val childUpdates = HashMap<String, Object>()

        childUpdates.put(getString(R.string.db_cases) + caseId, caseDetails as Object)
        var caseOverview: CaseOverview = CaseOverview(caseDetails as CaseDetails)
        childUpdates.put(getString(R.string.db_answers) + caseDetails!!.consultant + "/" + caseId,
                caseOverview as Object)

        database.updateChildren(childUpdates as Map<String, Any>)
    }

    /**
     * Do all updates necessary when new question asked
     */
    fun createCase(title : String, description : String, ref : DatabaseReference,
                   caseType : CaseType, userId : String, category : CategoryData, ctx : Context) {
        val TIMESTAMP = ServerValue.TIMESTAMP
        val caseKey = ref.child(ctx.getString(R.string.db_cases)).push().key

        // to add to case table
        val caseDetails : CaseDetails = CaseDetails(TIMESTAMP, caseType, title, description,
                "", userId, Status.UNANSWERED, category.dbString, caseKey)

        // add to case_data_by_user and _by_subject
        val caseOverview : CaseOverview = CaseOverview(caseType, title, description, caseKey)
        val childUpdates = HashMap<String, Object>()
        childUpdates.put(ctx.getString(R.string.db_cases)+caseKey, caseDetails as Object)
        childUpdates.put(ctx.getString(R.string.db_cases_by_subject) + category.dbString + "/"
                + caseKey, caseOverview as Object)
        childUpdates.put(ctx.getString(R.string.db_questions) + userId + "/"
                + caseKey, caseOverview as Object)

        ref.updateChildren(childUpdates as Map<String, Any>)
    }

    /**
     * Add case to unanswered cases under CasesBySubject table
     */
    fun addCaseToSubject(caseDetails: CaseDetails, ref : DatabaseReference) {
        ref.child(getString(R.string.db_cases_by_subject)).child(caseDetails.subject).
                child(caseDetails.caseId).setValue(CaseOverview(caseDetails))
    }

    /**
     * Update float field in case caseId to value. ref is root of DB.
     */
    fun updateCase_float(value : Float, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }

    /**
     * Update float field in case caseId to value. ref is root of DB.
     */
    fun updateCase_bool(value : Boolean, ref : DatabaseReference, caseId : String, field : String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(value)
    }

    /**
     * Update property field of case caseId to newValue. ref is root of DB.
     */
    fun updateCase_value(newValue: Any, ref: DatabaseReference, caseId: String, field: String) {
        ref.child(this.getString(R.string.db_cases)).child(caseId).child(field).setValue(newValue)
    }

    /**
     * Get reference to consultant consultant
     */
    fun getConsultantRef(rootRef : DatabaseReference, consultant : User) : DatabaseReference {
        return rootRef.child(this.getString(R.string.db_users)).child(consultant.userId)
    }

    /**
     * Get reference to consultant consultant
     */
    fun getConsultantRef(rootRef : DatabaseReference, consultant : String) : DatabaseReference {
        return rootRef.child(this.getString(R.string.db_users)).child(consultant)
    }

    /**
     * Update Subject table with caseDetails
     */
    fun updateSubject(caseOverview : CaseOverview, ref: DatabaseReference, caseDetails: CaseDetails) {
        ref.child(this.getString(R.string.db_cases_by_subject)).child(caseDetails.subject)
                .child(caseDetails.caseId).setValue(caseOverview)
    }

    /**
     * Update rating of consultant consultant with additional andwer rated at rating.
     */
    fun updateRating(consultant: User, ref : DatabaseReference, rating : Float) {
        val consultant_ref = getConsultantRef(ref, consultant)
        val num_cases = consultant.casesAnswered
        if (num_cases == 0) {
            consultant_ref.child("rating").setValue(rating)
            consultant_ref.child("casesAnswered").setValue(1)
        }
        else {
            consultant_ref.child("casesAnswered").setValue(num_cases+1)
            val new_rating = ((consultant.rating*num_cases) + rating)/(num_cases + 1.0F)
            consultant_ref.child("rating").setValue(new_rating)
        }
    }

    //Getting data

    /**
     * Get consultant by consultant id
     */
    fun getConsultantData(ref : DatabaseReference, consultantId: String) {
        val consultant_ref = getConsultantRef(ref, consultantId)
        var valueEventListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(User::class.java)
                if (obj!= null) {
                    consultant = obj
                }
            }
        }
        consultant_ref.addListenerForSingleValueEvent(valueEventListener)
    }

    /**
     * Get caseDetails by case id.
     */
    fun getCaseDetails(ref : DatabaseReference, caseId: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference(
                    getString(R.string.db_cases) + caseId)
            var valueEventListener : ValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError?) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    var obj = dataSnapshot?.getValue(CaseDetails::class.java)
                    if (obj!= null) {
                        caseDetails = obj
                        updateUiWithCaseDetails()
                    }
                }
            }
        databaseReference.addValueEventListener(valueEventListener)
    }

    /**
     * Deletes all Database information, Levaing only table names.
     */
    fun deleteAllData() {
        val databaseReference = FirebaseDatabase.getInstance().getReference();
        //Overwrite with blank collections.
        Log.d(TAG, "deleteAll called")

        var tableNames : List<String> = listOf(
                getString(R.string.db_cases_by_subject),
                getString(R.string.db_cases),
                getString(R.string.db_subjects),
                getString(R.string.db_users),
                getString(R.string.db_answers),
                getString(R.string.db_questions),
                getString(R.string.db_cases_by_users),
                getString(R.string.db_pending_app)
        )

        //TODO do in batch with
        //val childUpdates = HashMap<String, Object>()

        for (table : String in  tableNames) {
            databaseReference.child(table).setValue(null);
        }
    }

    /**
     * Override in child class with all updates to UI based on update in caseDetails.
     */
    open fun updateUiWithCaseDetails() {}
}