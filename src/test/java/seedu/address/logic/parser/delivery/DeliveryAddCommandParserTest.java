package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_ID_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMAT_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABRIELS_MILK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.delivery.DeliveryAddCommand;
import seedu.address.logic.commands.delivery.DeliveryAddCommand.DeliveryAddDescriptor;
import seedu.address.logic.parser.DeliveryAddCommandParser;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
public class DeliveryAddCommandParserTest {
    private DeliveryAddCommandParser parser = new DeliveryAddCommandParser();


    @Test
    public void parse_allFieldsPresent_success() {
        DeliveryAddDescriptor expectedDescriptor = new DeliveryAddDescriptor(DESC_MILK);

        assertParseSuccess(parser, VALID_NAME_GABRIELS_MILK + CUSTOMER_ID_DESC_MILK
                + DELIVERY_DATE_DESC_MILK, new DeliveryAddCommand(expectedDescriptor));
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE);
        // missing customer prefix
        assertParseFailure(parser, NAME_DESC_MILK + VALID_CUSTOMER_ID_1
                + DELIVERY_DATE_DESC_MILK, expectedMessage);

        // missing expected delivery date prefix
        assertParseFailure(parser, NAME_DESC_MILK + CUSTOMER_ID_DESC_MILK
                + VALID_DELIVERY_DATE_1, expectedMessage);
    }

    @Test
    public void parse_missingPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, CUSTOMER_ID_DESC_MILK
                + DELIVERY_DATE_DESC_MILK, expectedMessage);

    }

    @Test
    public void parse_duplicatePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, CUSTOMER_ID_DESC_MILK + CUSTOMER_ID_DESC_MILK
                + DELIVERY_DATE_DESC_MILK, expectedMessage);
    }

    @Test
    public void parse_noArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryAddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // delivery add <name> --customer <customer id> --date <expected delivery date>

        // invalid name
        assertParseFailure(parser, INVALID_DELIVERY_NAME + CUSTOMER_ID_DESC_MILK
                + DELIVERY_DATE_DESC_MILK, DeliveryName.MESSAGE_CONSTRAINTS);

        // invalid format expected delivery date
        assertParseFailure(parser, VALID_NAME_GABRIELS_MILK + CUSTOMER_ID_DESC_MILK
                + INVALID_FORMAT_DELIVERY_DATE_DESC, DeliveryDate.MESSAGE_CONSTRAINTS);

        // invalid customer id
        assertParseFailure(parser, VALID_NAME_GABRIELS_MILK + INVALID_CUSTOMER_ID_DESC
               + DELIVERY_DATE_DESC_MILK, MESSAGE_INVALID_INDEX);

    }
}
