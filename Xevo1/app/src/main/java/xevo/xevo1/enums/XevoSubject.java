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
    },
    COMPUTER_SCIENCE {
        @Override
        public String toString() {
            return "Computer Science";
        }
    },
    PHYSICS {
        @Override
        public String toString() {
            return "Physics";
        }
    }
}
