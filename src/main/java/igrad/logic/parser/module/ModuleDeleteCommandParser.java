package igrad.logic.parser.module;

import igrad.logic.commands.module.ModuleDeleteCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ModuleDeleteCommand object.
 */
public class ModuleDeleteCommandParser implements Parser<ModuleDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDeleteCommand
     * and returns a ModuleDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleDeleteCommand parse(String args) throws ParseException {
        Specifier specifier = ParserUtil.parseSpecifier(args,
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        ModuleCode moduleCode = new ModuleCode(specifier.getValue());

        return new ModuleDeleteCommand(moduleCode);
    }

}
