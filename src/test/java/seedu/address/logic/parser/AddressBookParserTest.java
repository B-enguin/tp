package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VIEW_CUSTOMER_ID_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.customer.CustomerAddCommand;
import seedu.address.logic.commands.customer.CustomerDeleteCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.logic.commands.customer.CustomerFindCommand;
import seedu.address.logic.commands.customer.CustomerListCommand;
import seedu.address.logic.commands.customer.CustomerViewCommand;
import seedu.address.logic.commands.delivery.DeliveryAddCommand;
import seedu.address.logic.commands.delivery.DeliveryAddCommand.DeliveryAddDescriptor;
import seedu.address.logic.commands.delivery.DeliveryCreateNoteCommand;
import seedu.address.logic.commands.delivery.DeliveryDeleteCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.logic.commands.delivery.DeliveryFindCommand;
import seedu.address.logic.commands.delivery.DeliveryStatusCommand;
import seedu.address.logic.commands.delivery.DeliveryViewCommand;
import seedu.address.logic.commands.user.UserDeleteCommand;
import seedu.address.logic.commands.user.UserLoginCommand;
import seedu.address.logic.commands.user.UserLogoutCommand;
import seedu.address.logic.commands.user.UserRecoverAccountCommand;
import seedu.address.logic.commands.user.UserRegisterCommand;
import seedu.address.logic.commands.user.UserUpdateCommand;
import seedu.address.logic.commands.user.UserUpdateCommand.UserUpdateDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.user.User;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerEditDescriptorBuilder;
import seedu.address.testutil.CustomerUtil;
import seedu.address.testutil.DeliveryAddDescriptorBuilder;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.DeliveryEditDescriptorBuilder;
import seedu.address.testutil.DeliveryUtil;
import seedu.address.testutil.UpdateUserDescriptorBuilder;
import seedu.address.testutil.UserBuilder;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_customerAdd() throws Exception {
        Customer customer = new CustomerBuilder().withCustomerId(Customer.getCustomerCount()).build();
        CustomerAddCommand command = (CustomerAddCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer));
        assertEquals(new CustomerAddCommand(customer), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deliveryCreateNote() throws Exception {
        DeliveryCreateNoteCommand command = (DeliveryCreateNoteCommand) parser.parseCommand(
                DeliveryCreateNoteCommand.COMMAND_WORD + " 1 --note This is a note");
        assertEquals(new DeliveryCreateNoteCommand(1, new Note("This is a note")), command);
    }

    @Test
    public void parseCommand_customerDelete() throws Exception {
        CustomerDeleteCommand command = (CustomerDeleteCommand) parser.parseCommand(
                CustomerDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CustomerDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_customerView() throws Exception {
        CustomerViewCommand command = (CustomerViewCommand) parser.parseCommand(
                CustomerViewCommand.COMMAND_WORD + " " + VALID_VIEW_CUSTOMER_ID_1);
        assertEquals(new CustomerViewCommand(Integer.parseInt(VALID_VIEW_CUSTOMER_ID_1)), command);
    }


    @Test
    public void parseCommand_deliveryStatus() throws Exception {
        DeliveryStatusCommand command = (DeliveryStatusCommand) parser.parseCommand(
                DeliveryStatusCommand.COMMAND_WORD + " "
                        + GABRIELS_MILK.getDeliveryId() + " " + DeliveryStatus.COMPLETED);
        assertEquals(new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), DeliveryStatus.COMPLETED), command);
    }

    @Test
    public void parseCommand_deliveryView() throws Exception {
        DeliveryViewCommand command = (DeliveryViewCommand) parser.parseCommand(
                DeliveryViewCommand.COMMAND_WORD + " " + GABRIELS_MILK.getDeliveryId());
        assertEquals(new DeliveryViewCommand(GABRIELS_MILK.getDeliveryId()), command);
    }

    @Test
    public void parseCommand_customerEdit() throws Exception {

        Customer customer = new CustomerBuilder().build();
        CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder(customer).build();
        CustomerEditCommand command = (CustomerEditCommand) parser
                .parseCommand(CustomerEditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " "
                        + CustomerUtil.getEditCustomerDescriptorDetails(descriptor));

        assertEquals(new CustomerEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteDelivery() throws Exception {
        DeliveryDeleteCommand command = (DeliveryDeleteCommand) parser.parseCommand(
                DeliveryDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeliveryDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_addDelivery() throws Exception {
        DeliveryAddCommand command = (DeliveryAddCommand) parser.parseCommand(
                DeliveryAddCommand.COMMAND_WORD + " "
                        + "Gabriels "
                        + PREFIX_CUSTOMER_ID + VALID_VIEW_CUSTOMER_ID_1 + " "
                        + PREFIX_DATE + VALID_DELIVERY_DATE_3);

        DeliveryBuilder deliveryBuilder = new DeliveryBuilder();
        Delivery validDelivery = deliveryBuilder.build();
        DeliveryAddDescriptorBuilder deliveryAddDescriptorBuilder = new DeliveryAddDescriptorBuilder(validDelivery);
        DeliveryAddDescriptor validDeliveryAddDescriptor = deliveryAddDescriptorBuilder.build();

        assertEquals(new DeliveryAddCommand(validDeliveryAddDescriptor), command);
    }

    @Test
    public void parseCommand_editDelivery() throws Exception {
        Delivery delivery = new DeliveryBuilder().build();
        DeliveryEditDescriptor descriptor = new DeliveryEditDescriptorBuilder(delivery).build();
        DeliveryEditCommand command = (DeliveryEditCommand) parser.parseCommand(DeliveryEditCommand
                .COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + DeliveryUtil.getEditDeliveryDescriptorDetails(descriptor));
        assertEquals(new DeliveryEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    public void parseCommand_deliveryFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        DeliveryFindCommand command = (DeliveryFindCommand) parser.parseCommand(
                DeliveryFindCommand.COMMAND_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new DeliveryFindCommand(new DeliveryNameContainsKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        CustomerFindCommand command = (CustomerFindCommand) parser.parseCommand(
                CustomerFindCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new CustomerFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_customerList() throws Exception {
        assertTrue(parser.parseCommand(CustomerListCommand.COMMAND_WORD) instanceof CustomerListCommand);
        assertTrue(parser.parseCommand(CustomerListCommand.COMMAND_WORD + " 3")
                instanceof CustomerListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser
                .parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_invalidPrefix_throwsParseException() {
        // Cannot wrap lines due to Separator Wrap not allowing lambda on newline
        assertThrows(
                ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser
                        .parseCommand("invalidPrefix list"));
    }

    @Test
    // test user register command
    public void parseCommand_userRegister() throws Exception {
        assertTrue(parser.parseCommand(UserRegisterCommand.COMMAND_WORD + " "
                + PREFIX_USER + " username "
                + PREFIX_PASSWORD + " password "
                + PREFIX_PASSWORD_CONFIRM + " password "
                + PREFIX_SECRET_QUESTION + " secret question "
                + PREFIX_ANSWER + " answer") instanceof UserRegisterCommand);
    }

    @Test
    public void parseCommand_userLogin() throws Exception {
        assertTrue(parser.parseCommand(UserLoginCommand.COMMAND_WORD + " "
                + PREFIX_USER + " username "
                + PREFIX_PASSWORD + " password ") instanceof UserLoginCommand);
    }

    @Test
    public void parseCommand_userLogout() throws Exception {
        assertTrue(parser.parseCommand(UserLogoutCommand.COMMAND_WORD) instanceof UserLogoutCommand);
        assertTrue(parser.parseCommand(UserLogoutCommand.COMMAND_WORD + " abc") instanceof UserLogoutCommand);
    }

    @Test
    public void parseCommand_userDelete() throws Exception {
        assertTrue(parser.parseCommand(UserDeleteCommand.COMMAND_WORD) instanceof UserDeleteCommand);
        assertTrue(parser.parseCommand(UserDeleteCommand.COMMAND_WORD + " abc") instanceof UserDeleteCommand);
    }

    @Test
    public void parseCommand_userRecoverAccount() throws Exception {
        assertTrue(parser.parseCommand(UserRecoverAccountCommand.COMMAND_WORD) instanceof UserRecoverAccountCommand);
        assertTrue(parser.parseCommand(UserRecoverAccountCommand.COMMAND_WORD + " abc")
                instanceof UserRecoverAccountCommand);
        assertTrue(parser.parseCommand(UserRecoverAccountCommand.COMMAND_WORD + " "
                + PREFIX_ANSWER + " answer "
                + PREFIX_PASSWORD + " password "
                + PREFIX_PASSWORD_CONFIRM + " password") instanceof UserRecoverAccountCommand);
    }

    @Test
    public void parseCommand_userUpdate() throws Exception {
        User user = new UserBuilder().build();
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder(user).build();
        UserUpdateCommand command = (UserUpdateCommand) parser.parseCommand(UserUpdateCommand.COMMAND_WORD + " "
                + PREFIX_USER + " " + UserBuilder.DEFAULT_USERNAME + " "
                + PREFIX_PASSWORD + " " + UserBuilder.DEFAULT_PASSWORD + " "
                + PREFIX_PASSWORD_CONFIRM + " " + UserBuilder.DEFAULT_PASSWORD + " "
                + PREFIX_SECRET_QUESTION + " " + UserBuilder.SECRET_QUESTION + " "
                + PREFIX_ANSWER + " " + UserBuilder.ANSWER);
        assertEquals(new UserUpdateCommand(descriptor), command);
    }
}
