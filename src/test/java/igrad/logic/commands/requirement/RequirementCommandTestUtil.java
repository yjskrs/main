package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import igrad.logic.commands.CommandTestUtil;

/**
 * Utility class that stores static strings used in creating Requirement objects
 * or RequirementCommand objects.
 */
public class RequirementCommandTestUtil extends CommandTestUtil {

    // valid requirement arguments
    public static final String VALID_REQ_TITLE_CSF = "Computer Science Foundation";
    public static final String VALID_REQ_TITLE_CSBD = "Computer Science Breadth and Depth";
    public static final String VALID_REQ_TITLE_MS = "Mathematics and Sciences";
    public static final String VALID_REQ_TITLE_IP = "IT Professionalism";
    public static final String VALID_REQ_TITLE_UE = "Unrestricted Electives";
    public static final String VALID_REQ_TITLE_GE = "General Electives";
    public static final String VALID_REQ_CODE_CSF = "CSF0";
    public static final String VALID_REQ_CODE_CSBD = "CSBD0";
    public static final String VALID_REQ_CODE_MS = "MS0";
    public static final String VALID_REQ_CODE_IP = "IP0";
    public static final String VALID_REQ_CODE_UE = "UE0";
    public static final String VALID_REQ_CODE_GE = "GE0";
    public static final String VALID_REQ_CREDITS_CSF = "32";
    public static final String VALID_REQ_CREDITS_CSBD = "48";
    public static final String VALID_REQ_CREDITS_MS = "16";
    public static final String VALID_REQ_CREDITS_IP = "12";
    public static final String VALID_REQ_CREDITS_UE = "32";
    public static final String VALID_REQ_CREDITS_GE = "20";

    // invalid requirement arguments
    public static final String INVALID_REQ_CODE_DECIMAL = "RE1.0";
    public static final String INVALID_REQ_CODE_SYMBOL = "RE<";

    public static final String INVALID_REQ_CREDITS_ALPHABET = "a";
    public static final String INVALID_REQ_CREDITS_DECIMAL = "40.0";
    public static final String INVALID_REQ_CREDITS_SYMBOL = "&";

    // requirement title descriptor for command entered
    public static final String REQ_TITLE_DESC_CSF = " " + PREFIX_TITLE + VALID_REQ_TITLE_CSF;
    public static final String REQ_TITLE_DESC_CSBD = " " + PREFIX_TITLE + VALID_REQ_TITLE_CSBD;
    public static final String REQ_TITLE_DESC_MS = " " + PREFIX_TITLE + VALID_REQ_TITLE_MS;
    public static final String REQ_TITLE_DESC_IP = " " + PREFIX_TITLE + VALID_REQ_TITLE_IP;
    public static final String REQ_TITLE_DESC_UE = " " + PREFIX_TITLE + VALID_REQ_TITLE_UE;
    public static final String REQ_TITLE_DESC_GE = " " + PREFIX_TITLE + VALID_REQ_TITLE_GE;

    public static final String REQ_CREDITS_DESC_CSF = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_CSF;
    public static final String REQ_CREDITS_DESC_CSBD = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_CSBD;
    public static final String REQ_CREDITS_DESC_MS = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_MS;
    public static final String REQ_CREDITS_DESC_IP = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_IP;
    public static final String REQ_CREDITS_DESC_UE = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_UE;
    public static final String REQ_CREDITS_DESC_GE = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_GE;
}
