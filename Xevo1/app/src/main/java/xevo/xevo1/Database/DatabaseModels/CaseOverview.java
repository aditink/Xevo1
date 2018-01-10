package xevo.xevo1.Database.DatabaseModels;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import xevo.xevo1.enums.CaseType;

/**
 * Created by aditi on 1/7/18.
 */

@IgnoreExtraProperties
public class CaseOverview {

    private Object timeStamp;
    private CaseType caseType;
    private String title;
    private String description;
    private String caseId;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class)
     * Setting all variables to empty string / default value.
     */
    public CaseOverview() {
        timeStamp = ServerValue.TIMESTAMP;
        caseType = CaseType.QUICK_HIT;
        title = "";
        description = "";
        caseId = "";
    }

    public CaseOverview(Object timeStamp, CaseType caseType, String title, String description, String caseId) {
        this.timeStamp = timeStamp;
        this.caseType = caseType;
        this.title = title;
        this.description = description;
        this.caseId = caseId;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}