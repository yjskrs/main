package igrad.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;

/**
 * An Immutable CourseBook that is serializable to JSON format.
 */
@JsonRootName(value = "coursebook")
class JsonSerializableCourseBook {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_REQUIREMENT = "Requirement list contains duplicate requirement(s).";

    private final JsonAdaptedCourseInfo courseInfo;
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedRequirement> requirements = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCourseBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableCourseBook(@JsonProperty("courseInfo") JsonAdaptedCourseInfo courseInfo,
                                      @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                      @JsonProperty("requirements") List<JsonAdaptedRequirement> requirements) {
        this.courseInfo = courseInfo;
        this.modules.addAll(modules);
        this.requirements.addAll(requirements);
    }

    /**
     * Converts a given {@code ReadOnlyCourseBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCourseBook}.
     */
    public JsonSerializableCourseBook(ReadOnlyCourseBook source) {
        courseInfo = new JsonAdaptedCourseInfo(source.getCourseInfo());

        modules.addAll(source.getModuleList().stream()
            .map(JsonAdaptedModule::new)
            .collect(Collectors.toList()));

        requirements.addAll(source.getRequirementList().stream()
            .map(JsonAdaptedRequirement::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this course book into the model's {@code CourseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CourseBook toModelType() throws IllegalValueException {
        CourseBook courseBook = new CourseBook();

        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (courseBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            courseBook.addModule(module);
        }

        List<Module> moduleList = courseBook.getModuleList();

        for (JsonAdaptedRequirement jsonAdaptedRequirement : requirements) {
            Requirement requirement = jsonAdaptedRequirement.toModelType(moduleList);
            if (courseBook.hasRequirement(requirement)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REQUIREMENT);
            }
            courseBook.addRequirement(requirement);
        }

        List<Requirement> requirementList = courseBook.getRequirementList();

        /**
         * If the modules and all requirements are valid (from storage), we now populate course info
         * from storage. However, to do that, we need a list of all {@code Module} in the system
         * to calculate the CAP (i.e, those modules that has grade would be added to cumulative CAP
         * of course info), the total credits fulfilled, and we also need a list of all {@code Requirement}
         * to compute the total credits required for the course.
         */
        courseBook.addCourseInfo(courseInfo.toModelType(moduleList, requirementList));

        return courseBook;
    }

}
