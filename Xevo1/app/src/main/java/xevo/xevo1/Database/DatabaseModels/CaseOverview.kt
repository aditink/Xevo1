package xevo.xevo1.Database.DatabaseModels;

//import com.google.firebase.database.IgnoreExtraProperties;
//import com.google.firebase.database.ServerValue;
//
//import xevo.xevo1.enums.CaseType;

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.database.ServerValue
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import xevo.xevo1.enums.CaseType

/**
 * Created by aditi on 1/7/18.
 */

@SuppressLint("ParcelCreator")
@Parcelize
public data class CaseOverview(
//        @RawValue val timeStamp: Object = ServerValue.TIMESTAMP,
        val caseType: CaseType = CaseType.QUICK_HIT,
        val title: String = "",
        val description: String = "",
        val caseId: String = ""
) : Parcelable {
    constructor(caseDetails: CaseDetails) : this (
//            caseDetails.timeStamp,
            caseDetails.caseType,
            caseDetails.title,
            caseDetails.description,
            caseDetails.caseId
    )
}

//@IgnoreExtraProperties
//public class CaseOverview {
//
//    private Object timeStamp;
//    private CaseType caseType;
//    private String title;
//    private String description;
//    private String caseId;
//
//    /**
//     * Default constructor required for calls to DataSnapshot.getValue(User.class)
//     * Setting all variables to empty string / default value.
//     */
//    public CaseOverview() {
//        timeStamp = ServerValue.TIMESTAMP;
//        caseType = CaseType.QUICK_HIT;
//        title = "";
//        description = "";
//        caseId = "";
//    }
//
//    public CaseOverview(CaseDetails caseDetails) {
//        timeStamp = caseDetails.getTimeStamp();
//        caseType = caseDetails.getCaseType();
//        title = caseDetails.getTitle();
//        description = caseDetails.getDescription();
//        caseId = caseDetails.getCaseId();
//    }
//
//    public CaseOverview(Object timeStamp, CaseType caseType, String title, String description, String caseId) {
//        this.timeStamp = timeStamp;
//        this.caseType = caseType;
//        this.title = title;
//        this.description = description;
//        this.caseId = caseId;
//    }
//
//    public CaseType getCaseType() {
//        return caseType;
//    }
//
//    public Object getTimeStamp() {
//        return timeStamp;
//    }
//
//    public String getCaseId() {
//        return caseId;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setCaseType(CaseType caseType) {
//        this.caseType = caseType;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setTimeStamp(Object timeStamp) {
//        this.timeStamp = timeStamp;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setCaseId(String caseId) {
//        this.caseId = caseId;
//    }
//}