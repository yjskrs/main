package igrad.model.module;

//@@author waynewee

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's module code in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {


    public static final String MESSAGE_CONSTRAINTS = "The Module Code provided for the module is invalid!\n"
        + "Module code should contain at least two or three letters at the front and four numbers at the back, "
        + "with an optional letter at the end.\ne.g. CS2103T";

    public static final String VALIDATION_REGEX = ".{2,3}\\d{4}\\w{0,1}";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        value = moduleCode;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleCode // instanceof handles nulls
            && value.equals(((ModuleCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
