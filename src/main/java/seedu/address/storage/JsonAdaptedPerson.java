package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Description;
import seedu.address.model.module.Memo;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Title;
import seedu.address.model.tags.Tags;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String moduleCode;
    private final String credits;
    private final String semester;
    private final String memo;
    private final String description;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("credits") String credits,
                             @JsonProperty("memo") String memo,
                             @JsonProperty("description") String description,
                             @JsonProperty("semester") String semester,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.memo = memo;
        this.description = description;
        this.semester = semester;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Module source) {
        name = source.getTitle().fullName;
        moduleCode = source.getModuleCode().value;
        credits = source.getCredits().value;
        memo = source.getMemo() != null ? source.getMemo().value : null;
        description = source.getDescription() != null ? source.getDescription().value : null;
        semester = source.getSemester() != null ? source.getSemester().value : null;
        tagged.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tags> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidName(name)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(name);

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
        final Credits modelCredits = new Credits(credits);

        final Semester modelSemester = new Semester(semester);
        final Memo modelMemo = new Memo(memo);
        final Description modelDescription = new Description(description);

        final Set<Tags> modelTags = new HashSet<>(personTags);
        return new Module(
            modelTitle,
            modelModuleCode,
            modelCredits,
            modelMemo,
            modelDescription,
            modelSemester,
            modelTags
        );
    }

}
