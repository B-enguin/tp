package seedu.address.logic.parser.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.CustomerEditCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CustomerEditCommand object
 */
public class CustomerEditCommandParser implements Parser<CustomerEditCommand> {

    private static final Logger logger = Logger.getLogger(CustomerEditCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the CustomerEditCommand
     * and returns an CustomerEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerEditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing CustomerEditCommand: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.info("Index parsed: " + index);
        } catch (ParseException pe) {
            logger.warning("Index parse failed. Invalid Command Format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CustomerEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        CustomerEditDescriptor customerEditDescriptor = createCustomerEditDescriptor(argMultimap);

        logger.info("CustomerEditDescriptor created: " + customerEditDescriptor);

        return new CustomerEditCommand(index, customerEditDescriptor);
    }

    /**
     * Creates and returns a {@code CustomerEditDescriptor} based on the given {@code ArgumentMultimap}.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerEditDescriptor createCustomerEditDescriptor(ArgumentMultimap argMultimap) throws ParseException {

        CustomerEditDescriptor customerEditDescriptor = new CustomerEditDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            customerEditDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            customerEditDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            customerEditDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            customerEditDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (!customerEditDescriptor.isAnyFieldEdited()) {
            logger.warning("No fields provided.");
            throw new ParseException(CustomerEditCommand.MESSAGE_NOT_EDITED);
        }
        return customerEditDescriptor;


    }
}
