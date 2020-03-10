package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Test;

public class CreditsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Credits(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Credits(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Credits.isValidCredits(null));

        // blank email
        assertFalse(Credits.isValidCredits("")); // empty string
        assertFalse(Credits.isValidCredits(" ")); // spaces only

        // missing parts
        assertFalse(Credits.isValidCredits("@example.com")); // missing local part
        assertFalse(Credits.isValidCredits("peterjackexample.com")); // missing '@' symbol
        assertFalse(Credits.isValidCredits("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Credits.isValidCredits("peterjack@-")); // invalid domain name
        assertFalse(Credits.isValidCredits("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Credits.isValidCredits("peter jack@example.com")); // spaces in local part
        assertFalse(Credits.isValidCredits("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Credits.isValidCredits(" peterjack@example.com")); // leading space
        assertFalse(Credits.isValidCredits("peterjack@example.com ")); // trailing space
        assertFalse(Credits.isValidCredits("peterjack@@example.com")); // double '@' symbol
        assertFalse(Credits.isValidCredits("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Credits.isValidCredits("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Credits.isValidCredits("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Credits.isValidCredits("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Credits.isValidCredits("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Credits.isValidCredits("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Credits.isValidCredits("PeterJack_1190@example.com"));
        assertTrue(Credits.isValidCredits("a@bc")); // minimal
        assertTrue(Credits.isValidCredits("test@localhost")); // alphabets only
        assertTrue(Credits.isValidCredits("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Credits.isValidCredits("123@145")); // numeric local part and domain name
        assertTrue(Credits.isValidCredits("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Credits.isValidCredits("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Credits.isValidCredits("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
