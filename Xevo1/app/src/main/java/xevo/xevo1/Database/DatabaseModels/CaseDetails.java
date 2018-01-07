package xevo.xevo1.Database.DatabaseModels;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import xevo.xevo1.enums.Status;
import xevo.xevo1.enums.XevoSubject;

/**
 * Created by aditi on 1/7/18.
 */

@IgnoreExtraProperties
public class CaseDetails {

    private Object timeStamp;
    private CaseType caseType;
    private String title;
    private String description;
    private String consultant;
    private String client;
    private Status status;
    private XevoSubject subject;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class)
     * Setting all variables to empty string / default value.
     */
    public CaseDetails() {
        timeStamp = ServerValue.TIMESTAMP;
        caseType = CaseType.QUICK_HIT;
        title = "";
        description = "";
        consultant = "";
        client = "";
        status = Status.UNANSWERED;
        subject = XevoSubject.COMPUTER_SCIENCE;
    }

    public CaseDetails(Object timeStamp, CaseType caseType, String title,
                       String description, String consultant, String client,
                       Status status, XevoSubject subject) {
        this.timeStamp = timeStamp;
        this.caseType = caseType;
        this.title = title;
        this.description = description;
        this.consultant = consultant;
        this.client = client;
        this.status = status;
        this.subject = subject;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public String getClient() {
        return client;
    }

    public String getConsultant() {
        return consultant;
    }

    public XevoSubject getSubject() {
        return subject;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSubject(XevoSubject subject) {
        this.subject = subject;
    }

    public void setClient(String client) {
        this.client = client;
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
}
