package xevo.xevo1.enums;

/**
 * Created by aditi on 1/7/18.
 */

public enum XevoSubject {
    MATH("math/") {
        @Override
        public String toString() {
            return "Mathematics";
        }
    },
    COMPUTER_SCIENCE("computer_science/") {
        @Override
        public String toString() {
            return "Computer Science";
        }
    },
    PHYSICS("phys/") {
        @Override
        public String toString() {
            return "Physics";
        }
    };

    public String value;

    XevoSubject(String value) {
        this.value = value;
    }

    public String getDbString() {
        return value;
    }
}
