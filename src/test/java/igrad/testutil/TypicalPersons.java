package igrad.testutil;

import static igrad.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static igrad.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static igrad.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static igrad.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static igrad.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static igrad.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static igrad.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static igrad.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.CourseBook;
import igrad.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Module ALICE = new ModuleBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Module BENSON = new ModuleBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Module CARL = new ModuleBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Module DANIEL = new ModuleBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withTags("friends").build();
    public static final Module ELLE = new ModuleBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").build();
    public static final Module FIONA = new ModuleBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").build();
    public static final Module GEORGE = new ModuleBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Module HOON = new ModuleBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").build();
    public static final Module IDA = new ModuleBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Module's details found in {@code CommandTestUtil}
    public static final Module AMY = new ModuleBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Module BOB = new ModuleBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code CourseBook} with all the typical persons.
     */
    public static CourseBook getTypicalCourseBook() {
        CourseBook ab = new CourseBook();
        for (Module module : getTypicalPersons()) {
            ab.addModule(module);
        }
        return ab;
    }

    public static List<Module> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
