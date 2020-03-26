package igrad.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

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
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedCourseInfo(CourseInfo source) {
        this.name = source.getName().isPresent() ? source.getName().get().value : null;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code CourseInfo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public CourseInfo toModelType() throws IllegalValueException {
        if (name != null && !Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Optional<Name> modelName = name != null
            ? Optional.of(new Name(name))
            : Optional.empty();

        return new CourseInfo(modelName);
    }
}
