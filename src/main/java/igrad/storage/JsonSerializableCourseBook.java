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

/**
 * An Immutable CourseBook that is serializable to JSON format.
 */
@JsonRootName(value = "coursebook")
class JsonSerializableCourseBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate module(s).";

    private final List<JsonAdaptedModule> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCourseBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCourseBook(@JsonProperty("persons") List<JsonAdaptedModule> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyCourseBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCourseBook}.
     */
    public JsonSerializableCourseBook(ReadOnlyCourseBook source) {
        persons.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this course book into the model's {@code CourseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CourseBook toModelType() throws IllegalValueException {
        CourseBook courseBook = new CourseBook();
        for (JsonAdaptedModule jsonAdaptedModule : persons) {
            Module module = jsonAdaptedModule.toModelType();
            if (courseBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            courseBook.addModule(module);
        }
        return courseBook;
    }

}
