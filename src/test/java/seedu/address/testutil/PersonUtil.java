package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CREDITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import java.util.Set;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.module.Module;
import seedu.address.model.tags.Tags;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + module.getTitle().fullName + " ");
        sb.append(PREFIX_MODULE_CODE + module.getModuleCode().value + " ");
        sb.append(PREFIX_CREDITS + module.getCredits().value + " ");
        sb.append(PREFIX_MEMO + module.getMemo().value + " ");
        module.getTags().stream().forEach(
            s -> sb.append(PREFIX_SEMESTER + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.fullName).append(" "));
        descriptor.getModuleCode().ifPresent(phone -> sb.append(PREFIX_MODULE_CODE).append(phone.value).append(" "));
        descriptor.getCredits().ifPresent(email -> sb.append(PREFIX_CREDITS).append(email.value).append(" "));
        descriptor.getMemo().ifPresent(address -> sb.append(PREFIX_MEMO).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tags> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_SEMESTER);
            } else {
                tags.forEach(s -> sb.append(PREFIX_SEMESTER).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
