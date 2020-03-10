package igrad.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.module.Credits;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Title;
import igrad.model.module.Semester;
import igrad.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String title;
    private final String moduleCode;
    private final String credits;
    private final String memo;
    private final String semester;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("title") String name, @JsonProperty("moduleCode") String moduleCode,
            @JsonProperty("credits") String credits, @JsonProperty("memo") String memo, @JsonProperty("semester") String semester,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = name;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.memo = memo;
        this.semester = semester;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        title = source.getTitle().value;
        moduleCode = source.getModuleCode().value;
        credits = source.getCredits().value;
        memo = source.getMemo().value;
        semester = source.getSemester().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tag> moduleTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            moduleTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }

        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException( Title.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }

        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException( ModuleCode.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Credits.class.getSimpleName()));
        }

        if (!Credits.isValidCredits(credits)) {
            throw new IllegalValueException( Credits.MESSAGE_CONSTRAINTS);
        }

        final Credits modelCredits = new Credits(credits);

        if (memo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Memo.class.getSimpleName()));
        }

        final Memo modelMemo = new Memo(memo);

        if (semester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Memo.class.getSimpleName()));
        }

        if (!Semester.isValidSemester(credits)) {
            throw new IllegalValueException( Credits.MESSAGE_CONSTRAINTS);
        }

        final Semester modelSemester = new Semester(semester);

        final Set<Tag> modelTags = new HashSet<>(moduleTags);

        return new Module( modelTitle, modelModuleCode, modelCredits, modelMemo, modelSemester, modelTags );
    }

}
