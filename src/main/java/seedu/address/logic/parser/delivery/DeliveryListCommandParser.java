package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.Sort;
import seedu.address.logic.commands.delivery.DeliveryListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.DeliveryStatus;


/**
 * Parses input arguments and creates a new DeliveryListCommand object.
 */
public class DeliveryListCommandParser implements Parser<DeliveryListCommand> {

    private static final Logger logger = Logger.getLogger(DeliveryListCommandParser.class.getName());

    @Override
    public DeliveryListCommand parse(String userInput) throws ParseException {

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_STATUS, PREFIX_SORT, PREFIX_CUSTOMER_ID, PREFIX_DATE);

        if (!argMultimap.isEmptyPreamble()) {
            logger.warning("DeliveryListCommandParser: preamble is not empty");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryListCommand.MESSAGE_USAGE));
        }

        return parseDeliveryListCommand(argMultimap);
    }

    private DeliveryListCommand parseDeliveryListCommand(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> sort = argMultimap.getValue(PREFIX_SORT);
        Optional<String> inputStatus = argMultimap.getValue(PREFIX_STATUS);
        Optional<String> inputCustomerId = argMultimap.getValue(PREFIX_CUSTOMER_ID);
        Optional<String> inputDate = argMultimap.getValue(PREFIX_DATE);
        Integer customerId = null;
        DeliveryStatus status = null;
        Date deliveryDate = null;

        Sort sortString = parseInputSort(sort.orElse("desc"));
        assert sortString != null : "Sort string should not be null";

        if (inputStatus.isPresent()) {
            logger.info("Input status is present with value: " + inputStatus.get());
            status = parseInputStatus(inputStatus.get());
        }

        if (inputCustomerId.isPresent()) {
            logger.info("Input customer id is present with value: " + inputCustomerId.get());
            customerId = parseInputCustomerId(inputCustomerId.get());
        }

        if (inputDate.isPresent()) {
            logger.info("Input date is present with value: " + inputDate.get());
            deliveryDate = parseInputDate(inputDate.get());
            assert deliveryDate != null : "Delivery date should not be null";
        }

        return new DeliveryListCommand(status, customerId, deliveryDate, sortString);
    }

    private Date parseInputDate(String inputDate) throws ParseException {
        boolean isToday = inputDate.equalsIgnoreCase("today");
        if (isToday) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Date.FORMAT);
            return new Date(LocalDate.now().format(formatter));
        }
        return ParserUtil.parseDate(inputDate);
    }

    private DeliveryStatus parseInputStatus(String inputStatus) throws ParseException {
        return ParserUtil.parseDeliveryStatus(inputStatus);
    }

    private Integer parseInputCustomerId(String inputCustomerId) throws ParseException {
        return ParserUtil.parseId(inputCustomerId);
    }

    private Sort parseInputSort(String inputSort) throws ParseException {
        return ParserUtil.parseSort(inputSort);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeliveryListCommandParser); // instanceof handles nulls

    }
}
