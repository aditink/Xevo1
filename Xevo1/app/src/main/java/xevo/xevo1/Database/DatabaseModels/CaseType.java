package xevo.xevo1.Database.DatabaseModels;

/**
 * Created by aditi on 1/7/18.
 */

public enum CaseType {
    QUICK_HIT (1),
    DEEP_DIVE (2),
    HEAVY_LIFT (3);

    private final int caseTypeIndex;

    CaseType(int caseTypeIndex) {
        this.caseTypeIndex = caseTypeIndex;
    }

    public int getCaseTypeIndex() {
        return this.caseTypeIndex;
    }
}
