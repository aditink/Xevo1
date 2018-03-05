package xevo.xevo1.enums;

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

    @Override
    public String toString() {
        switch(this) {
            case QUICK_HIT: return "Quick Hit";
            case DEEP_DIVE: return "Deep Dive";
            case HEAVY_LIFT: return "Heavy Lift";
            default: throw new IllegalArgumentException();
        }
    }
}
