package seedu.address.logic.parser.customer;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.customer.CustomerViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryViewCommand object
 */
public class CustomerViewCommandParser implements Parser<CustomerViewCommand> {

    private static final Logger logger = Logger.getLogger(CustomerViewCommandParser.class.getName());

    private static final Pattern ARGUMENT_FORMAT = Pattern.compile(
        "^(?<id>\\d+)$"
    );

    /**
     * Parses the given {@code String} of arguments in the context of the CustomerViewCommand
     * and returns an CustomerViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CustomerViewCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            logger.warning("CustomerViewCommand: empty arguments given");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CustomerViewCommand.MESSAGE_USAGE));
        }

        final Matcher matcher = ARGUMENT_FORMAT.matcher(args.trim().toUpperCase());
        if (!matcher.matches()) {
            logger.warning("CustomerViewCommand: invalid id given");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CustomerViewCommand.MESSAGE_USAGE));
        }

        final String id = matcher.group("id");

        int customerId = ParserUtil.parseId(id);

        assert customerId > 0 : "Customer ID must be an integer more than 0.";

        return new CustomerViewCommand(customerId);
    }

}
