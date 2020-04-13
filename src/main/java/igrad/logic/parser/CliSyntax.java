package igrad.logic.parser;

//@@author teriaiw

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("n/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_CREDITS = new Prefix("u/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("s/");
    public static final Prefix PREFIX_CAP = new Prefix("c/");

    public static final Flag FLAG_AUTO = new Flag("-a");
    public static final Flag FLAG_OPERATOR_OR = new Flag("-o");
}
