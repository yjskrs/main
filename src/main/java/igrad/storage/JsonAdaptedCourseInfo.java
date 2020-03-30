package igrad.storage;

import static igrad.commons.core.Messages.MESSAGE_COURSE_NOT_SET;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.requirement.Requirement;

/**
 * Jackson-friendly version of {@link CourseInfo}.
 */
public class JsonAdaptedCourseInfo {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course Info's %s field is missing!";

    private String name;

    /**
     * Constructs a {@code JsonAdaptedCourseInfo} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedCourseInfo(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code CourseInfo} into this class for Jackson use.
     */
    public JsonAdaptedCourseInfo(CourseInfo source) {
        this.name = source.getName().isPresent() ? source.getName().get().value : null;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code CourseInfo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public CourseInfo toModelType(List<Requirement> requirementList) throws IllegalValueException {
        // Course name can be null (in the event that the user hasn't run course add command
        if (name != null && !Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Optional<Name> modelName = name != null
            ? Optional.of(new Name(name))
            : Optional.empty();


        /*
         * However, if course name is null (Optional.empty()), but we still have requirements for
         * it, that's an invalid state and we have to throw an IllegalValueException.
         */

        if (modelName.isEmpty() && !requirementList.isEmpty()) {
            throw new IllegalValueException(MESSAGE_COURSE_NOT_SET);
        }

        /*
         * Else if everything (the state) of the course info is valid, we can then proceed to
         * compute cap (if applicable; course name exists)
         */
        final Optional<Cap> cap = (!modelName.isEmpty() && !requirementList.isEmpty())
            ? Optional.of(CourseInfo.computeCap(requirementList))
            : Optional.empty();

        return new CourseInfo(modelName, cap);
    }
}
