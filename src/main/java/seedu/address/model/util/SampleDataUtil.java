package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.tags.Tags;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSamplePersons() {
        return new Module[] {
            new Module(new Title("Software Engineering"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Good Module, Gooder Teachers."), new Description("This module introduces the necessary conceptual and analytical tools for systematic and rigorous development of software systems. It covers four main areas of software development, namely object-oriented system analysis, object-oriented system modelling and design, implementation, and testing, with emphasis on system modelling and design and implementation of software modules that work cooperatively to fulfill the requirements of the system. Tools and techniques for software development, such as Unified Modelling Language (UML), program specification, and testing methods, will be taught. Major software engineering issues such as modularisation criteria, program correctness, and software quality will also be covered."), new Semester( "Y2S2" ),
                getTagSet("coding", "engineering", "fun")),
            new Module(new Title("Effective Communication for Computing Professionals"), new ModuleCode("CS2101"), new Credits("4"),
                new Memo("Learnt how to present!"), new Description("This module aims to equip students with the skills needed to communicate technical information to technical and nontechnical audiences, and to create comprehensible software documentation. A student-centric approach will be adopted to encourage independent and collaborative learning while engaging students in team-based projects. Students will learn interpersonal and intercultural communication skills as well as hone their oral and written communication skills. Assessment modes include a variety of oral and written communication tasks such as reports, software guides, oral presentations, software demonstrations and project blogs."), new Semester( "Y2S2" ),
                getTagSet("colleagues", "friends"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Module sampleModule : getSamplePersons()) {
            sampleAb.addPerson( sampleModule );
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tags> getTagSet( String... strings) {
        return Arrays.stream(strings)
                .map( Tags::new)
                .collect(Collectors.toSet());
    }

}
