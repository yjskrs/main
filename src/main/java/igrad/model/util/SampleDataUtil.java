package igrad.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CourseBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSamplePersons() {
        return new Module[] {
            new Module(new Title("Alex Yeoh"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 30 Geylang Street 29, #06-40"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("friends")),
            new Module(new Title("Bernice Yu"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("colleagues", "friends")),
            new Module(new Title("Charlotte Oliveiro"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 11 Ang Mo Kio Street 74, #11-04"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("neighbours")),
            new Module(new Title("David Li"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 436 Serangoon Gardens Street 26, #16-43"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("family")),
            new Module(new Title("Irfan Ibrahim"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 47 Tampines Street 20, #17-35"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("classmates")),
            new Module(new Title("Roy Balakrishnan"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Blk 45 Aljunied Street 85, #11-31"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyCourseBook getSampleCourseBook() {
        CourseBook sampleCourseBook = new CourseBook();
        for (Module sampleModule : getSamplePersons()) {
            sampleCourseBook.addModule(sampleModule);
        }
        return sampleCourseBook;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
