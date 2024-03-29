package xevo.xevo1.Database.DatabaseModels;

//import android.annotation.SuppressLint
//import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import kotlinx.android.parcel.Parcelize;
import xevo.xevo1.enums.CaseType;
import xevo.xevo1.enums.Status;
import xevo.xevo1.enums.XevoSubject;
import xevo.xevo1.models.CategoryData;

/**
 * Created by aditi on 1/7/18.
 */

//@SuppressLint("ParcelCreator")
//@Parcelize
//public data class CaseDetails(
////        val timeStamp: Object,
//        val caseType: CaseType,
//        val title: String,
//        val description: String,
//        val consultant: String,
//        val client: String,
//        val status: Status,
//        val subject: XevoSubject,
//        val answer: String,
//        val caseId: String
//) : Parcelable
@IgnoreExtraProperties
public class CaseDetails {

    private Object timeStamp;
    private CaseType caseType;
    private String title;
    private String description;
    private String consultant;
    private String client;
    private Status status;
    private String subject;
    private String answer;
    private String oldAnswer;
    private String caseId;
    private Boolean isRated;
    private Float rating;
    private String rejectionExplanation;
    private String moderatorReaction;
    private String originalConsultantReaction;
    private boolean wasRejected;
    private float oldRating;

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
        subject = "";
        answer = "";
        caseId = "";
        isRated = false;
        rating = 0.0F;
    }

    public CaseDetails(Object timeStamp, CaseType caseType, String title,
                       String description, String consultant, String client,
                       Status status, String subject, String caseId) {
        this.timeStamp = timeStamp;
        this.caseType = caseType;
        this.title = title;
        this.description = description;
        this.consultant = consultant;
        this.client = client;
        this.status = status;
        this.subject = subject;
        this.answer = "";
        this.caseId = caseId;
        this.isRated = false;
        this.rating = 0.0F;
        this.rejectionExplanation = "";
        this.oldAnswer = "";
        wasRejected = false;
    }

    public CaseDetails(Object timeStamp, CaseType caseType, String title,
                       String description, String consultant, String client,
                       Status status, String subject, String answer,
                       String caseId, boolean isRated, float rating,
                       String rejectionExplanation, String oldAnswer,
                       boolean wasRejected, float oldRating) {
        this.timeStamp = timeStamp;
        this.caseType = caseType;
        this.title = title;
        this.description = description;
        this.consultant = consultant;
        this.client = client;
        this.status = status;
        this.subject = subject;
        this.answer = answer;
        this.caseId = caseId;
        this.isRated = isRated;
        this.rating = rating;
        this.rejectionExplanation = rejectionExplanation;
        this.oldAnswer = oldAnswer;
        this.wasRejected = wasRejected;
        this.oldRating = oldRating;
    }

    public CaseDetails(Object timeStamp, CaseType caseType, String title,
                       String description, String consultant, String client,
                       Status status, String subject, String answer, String caseId) {
        this.timeStamp = timeStamp;
        this.caseType = caseType;
        this.title = title;
        this.description = description;
        this.consultant = consultant;
        this.client = client;
        this.status = status;
        this.subject = subject;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public String getSubject() {
        return subject;
    }

    public String getCaseId() {
        return caseId;
    }

    public Boolean getIsRated() {
        return isRated;
    }

    public Float getRating() {
        return rating;
    }

    public String getRejectionExplanation() {
        return rejectionExplanation;
    }

    public String getModeratorReaction() {
        return moderatorReaction;
    }

    public String getOriginalConsultantReaction() {
        return originalConsultantReaction;
    }

    public String getOldAnswer() {
        return oldAnswer;
    }

    public boolean getWasRejected() {
        return wasRejected;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSubject(String subject) {
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

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsRated(Boolean rated) {
        isRated = rated;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setRejectionExplanation(String rejectionExplanation) {
        this.rejectionExplanation = rejectionExplanation;
    }

    public void setModeratorReaction(String moderatorReaction) {
        this.moderatorReaction = moderatorReaction;
    }

    public void setOriginalConsultantReaction(String originalConsultantReaction) {
        this.originalConsultantReaction = originalConsultantReaction;
    }

    public void setOldAnswer(String oldAnswer) {
        this.oldAnswer = oldAnswer;
    }

    public void setWasRejected(boolean wasRejected) {
        this.wasRejected = wasRejected;
    }
}
