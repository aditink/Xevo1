package xevo.xevo1.enums;

/**
 * Created by aditi on 1/7/18.
 */

public enum XevoSubject {
    MATH {
        @Override
        public String toString() {
            return "Mathematics";
        }
        public String getDbString() {return "math";}
    },
    COMPUTER_SCIENCE {
        @Override
        public String toString() {
            return "Computer Science";
        }
        public String getDbString() {return "computer_science";}
    },
    PHYSICS {
        @Override
        public String toString() {
            return "Physics";
        }
        public String getDbString() {return "phys";}
    }
}
