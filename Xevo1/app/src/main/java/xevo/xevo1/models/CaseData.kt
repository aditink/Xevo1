package xevo.xevo1.models;

import android.net.Uri
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.enums.CaseType

/**
 * Holds the data for each Case. There are fields
 * for each type of case which means that all fields
 * aren't necessarily used for each individual case.
 */
data class CaseData(
        val type: CaseType,
        val title: String,
        val description: String,
        val difficulty: String,
        val photoUrl: Uri?,
        val caseId: String //Unique question id
) {
    constructor(caseOverview: CaseOverview) : this(
            caseOverview.caseType,
            caseOverview.title,
            caseOverview.description,
            "",
            null,
            caseOverview.caseId
    )
}

///**
// * Enum for Case Types.
// */
//enum class CaseType {
//    QUICK_HIT,
//    TALK_ABOUT_IT,
//    PROFESSIONAL
//}

