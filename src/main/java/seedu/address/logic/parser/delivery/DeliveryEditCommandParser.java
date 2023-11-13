package seedu.address.logic.parser.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delivery.DeliveryEditCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliveryEditCommand object
 */
public class DeliveryEditCommandParser implements Parser<DeliveryEditCommand> {

    private static final Logger logger = Logger.getLogger(DeliveryEditCommandParser.class.getName());
    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryEditCommand
     * and returns an DeliveryEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryEditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing DeliveryEditCommand: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CUSTOMER_ID,
                        PREFIX_DATE, PREFIX_STATUS, PREFIX_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.info("Index parsed: " + index);
        } catch (ParseException pe) {
            logger.warning("Index parse failed. Invalid Command Format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeliveryEditCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CUSTOMER_ID,
                PREFIX_DATE, PREFIX_STATUS, PREFIX_NOTE);

        DeliveryEditDescriptor deliveryEditDescriptor = createDeliveryEditDescriptor(argMultimap);

        logger.info("DeliveryEditDescriptor created: " + deliveryEditDescriptor);
        return new DeliveryEditCommand(index, deliveryEditDescriptor);
    }

    /**
     * Creates and returns a {@code DeliveryEditDescriptor} based on the given {@code ArgumentMultimap}.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryEditDescriptor createDeliveryEditDescriptor(ArgumentMultimap argMultimap) throws ParseException {

        DeliveryEditDescriptor deliveryEditDescriptor = new DeliveryEditDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            deliveryEditDescriptor.setDeliveryName(ParserUtil
                    .parseDeliveryName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CUSTOMER_ID).isPresent()) {
            deliveryEditDescriptor.setCustomerId(ParserUtil.parseId(argMultimap.getValue(PREFIX_CUSTOMER_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            deliveryEditDescriptor.setDeliveryDate(ParserUtil
                    .parseDeliveryDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            deliveryEditDescriptor.setStatus(ParserUtil.parseDeliveryStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            deliveryEditDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (!deliveryEditDescriptor.isAnyFieldEdited()) {
            logger.warning("No fields provided.");
            throw new ParseException(DeliveryEditCommand.MESSAGE_NOT_EDITED);
        }
        return deliveryEditDescriptor;
    }
}
