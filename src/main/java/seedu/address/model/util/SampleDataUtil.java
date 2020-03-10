package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Description;
import seedu.address.model.module.Memo;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Title;
import seedu.address.model.tags.Tags;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSamplePersons() {
        return new Module[] {
            new Module(
                new Title("Software Engineering"),
                new ModuleCode("CS2103T"),
                new Credits("4"),
                new Memo("Good Module, Gooder Teachers."),
                new Description("This module introduces the necessary conceptual and "
                    + "analytical tools for systematic and rigorous development "
                    + "of software systems."),
                new Semester("Y2S2"),
                getTagSet("coding", "engineering", "fun")
            ),
            new Module(
                new Title("Effective Communication for Computing Professionals"),
                new ModuleCode("CS2101"),
                new Credits("4"),
                new Memo("Learnt how to present!"),
                new Description("This module aims to equip students with the "
                    + "skills needed to communicate technical information to technical"
                    + " and nontechnical audiences, and to create comprehensible "
                    + "software documentation."), new Semester("Y2S2"),
                getTagSet("colleagues", "friends"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Module sampleModule : getSamplePersons()) {
            sampleAb.addPerson(sampleModule);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tags> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tags::new)
            .collect(Collectors.toSet());
    }

}
