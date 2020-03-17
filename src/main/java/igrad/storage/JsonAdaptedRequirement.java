package igrad.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.Title;

/**
 * Jackson-friendly version of {@link Requirement}.
 */
class JsonAdaptedRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Requirement's %s field is missing!";

    private final String title;
    private final String credits;
    private final List<String> moduleCodes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRequirement} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedRequirement(@JsonProperty("title") String title,
                                  @JsonProperty("credits") String credits,
                                  @JsonProperty("modules") List<String> moduleCodes) {
        this.title = title;
        this.credits = credits;
        if (moduleCodes != null) {
            this.moduleCodes.addAll(moduleCodes);
        }
    }

    /**
     * Converts a given {@code Requirement} into this class for Jackson use.
     */
    public JsonAdaptedRequirement(Requirement source) {
        title = source.getTitle().value;
        credits = source.getCredits().value;
        moduleCodes.addAll(source.getModuleList().stream()
            .map(module -> module.getModuleCode().toString())
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted requirement object into the model's {@code Requirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted requirement.
     */
    public Requirement toModelType(List<Module> moduleList) throws IllegalValueException {
        final List<Module> modelModules = new ArrayList<>();
        modelModules.addAll(moduleList.stream()
                                .filter(module -> moduleCodes.stream()
                                                      .anyMatch(code -> module.hasModuleCodeOf(new ModuleCode(code))))
                                .collect(Collectors.toList()));

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }

        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);

        final Credits modelCredits = new Credits(credits);

        return new Requirement(modelTitle, modelCredits, modelModules);
    }

}
