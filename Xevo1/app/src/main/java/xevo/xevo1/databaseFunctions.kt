package xevo.xevo1

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import xevo.xevo1.Database.DatabaseModels.CaseDetails

/**
 * Created by aditi on 3/24/18.
 */
class databaseFunctions {

    public fun getCaseDetails(caseId: String, TAG: String): CaseDetails? {
        var caseDetails: CaseDetails? = null;
        val databaseReference = FirebaseDatabase.getInstance().getReference(
                R.string.db_cases.toString() + caseId)
        var valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.w(TAG, "loadPost:onCancelled", databaseError?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var obj = dataSnapshot?.getValue(CaseDetails::class.java)
                if (obj != null) {
                    caseDetails = obj
//                isRated = caseDetails.rated
//                updateUI(caseDetails)
                }
            }
        }
        databaseReference.addListenerForSingleValueEvent(valueEventListener)
        return caseDetails
    }
}