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
            new Module(new Title("Alex Yeoh"), new ModuleCode("CS2103T"), new Credits("alexyeoh@example.com"),
                new Memo("Blk 30 Geylang Street 29, #06-40"), new Semester( "Y2S2" ),
                getTagSet("friends")),
            new Module(new Title("Bernice Yu"), new ModuleCode("CS2103T"), new Credits("berniceyu@example.com"),
                new Memo("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Semester( "Y2S2" ),
                getTagSet("colleagues", "friends")),
            new Module(new Title("Charlotte Oliveiro"), new ModuleCode("CS2103T"), new Credits("charlotte@example.com"),
                new Memo("Blk 11 Ang Mo Kio Street 74, #11-04"), new Semester( "Y2S2" ),
                getTagSet("neighbours")),
            new Module(new Title("David Li"), new ModuleCode("CS2103T"), new Credits("lidavid@example.com"),
                new Memo("Blk 436 Serangoon Gardens Street 26, #16-43"), new Semester( "Y2S2" ),
                getTagSet("family")),
            new Module(new Title("Irfan Ibrahim"), new ModuleCode("CS2103T"), new Credits("irfan@example.com"),
                new Memo("Blk 47 Tampines Street 20, #17-35"), new Semester( "Y2S2" ),
                getTagSet("classmates")),
            new Module(new Title("Roy Balakrishnan"), new ModuleCode("CS2103T"), new Credits("royb@example.com"),
                new Memo("Blk 45 Aljunied Street 85, #11-31"), new Semester( "Y2S2" ),
                getTagSet("colleagues"))
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
