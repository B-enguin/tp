package seedu.address.logic.parser.customer;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.CustomerDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CustomerDeleteCommand object
 */
public class CustomerDeleteCommandParser implements Parser<CustomerDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomerDeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CustomerDeleteCommand(index);
        } catch (ParseException pe) {
            String commandErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CustomerDeleteCommand.MESSAGE_USAGE);
            throw new ParseException(commandErrorMessage, pe);
        }
    }

}
