package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.delivery.DeliveryViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryViewCommand object
 */
public class DeliveryViewCommandParser implements Parser<DeliveryViewCommand> {
    private static final Logger logger = Logger.getLogger(DeliveryViewCommandParser.class.getName());
    private static final Pattern ARGUMENT_FORMAT = Pattern.compile(
        "^(?<id>\\d+)$"
    );

    @Override
    public DeliveryViewCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            logger.warning("DeliveryViewCommandParser: args is empty");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryViewCommand.MESSAGE_USAGE));
        }

        final Matcher matcher = ARGUMENT_FORMAT.matcher(args.trim().toUpperCase());
        if (!matcher.matches()) {
            logger.warning("DeliveryViewCommandParser: args does not match regex");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryViewCommand.MESSAGE_USAGE));
        }

        final String id = matcher.group("id");
        logger.info("DeliveryViewCommandParser: id " + id);
        int deliveryId = ParserUtil.parseId(id);

        return new DeliveryViewCommand(deliveryId);
    }
}
