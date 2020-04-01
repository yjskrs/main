package igrad.model.course;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import igrad.model.module.Grade;
import igrad.model.module.Module;

/**
 * Represents all the (additional) details a Course (there's only one of which), might have e.g, course name, cap, etc
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseInfo {

    // Identity fields

    // All fields in the course info object can be optional, this is the case when the user hasn't done course add cmd
    private final Optional<Name> name;
    private final Optional<Cap> cap;

    // Data fields

    // This constructor is only used for JSON Serialising classes
    public CourseInfo() {
        name = Optional.empty();
        cap = Optional.empty();
    }

    /**
     * Every field must be present and not null.
     */
    public CourseInfo(Optional<Name> name, Optional<Cap> cap) {
        this.name = name;
        this.cap = cap;
    }

    public Optional<Name> getName() {
        return name;
    }

    public Optional<Cap> getCap() {
        return cap;
    }

    /**
     * Given a requirement list, compute the cap based on all modules in the requirements of the
     * requirement list.
     */
    public static Cap computeCap(List<Module> moduleList) {
        double cap = 0;
        /**
         * TODO: Teri, please fill the details. Iterate through all modules in the module list and
         * for each module with a grade, add them together to calcualte the cap
         * Lemme know if you find this too hard, i can do it :)
         */

        int totalNumOfModules = moduleList.size();

        for (int i = 0; i < totalNumOfModules; i++) {
            String grade = moduleList.get(i).getGrade().toString();

            switch (grade) {
            case "A+":
                cap += 5.0;
                break;

            case "A":
                cap += 5.0;
                break;

            case "A-":
                cap += 4.5;
                break;

            case "B+":
                cap += 4.0;
                break;

            case "B":
                cap += 3.5;
                break;

            case "B-":
                cap += 3.0;
                break;

            case "C+":
                cap += 2.5;
                break;

            case "C":
                cap += 2.0;
                break;

            case "D+":
                cap += 1.5;
                break;

            case "D":
                cap += 1.0;
                break;

            case "F":
                cap += 0;
                break;

            default:
                cap = cap;
                break;
            }
        }

        Cap capResult = new Cap(Double.toString(cap));

        return capResult;
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CourseInfo)) {
            return false;
        }

        CourseInfo otherCourseInfo = (CourseInfo) other;

        return otherCourseInfo.getName().equals(getName())
            && otherCourseInfo.getCap().equals(getCap());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {

        Optional<Name> name = getName();

        final StringBuilder builder = new StringBuilder();

        name.ifPresent(x -> builder.append(" Name ").append(x));

        cap.ifPresent(x -> builder.append(" Cap ").append(x));
        return builder.toString();
    }

}
