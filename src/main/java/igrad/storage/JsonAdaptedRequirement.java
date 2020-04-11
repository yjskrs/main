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
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * Jackson-friendly version of {@link Requirement}.
 */
class JsonAdaptedRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Requirement's %s field is missing!";

    private final String requirementCode;
    private final String title;
    private final String credits;
    private final List<String> moduleCodes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRequirement} with the given requirement details.
     */
    @JsonCreator
    public JsonAdaptedRequirement(@JsonProperty("requirementCode") String requirementCode,
                                  @JsonProperty("title") String title,
                                  @JsonProperty("credits") String credits,
                                  @JsonProperty("modules") List<String> moduleCodes) {

        this.requirementCode = requirementCode;
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
        requirementCode = source.getRequirementCode().toString();
        title = source.getTitle().toString();
        credits = String.valueOf(source.getCreditsRequired());
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
        // check for valid requirement code
        if (requirementCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RequirementCode.class.getSimpleName()));
        }
        if (!RequirementCode.isValidRequirementCode(requirementCode)) {
            throw new IllegalValueException(RequirementCode.MESSAGE_CONSTRAINTS);
        }

        // check for valid title
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        // check for valid credits
        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Credits.class.getSimpleName()));
        }
        if (!Credits.isValidCredits(credits)) {
            throw new IllegalValueException(Credits.MESSAGE_CONSTRAINTS);
        }

        final RequirementCode modelRequirementCode = new RequirementCode(requirementCode);
        final Title modelName = new Title(title);
        final Credits modelCredits = new Credits(credits);
        final List<Module> modelModules = new ArrayList<>();

        modelModules.addAll(moduleList.stream() // for all modules
            .filter(module -> moduleCodes.stream() // find module existing in moduleCodes
                .anyMatch(moduleCode -> module.hasModuleCodeOf(new ModuleCode(moduleCode))))
            .collect(Collectors.toList()));

        return new Requirement(modelRequirementCode, modelName, modelCredits, modelModules);
    }

}
