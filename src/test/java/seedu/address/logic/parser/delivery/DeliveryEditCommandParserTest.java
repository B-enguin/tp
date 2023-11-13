package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_ID_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_ID_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMAT_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JAMES_MILK;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAMES_MILK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CREATED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.delivery.DeliveryEditCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.model.customer.Name;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.testutil.DeliveryEditDescriptorBuilder;


public class DeliveryEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryEditCommand.MESSAGE_USAGE);
    private DeliveryEditCommandParser parser = new DeliveryEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", DeliveryEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_MILK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_MILK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //in valid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        //invalid customer id
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_ID_DESC, MESSAGE_INVALID_INDEX);
        //invalid delivery date
        assertParseFailure(parser, "1" + INVALID_FORMAT_DELIVERY_DATE_DESC, DeliveryDate.MESSAGE_CONSTRAINTS);

        //invalid status
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, DeliveryStatus.MESSAGE_CONSTRAINTS);

        //invalid note
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS);

        //invalid name followed by valid customer id
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + CUSTOMER_ID_DESC_MILK, Name.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_CUSTOMER_ID_DESC + INVALID_NOTE_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_JAMES_MILK + DELIVERY_DATE_DESC_MILK
                + VALID_STATUS_DESC + CUSTOMER_ID_DESC_MILK + VALID_NOTE_DESC;

        DeliveryEditDescriptor descriptor =
                new DeliveryEditDescriptorBuilder().withDeliveryName(VALID_NAME_JAMES_MILK)
                        .withDeliveryDate(VALID_DELIVERY_DATE_1).withStatus(VALID_STATUS_CREATED)
                        .withCustomerId(VALID_CUSTOMER_ID_1).withNote(VALID_NOTE).build();

        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);


        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() { //not done
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_JAMES_MILK + VALID_STATUS_DESC;

        DeliveryEditDescriptor descriptor =
                new DeliveryEditDescriptorBuilder().withDeliveryName(VALID_NAME_JAMES_MILK)
                        .withStatus(VALID_STATUS_CREATED).build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // delivery name
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_JAMES_MILK;
        DeliveryEditDescriptor descriptor =
                new DeliveryEditDescriptorBuilder().withDeliveryName(VALID_NAME_JAMES_MILK).build();
        DeliveryEditCommand expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delivery date
        userInput = targetIndex.getOneBased() + DELIVERY_DATE_DESC_MILK;
        descriptor = new DeliveryEditDescriptorBuilder().withDeliveryDate(VALID_DELIVERY_DATE_1).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + VALID_STATUS_DESC;
        descriptor = new DeliveryEditDescriptorBuilder().withStatus(VALID_STATUS_CREATED).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // customer id
        userInput = targetIndex.getOneBased() + CUSTOMER_ID_DESC_RICE;
        descriptor =
                new DeliveryEditDescriptorBuilder().withCustomerId(VALID_CUSTOMER_ID_2).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetIndex.getOneBased() + VALID_NOTE_DESC;
        descriptor = new DeliveryEditDescriptorBuilder().withNote(VALID_NOTE).build();
        expectedCommand = new DeliveryEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_MILK + INVALID_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_CUSTOMER_ID_DESC + CUSTOMER_ID_DESC_MILK;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER_ID));

        // mulltiple valid fields repeated
        userInput =
                targetIndex.getOneBased() + NAME_DESC_MILK + CUSTOMER_ID_DESC_MILK + NAME_DESC_JAMES_MILK
                        + DELIVERY_DATE_DESC_MILK + CUSTOMER_ID_DESC_RICE + DELIVERY_DATE_DESC_RICE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_CUSTOMER_ID, PREFIX_DATE));

        //multiple invalid values

        userInput = targetIndex.getOneBased() + INVALID_NOTE_DESC + INVALID_CUSTOMER_ID_DESC + INVALID_NOTE_DESC
                + INVALID_CUSTOMER_ID_DESC + INVALID_DELIVERY_NAME_DESC + INVALID_DELIVERY_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE, PREFIX_CUSTOMER_ID, PREFIX_NAME));
    }

}
