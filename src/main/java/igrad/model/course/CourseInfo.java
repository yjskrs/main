package igrad.model.course;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.model.course.Cap.CAP_ZERO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.Semester;
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
    private final Optional<Semesters> semesters;

    // Data fields

    /*
     * Usually, Model entity classes such as model.Module.java, do not have empty no-arg constructor
     * like this. However, since this class is instantiated in ModelMananger.java, using the usual
     * 4-arg cosntructor would seem messy.
     */
    public CourseInfo() {
        name = Optional.empty();
        cap = Optional.empty();
        credits = Optional.empty();
        semesters = Optional.empty();
    }

    /**
     * Every field must be present and not null.
     */
    public CourseInfo(Optional<Name> name, Optional<Cap> cap, Optional<Credits> credits,
                      Optional<Semesters> semesters) {
        requireAllNonNull(name, cap, credits, semesters);

        this.name = name;
        this.cap = cap;
        this.credits = credits;
        this.semesters = semesters;
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

    public Optional<Semesters> getSemesters() {
        return semesters;
    }

    /**
     * Computes and returns a {@code Credits} object which has {@code creditsFulfilled} and
     * {@code creditsRequired}, based  on a list of {@code Requirement}s;
     * {@code requirementList} passed in.
     */
    public static Optional<Credits> computeCredits(List<Requirement> requirementList) {
        requireAllNonNull(requirementList);

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
     * Computes and returns a {@code Optional<Cap>} object based on a list of {@code Requirement}s;
     * in {@code requirementList} and list of {@code Module}s in {@code moduleList} passed in.
     */
    public static Optional<Cap> computeCap(List<Module> moduleList, List<Requirement> requirementList) {
        requireAllNonNull(moduleList, requirementList);

        /*
         * If the moduleList or requirementList is empty, there's no talk about this, Cap would be
         * Optional.empty, because there is (literally) nothing by which cap could be computed upon
         */
        if (moduleList.isEmpty() || requirementList.isEmpty()) {
            return Optional.empty();
        }

        double totalCredits = 0;

        double totalModuleCredits = 0;

        int totalNumOfModules = moduleList.size();

        for (int i = 0; i < totalNumOfModules; i++) {
            Module module = moduleList.get(i);

            /*
             * Firstly, we've to check if that module belongs to any requirement. If it doesn't
             * then we can't add that into the final Cap.
             */
            boolean modPresentInAnyReq = requirementList.stream()
                .filter(requirement -> requirement.hasModule(module))
                .findFirst()
                .isPresent();

            if (!modPresentInAnyReq) {
                continue;
            }

            // Now if the module belongs to at least one requirement, we try to compute cap.
            Optional<Grade> grade = module.getGrade();

            // However, if the module does not have any grade, don't bother computing, just skip.
            if (grade.isEmpty()) {
                continue;
            }

            int moduleCredits = module.getCredits().toInteger();

            totalModuleCredits += moduleCredits;

            totalCredits += (grade.get().getGradeValue() * moduleCredits);

            if (grade.get().isSuGrade()) {
                totalModuleCredits -= moduleCredits;
            }
        }

        Cap capResult;

        if (totalModuleCredits == 0) {
            capResult = CAP_ZERO;
        } else {
            capResult = new Cap(Double.toString(totalCredits / totalModuleCredits));
        }

        return Optional.of(capResult);
    }

    /**
     * Computes and returns a {@code Optional<Semesters>} object based on {@code Optional<Semesters>} object and a
     * list of {@code Module}s passed in.
     */
    public static Optional<Semesters> computeSemesters(Optional<Semesters> semesters, List<Module> moduleList) {
        requireAllNonNull(semesters, moduleList);

        if (moduleList.isEmpty()) {
            return Optional.of(new Semesters(semesters.get().toString()));
        }

        int totalSemester = semesters.get().getTotalSemesters();
        int remainingSemesters = computeRemainingSemesters(moduleList);

        return Optional.of(new Semesters(totalSemester, remainingSemesters));
    }

    /**
     * Computes and returns an Integer representing remaining semesters based on a list of {@Module}s passed in.
     */
    private static int computeRemainingSemesters(List<Module> moduleList) {
        //If module list is empty, no semesters have been done yet
        if (moduleList.isEmpty()) {
            return 0;
        }

        int totalNumOfModules = moduleList.size();
        int latestFinishedSem = 0;

        for (int i = 0; i < totalNumOfModules; i++) {
            Optional<Grade> grade = moduleList.get(i).getGrade();

            if (grade.isEmpty()) {
                continue;
            }

            Optional<Semester> semester = moduleList.get(i).getSemester();

            if (semester.isEmpty()) {
                continue;
            }

            int semesterValue = semester.get().getValue();
            if (semesterValue > latestFinishedSem) {
                latestFinishedSem = semesterValue;
            }
        }

        int year = latestFinishedSem / 10;
        int sem = latestFinishedSem % 10;
        int totalSems = 0;

        if (year > 0) {
            totalSems = ((year - 1) * 2);
        }

        totalSems += sem;

        return totalSems;
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
