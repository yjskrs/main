package igrad.model.course;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import igrad.model.module.Module;
import igrad.model.requirement.Requirement;

/**
 * Represents all the (additional) details a Course (there's only one of which), might have e.g, course name, cap, etc
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseInfo {

    // Identity fields

    /*
     * All fields in the course info object can be optional, this is the case when the user hasn't done course add
     * command, hence they can be Optional.empty(). Conversely, once the course add command has been
     * successful, all fields would NOT be Optional.empty()
     */
    private final Optional<Name> name;
    private final Optional<Cap> cap;
    private final Optional<Credits> credits;

    // Data fields

    // This constructor is only used for JSON Serialising classes
    public CourseInfo() {
        name = Optional.empty();
        cap = Optional.empty();
        credits = Optional.empty();
    }

    /**
     * Every field must be present and not null.
     */
    public CourseInfo(Optional<Name> name, Optional<Cap> cap, Optional<Credits> credits) {
        this.name = name;
        this.cap = cap;
        this.credits = credits;
    }

    public Optional<Name> getName() {
        return name;
    }

    public Optional<Cap> getCap() {
        return cap;
    }

    public Optional<Credits> getCredits() {
        return credits;
    }

    /**
     * Computes and returns a {@code Credits} object which has {@code creditsFulfilled} and
     * {@code creditsRequired}, based  on a list of {@code Requirement}s;
     * {@code requirementList} passed in.
     */
    public static Optional<Credits> computeCredits(List<Requirement> requirementList) {
        // If the requirementList is empty, there's no talk about this, Credits would be Optional.empty
        if (requirementList.isEmpty()) {
            return Optional.empty();
        }

        int totalCreditsRequired = computeCreditsRequired(requirementList);
        int totalCreditsFulfilled = computeCreditsFulfilled(requirementList);

        return Optional.of(new Credits(totalCreditsRequired, totalCreditsFulfilled));
    }

    /**
     * Computes the total number of credits fulfilled in a course by summing up all the {@code creditsFulfilled} in
     * the list of all {@code Requirement}s as passed as tthe {@code requirementList} argument.
     * Returns an integer.
     */
    private static int computeCreditsFulfilled(List<Requirement> requirementList) {
        int creditsFulfilled = 0;

        for (Requirement requirement : requirementList) {
            creditsFulfilled += requirement.getCredits().getCreditsFulfilled();
        }

        return creditsFulfilled;
    }

    /**
     * Computes the total number of credits required in a course by summing up all the {@code creditsRequired} in
     * the list of all {@code Requirement}s as passed as tthe {@code requirementList} argument.
     * Returns an integer.
     */
    private static int computeCreditsRequired(List<Requirement> requirementList) {
        int creditsRequired = 0;

        for (Requirement requirement : requirementList) {
            creditsRequired += requirement.getCredits().getCreditsRequired();
        }

        return creditsRequired;
    }

    /**
     * Given a requirement list, compute the cap based on all modules in the requirements of the
     * requirement list.
     */
    public static Optional<Cap> computeCap(List<Module> moduleList) {
        // If the moduleList is empty, there's no talk about this, Cap would be Optional.empty
        if (moduleList.isEmpty()) {
            return Optional.empty();
        }

        double cap = 0;

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

        return Optional.of(capResult);
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

        /*name.ifPresent(x -> builder.append(" Name ").append(x));
         cap.ifPresent(x -> builder.append(" Cap ").append(x));*/
        name.ifPresent(x -> builder.append(x));

        return builder.toString();
    }

}
