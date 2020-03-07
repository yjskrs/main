package igrad.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.module.Email;
import igrad.model.module.Module;
import igrad.model.module.Name;
import igrad.model.module.Phone;
import igrad.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CourseBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSamplePersons() {
        return new Module[] {
            new Module(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Module(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Module(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Module(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Module(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Module(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
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
