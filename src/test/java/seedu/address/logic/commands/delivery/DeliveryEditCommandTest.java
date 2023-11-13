package seedu.address.logic.commands.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EDIT_CHIPS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EDIT_MILK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.TOO_LARGE_CUSTOMER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_SHIPPED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_TOO_LARGE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.DeliveryEditDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class DeliveryEditCommandTest {
    private Model modelLoggedIn = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(),
            true);

    private Model modelLoggedOut = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
            new UserPrefs(), false);

    @Test
    public void constructor_nullDeliveryEditDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryEditCommand(null, null));
    }

    @Test
    public void execute_allFieldsSpecified_success() {

        Customer editedCustomer = modelLoggedIn.getFilteredCustomerList().get(0);
        Delivery editedDelivery =
                new DeliveryBuilder().withCustomer(editedCustomer).withOrderDate("2021-12-12")
                        .withNote("TestFF").withName("Vanilla Cake")
                        .withDeliveryDate("2027-12-12").withStatus(VALID_STATUS_SHIPPED).build();

        DeliveryEditDescriptor descriptor = new DeliveryEditDescriptorBuilder(editedDelivery).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeliveryEditCommand.MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.format(editedDelivery));

        Model expectedModel = new ModelManager(new AddressBook(modelLoggedIn.getAddressBook()),
                new DeliveryBook(modelLoggedIn.getDeliveryBook()),
                new UserPrefs(), modelLoggedIn.getUserLoginStatus());

        expectedModel.setDelivery(modelLoggedIn.getFilteredDeliveryList().get(0), editedDelivery);

        assertCommandSuccess(editCommand, modelLoggedIn, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidDelivery_throwsCommandException() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.withCustomerId(VALID_CUSTOMER_ID_1).build();

        Delivery editedDelivery =
                new DeliveryBuilder().withCustomer(validCustomer).withOrderDate("2021-12-12")
                        .withNote("TestFF").withName("Vanilla Cake")
                        .withDeliveryDate("2027-12-12").withStatus(VALID_STATUS_SHIPPED).build();

        DeliveryEditDescriptor descriptor = new DeliveryEditDescriptorBuilder(editedDelivery).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_TOO_LARGE, descriptor);


        Model expectedModel = new ModelManager(new AddressBook(modelLoggedIn.getAddressBook()),
                new DeliveryBook(modelLoggedIn.getDeliveryBook()),
                new UserPrefs(), modelLoggedIn.getUserLoginStatus());

        assertThrows(CommandException.class, MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX, () ->
                editCommand.execute(expectedModel));
    }

    @Test
    public void execute_invalidDeliveryDate_throwsCommandException() { //not done
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.withCustomerId(VALID_CUSTOMER_ID_1).build();

        Delivery editedDelivery =
                new DeliveryBuilder().withCustomer(validCustomer).withOrderDate("2021-12-12")
                        .withNote("TestFF").withName("Vanilla Cake")
                        .withDeliveryDate(INVALID_DELIVERY_DATE).withStatus(VALID_STATUS_SHIPPED).build();

        DeliveryEditDescriptor descriptor = new DeliveryEditDescriptorBuilder(editedDelivery).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_FIRST_PERSON, descriptor);


        Model expectedModel = new ModelManager(new AddressBook(modelLoggedIn.getAddressBook()),
                new DeliveryBook(modelLoggedIn.getDeliveryBook()),
                new UserPrefs(), modelLoggedIn.getUserLoginStatus());

        assertThrows(CommandException.class, MESSAGE_INVALID_DELIVERY_DATE, () ->
                editCommand.execute(expectedModel));
    }

    //need to check if this is ok
    @Test
    public void execute_invalidCustomerId_throwsCommandException() { //not done
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer invalidCustomer = personBuilder.withCustomerId(TOO_LARGE_CUSTOMER_ID).build();

        Delivery editedDelivery =
                new DeliveryBuilder().withCustomer(invalidCustomer).withOrderDate("2021-12-12")
                        .withNote("TestFF").withName("Vanilla Cake")
                        .withDeliveryDate(INVALID_DELIVERY_DATE).withStatus(VALID_STATUS_SHIPPED).build();

        DeliveryEditDescriptor descriptor = new DeliveryEditDescriptorBuilder(editedDelivery).build();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(INDEX_FIRST_PERSON, descriptor);


        Model expectedModel = new ModelManager(new AddressBook(modelLoggedIn.getAddressBook()),
                new DeliveryBook(modelLoggedIn.getDeliveryBook()),
                new UserPrefs(), modelLoggedIn.getUserLoginStatus());

        assertThrows(CommandException.class, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX, () ->
                editCommand.execute(expectedModel));
    }

    @Test
    public void execute_deliveryAcceptedByModelLoggedOut_addFailure() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.build();


        Delivery validDelivery = new DeliveryBuilder().withCustomer(validCustomer).build();

        DeliveryEditCommand deliveryEditCommand = new DeliveryEditCommand(INDEX_FIRST_PERSON, new
                DeliveryEditDescriptorBuilder(validDelivery).build());
        assertThrows(CommandException.class, Messages.MESSAGE_USER_NOT_AUTHENTICATED, () ->
                deliveryEditCommand.execute(modelLoggedOut));

    }

    @Test
    public void equals() {
        final DeliveryEditCommand standardCommand = new DeliveryEditCommand(INDEX_FIRST_PERSON, DESC_EDIT_CHIPS);

        // same values -> returns true
        DeliveryEditDescriptor copyDescriptor = new DeliveryEditDescriptor(DESC_EDIT_CHIPS);
        DeliveryEditCommand commandWithSameValues = new DeliveryEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeliveryEditCommand(INDEX_SECOND_PERSON, DESC_EDIT_CHIPS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeliveryEditCommand(INDEX_FIRST_PERSON, DESC_EDIT_MILK)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeliveryEditDescriptor deliveryEditDescriptor = new DeliveryEditDescriptor();
        DeliveryEditCommand editCommand = new DeliveryEditCommand(index, deliveryEditDescriptor);
        String expected = DeliveryEditCommand.class.getCanonicalName() + "{id=" + index + ", deliveryEditDescriptor="
                + deliveryEditDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}

