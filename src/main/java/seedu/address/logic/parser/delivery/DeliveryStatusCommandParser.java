package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.delivery.DeliveryStatusCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliveryStatus;

/**
 * Parses input arguments and creates a new DeliveryStatusCommand object
 */
public class DeliveryStatusCommandParser implements Parser<DeliveryStatusCommand> {

    private static final Logger logger = Logger.getLogger(DeliveryStatusCommandParser.class.getName());

    private static final Pattern ARGUMENT_FORMAT = Pattern.compile(
        "^(?<id>\\d+)\\s+(?<status>\\w+)$"
    );

    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryStatusCommand
     * and returns an DeliveryStatusCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryStatusCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            logger.warning("DeliveryStatusCommand: empty arguments given");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryStatusCommand.MESSAGE_USAGE));
        }
        final Matcher matcher = ARGUMENT_FORMAT.matcher(args.trim().toUpperCase());
        if (!matcher.matches()) {
            logger.warning("DeliveryStatusCommand: arguments do not match command format");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryStatusCommand.MESSAGE_USAGE));
        }

        final String status = matcher.group("status");
        final String id = matcher.group("id");

        DeliveryStatus deliveryStatus = ParserUtil.parseDeliveryStatus(status);

        int deliveryId = ParserUtil.parseId(id);

        assert deliveryId > 0 : "Delivery ID must be an integer more than 0.";

        return new DeliveryStatusCommand(deliveryId, deliveryStatus);
    }

}
