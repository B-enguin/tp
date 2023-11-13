package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delivery.DeliveryDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryDeleteCommand object
 */
public class DeliveryDeleteCommandParser implements Parser<DeliveryDeleteCommand> {

    private static final Logger logger = Logger.getLogger(DeliveryDeleteCommandParser.class.getName());
    /**
     * Parses the given {@code String} of arguments in the context of the CustomerDeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing DeliveryDeleteCommand: " + args);
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.info("Index parsed: " + index);
            return new DeliveryDeleteCommand(index);
        } catch (ParseException pe) {
            logger.warning("Index parse failed. Invalid Command Format.");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
