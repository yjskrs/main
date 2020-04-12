package igrad.storage;

//@@author waynewee

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String title;
    private final String moduleCode;
    private final String credits;
    private final String semester;
    private final String grade;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("title") String name, @JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("credits") String credits, @JsonProperty("memo") String memo,
                             @JsonProperty("semester") String semester, @JsonProperty("grade") String grade) {
        this.title = name;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.semester = semester;
        this.grade = grade;

    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        title = source.getTitle().value;
        moduleCode = source.getModuleCode().value;
        credits = source.getCredits().value;
        semester = source.getSemester().isPresent() ? source.getSemester().get().value : null;
        grade = source.getGrade().isPresent() ? source.getGrade().get().value : null;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }

        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ModuleCode.class.getSimpleName()));
        }

        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Credits.class.getSimpleName()));
        }

        if (!Credits.isValidCredits(credits)) {
            throw new IllegalValueException(Credits.MESSAGE_CONSTRAINTS);
        }

        if (semester != null && !Semester.isValidSemester(semester)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }

        if (grade != null && !Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        final Credits modelCredits = new Credits(credits);

        final Optional<Semester> modelSemester = semester != null
            ? Optional.of(new Semester(semester)) : Optional.empty();

        final Optional<Grade> modelGrade = grade != null
            ? Optional.of(new Grade(grade)) : Optional.empty();

        return new Module(modelTitle, modelModuleCode, modelCredits, modelSemester, modelGrade);
    }

}
