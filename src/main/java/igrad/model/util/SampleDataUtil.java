package igrad.model.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import igrad.commons.core.GuiSettings;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.UserPrefs;
import igrad.model.avatar.Avatar;
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
            new Module(new Title("Software Engineering"), new ModuleCode("CS2103T"), new Credits("4"),
                new Memo("Fun module, but a lot of work"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("friends")),
            new Module(new Title("Introduction to Operating Systems"), new ModuleCode("CS2106"), new Credits("4"),
                new Memo("Learn about pipes and acheiving synchronity in shared resources"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("neighbours")),
            new Module(new Title("Linear Algebra II"), new ModuleCode("MA2101"), new Credits("4"),
                new Memo("Still linear algebra, so everything is relatively linear"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("colleagues", "friends")),
            new Module(new Title("Introduction to Database systems"), new ModuleCode("CS2102"), new Credits("4"),
                new Memo("Learn about relational calculus"), new Semester("Y2S2"),
                new Description("blah"),
                getTagSet("family")),
        };
    }

    public static Avatar getSampleAvatar() {
        return Avatar.getSampleAvatar();
    }

    public static Path getSampleCourseBookFilePath() {
        return Paths.get("data", "coursebook.json");
    }

    public static Path getSampleBackupCourseBookFilePath() {
        return Paths.get("data", "backup_coursebook.json");
    }

    public static ReadOnlyCourseBook getSampleCourseBook() {
        CourseBook sampleCourseBook = new CourseBook();
        for (Module sampleModule : getSamplePersons()) {
            sampleCourseBook.addModule(sampleModule);
        }
        return sampleCourseBook;
    }

    public static ReadOnlyCourseBook getEmptyCourseBook() {
        CourseBook emptyCourseBook = new CourseBook();
        return emptyCourseBook;
    }

    public static UserPrefs getSampleUserPrefs() {
        UserPrefs sampleUserPrefs = new UserPrefs();

        sampleUserPrefs.setGuiSettings(new GuiSettings());
        sampleUserPrefs.setCourseBookFilePath(getSampleCourseBookFilePath());
        sampleUserPrefs.setBackupCourseBookFilePath(getSampleBackupCourseBookFilePath());
        sampleUserPrefs.setAvatar(getSampleAvatar());

        return sampleUserPrefs;
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
