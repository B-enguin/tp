package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.logic.commands.delivery.DeliveryAddCommand.DeliveryAddDescriptor;
import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.UserUpdateCommand.UserUpdateDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.testutil.CustomerEditDescriptorBuilder;
import seedu.address.testutil.DeliveryAddDescriptorBuilder;
import seedu.address.testutil.DeliveryEditDescriptorBuilder;
import seedu.address.testutil.UpdateUserDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Delivery
    public static final String VALID_VIEW_DELIVERY_ID = "1";

    public static final int VALID_DELIVERY_ID = 1;
    public static final String INVALID_VIEW_DELIVERY_ID = "11";
    public static final String VALID_NAME_GABRIELS_MILK = "Gabriel Milk";
    public static final String VALID_NAME_JAMES_MILK = "Jame Milk";

    public static final String VALID_NAME_CHIPS = "Chips";
    public static final String INVALID_NOTE = "";
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE + INVALID_NOTE;
    public static final String VALID_NOTE = "VALID NOTE";
    public static final String VALID_NOTE_DESC = " " + PREFIX_NOTE + VALID_NOTE;
    public static final String INVALID_STATUS = "INVALID";
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + INVALID_STATUS;
    public static final DeliveryStatus VALID_STATUS_CREATED = DeliveryStatus.CREATED;
    public static final DeliveryStatus VALID_STATUS_SHIPPED = DeliveryStatus.SHIPPED;
    public static final DeliveryStatus VALID_STATUS_COMPLETED = DeliveryStatus.COMPLETED;
    public static final DeliveryStatus VALID_STATUS_CANCELLED = DeliveryStatus.CANCELLED;
    public static final String VALID_STATUS_CREATED_VIEW = "CREATED";
    public static final String VALID_STATUS_SHIPPED_VIEW = "SHIPPED";
    public static final String VALID_STATUS_COMPLETED_VIEW = "COMPLETED";
    public static final String VALID_STATUS_CANCELLED_VIEW = "CANCELLED";
    public static final String VALID_STATUS_DESC = " " + PREFIX_STATUS + VALID_STATUS_CREATED;
    public static final String INVALID_ID_NEGATIVE = "-1";
    public static final String INVALID_ID_NAN = "NaN";

    public static final String VALID_DELIVERY_DATE_1 = "2025-12-12";

    public static final String VALID_DELIVERY_DATE_2 = "2025-11-11";
    public static final String VALID_DELIVERY_DATE_3 = "2023-12-12";
    public static final String INVALID_DELIVERY_DATE = "2022-01-01";
    public static final String INVALID_FORMAT_DELIVERY_DATE = "20-01-01";

    public static final String INVALID_FORMAT_DELIVERY_DATE_DESC = " " + PREFIX_DATE + INVALID_FORMAT_DELIVERY_DATE;


    public static final String INVALID_DELIVERY_NAME = "Gabriel&";
    public static final String INVALID_DELIVERY_DATE_DESC = " " + PREFIX_DATE + INVALID_DELIVERY_DATE;

    public static final String NAME_DESC_MILK = " " + PREFIX_NAME + " " + VALID_NAME_GABRIELS_MILK;

    public static final String NAME_DESC_JAMES_MILK = " " + PREFIX_NAME + " " + VALID_NAME_JAMES_MILK;

    public static final String INVALID_DELIVERY_NAME_DESC = " " + PREFIX_NAME + INVALID_DELIVERY_NAME;


    public static final String DELIVERY_DATE_DESC_MILK = " " + PREFIX_DATE + VALID_DELIVERY_DATE_1;

    public static final String DELIVERY_DATE_DESC_RICE = " " + PREFIX_DATE + VALID_DELIVERY_DATE_2;

    // Customer
    public static final int VALID_CUSTOMER_ID_1 = 1;
    public static final int VALID_CUSTOMER_ID_2 = 2;
    public static final String VALID_VIEW_CUSTOMER_ID_1 = "1";
    public static final String VALID_VIEW_CUSTOMER_ID_2 = "2";
    public static final String INVALID_CUSTOMER_ID = "a";
    public static final int TOO_LARGE_CUSTOMER_ID = 999;

    public static final String INVALID_CUSTOMER_ID_DESC = " " + PREFIX_CUSTOMER_ID + INVALID_CUSTOMER_ID;

    public static final String TOO_LARGE_CUSTOMER_ID_DESC = " " + PREFIX_CUSTOMER_ID + TOO_LARGE_CUSTOMER_ID;
    public static final String CUSTOMER_ID_DESC_MILK = " " + PREFIX_CUSTOMER_ID + VALID_VIEW_CUSTOMER_ID_1;
    public static final String CUSTOMER_ID_DESC_RICE = " " + PREFIX_CUSTOMER_ID + VALID_VIEW_CUSTOMER_ID_2;
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_USERNAME_AARON = "aAron1sMe";
    public static final String VALID_USERNAME_FOODBEAR = "FoodBear";
    // password string is qwerty1234
    public static final String VALID_PASSWORD_AARON = "qwerty1234";
    public static final String VALID_HASHED_PASSWORD_AARON =
            "17F80754644D33AC685B0842A402229ADBB43FC9312F7BDF36BA24237A1F1FFB";
    // password string is BEARYf00d
    public static final String VALID_PASSWORD_FOODBEAR = "BEARYf00d";
    public static final String VALID_HASHED_PASSWORD_FOODBEAR =
            "26CC1E3D7988F3AE6FB06859123B5046A5A84D47611092037E059F66E1C0E461";
    public static final String VALID_SECRET_QUESTION_AARON = "What is my name?";
    public static final String VALID_SECRET_QUESTION_FOODBEAR = "What is my favourite food?";
    public static final String VALID_ANSWER_AARON = "Aaron";
    public static final String VALID_ANSWER_FOODBEAR = "Food";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String USERNAME_DESC_AARON = " " + PREFIX_USER + VALID_USERNAME_AARON;
    public static final String USERNAME_DESC_FOODBEAR = " " + PREFIX_USER + VALID_USERNAME_FOODBEAR;
    public static final String PASSWORD_DESC_AARON = " " + PREFIX_PASSWORD + VALID_PASSWORD_AARON;
    public static final String PASSWORD_CONFIRM_DESC_AARON = " " + PREFIX_PASSWORD_CONFIRM + VALID_PASSWORD_AARON;
    public static final String PASSWORD_DESC_FOODBEAR = " " + PREFIX_PASSWORD + VALID_PASSWORD_FOODBEAR;
    public static final String PASSWORD_CONFIRM_DESC_FOODBEAR = " " + PREFIX_PASSWORD_CONFIRM + VALID_PASSWORD_FOODBEAR;
    public static final String SECRET_QUESTION_DESC_AARON = " " + PREFIX_SECRET_QUESTION + "What is my name?";
    public static final String SECRET_QUESTION_DESC_FOODBEAR = " "
            + PREFIX_SECRET_QUESTION + "What is my favourite food?";
    public static final String ANSWER_DESC_AARON = " " + PREFIX_ANSWER + "Aaron";
    public static final String ANSWER_DESC_FOODBEAR = " " + PREFIX_ANSWER + "Food";
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_PHONE_DESC_2 = " " + PREFIX_PHONE + "1234567"; // less than 8 digits
    public static final String INVALID_PHONE_DESC_3 = " " + PREFIX_PHONE + "123456789"; // more than 8 digits
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USER
            + "thisIs^myName"; // '^' not allowed in username
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "qwerty"; // lesser than 8 characters
    public static final String INVALID_PASSWORD_CONFIRM_DESC = " " + PREFIX_PASSWORD_CONFIRM + "qwerty";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String INVALID_DELIVERY_LIST_ALL = " " + "sadasdasdasdasdasdas";
    public static final String VALID_DELIVERY_LIST_CUSTOMER_ID = " " + PREFIX_CUSTOMER_ID + "1";
    public static final String INVALID_DELIVERY_LIST_CUSTOMER_ID = " " + PREFIX_CUSTOMER_ID + "x";
    public static final String VALID_DELIVERY_LIST_DELIVERY_DATE = " " + PREFIX_DATE + "2023-12-12";
    public static final String VALID_DELIVERY_LIST_DELIVERY_DATE_TODAY = " " + PREFIX_DATE + "today";
    public static final String INVALID_DELIVERY_LIST_DELIVERY_DATE_TODAY = " " + PREFIX_DATE + "tomorrow";
    public static final String INVALID_DELIVERY_LIST_DELIVERY_DATE = " " + PREFIX_DATE + "13-13-2023";
    public static final String INVALID_DELIVERY_LIST_DELIVERY_DATE_MONTH = " " + PREFIX_DATE + "2023-13-12";
    public static final String VALID_DELIVERY_LIST_CREATED = " " + PREFIX_STATUS + "created";
    public static final String VALID_DELIVERY_LIST_SHIPPED = " " + PREFIX_STATUS + "SHIPPED";
    public static final String VALID_DELIVERY_LIST_COMPLETED = " " + PREFIX_STATUS + "completed";
    public static final String VALID_DELIVERY_LIST_CANCELLED = " " + PREFIX_STATUS + "cancelled";

    public static final String INVALID_DELIVERY_LIST = " " + PREFIX_STATUS + "invalid";
    public static final String VALID_DELIVERY_LIST_SORT_ASC = " " + PREFIX_SORT + "asc";
    public static final String VALID_DELIVERY_LIST_SORT_DESC = " " + PREFIX_SORT + "desc";
    public static final String INVALID_DELIVERY_LIST_SORT = " " + PREFIX_SORT + "invalid";

    public static final CustomerEditDescriptor DESC_AMY;
    public static final CustomerEditDescriptor DESC_BOB;
    public static final DeliveryAddDescriptor DESC_MILK;
    public static final DeliveryAddDescriptor DESC_RICE;

    public static final DeliveryEditDescriptor DESC_EDIT_MILK;
    public static final DeliveryEditDescriptor DESC_EDIT_CHIPS;

    public static final UserUpdateDescriptor DESC_AARON;
    public static final UserUpdateDescriptor DESC_FOODBEAR;


    static {
        DESC_AMY = new CustomerEditDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();
        DESC_BOB = new CustomerEditDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        DESC_MILK = new DeliveryAddDescriptorBuilder().withCustomerId(VALID_CUSTOMER_ID_1)
                .withDeliveryDate(VALID_DELIVERY_DATE_1).withDeliveryName(VALID_NAME_GABRIELS_MILK).build();

        DESC_RICE = new DeliveryAddDescriptorBuilder().withCustomerId(VALID_CUSTOMER_ID_2)
                .withDeliveryDate(VALID_DELIVERY_DATE_2).withDeliveryName(VALID_NAME_JAMES_MILK).build();

        DESC_EDIT_MILK = new DeliveryEditDescriptorBuilder().withDeliveryDate(VALID_DELIVERY_DATE_3)
                .withDeliveryName(VALID_NAME_JAMES_MILK).withStatus(VALID_STATUS_SHIPPED)
                .withCustomerId(VALID_CUSTOMER_ID_2).withNote(VALID_NOTE).build();

        DESC_EDIT_CHIPS = new DeliveryEditDescriptorBuilder().withDeliveryDate(VALID_DELIVERY_DATE_1)
                .withDeliveryName(VALID_NAME_CHIPS).withStatus(VALID_STATUS_SHIPPED)
                .withCustomerId(VALID_CUSTOMER_ID_1).withNote(VALID_NOTE).build();

        DESC_AARON = new UpdateUserDescriptorBuilder().withUsername(VALID_USERNAME_AARON)
                .withPassword(VALID_PASSWORD_AARON).withSecretQuestion(VALID_SECRET_QUESTION_AARON)
                .withAnswer(VALID_ANSWER_AARON).build();

        DESC_FOODBEAR = new UpdateUserDescriptorBuilder().withUsername(VALID_USERNAME_FOODBEAR)
                .withPassword(VALID_PASSWORD_FOODBEAR).withSecretQuestion(VALID_SECRET_QUESTION_FOODBEAR)
                .withAnswer(VALID_ANSWER_FOODBEAR).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and {@code refreshListCommand}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, boolean refreshListCommand) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, refreshListCommand);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandListSuccess(Command command, Model actualModel, String expectedMessage,
                                                Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Customer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCustomerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCustomerList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCustomerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Customer customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the delivery at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDeliveryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeliveryList().size());

        Delivery delivery = model.getFilteredDeliveryList().get(targetIndex.getZeroBased());
        final String[] splitName = delivery.getName().toString().split("\\s+");
        model.updateFilteredDeliveryList(new DeliveryNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDeliveryList().size());
    }

}
