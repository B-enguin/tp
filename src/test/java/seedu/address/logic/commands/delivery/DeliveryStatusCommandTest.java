package seedu.address.logic.commands.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.testutil.DeliveryBuilder;

public class DeliveryStatusCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);

    @Test
    public void execute_allFieldsValid_success() {
        DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETED;
        Delivery expectedDelivery = new DeliveryBuilder(GABRIELS_MILK).withStatus(deliveryStatus).build();
        DeliveryStatusCommand deliveryStatusCommand =
                new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), deliveryStatus);

        String expectedMessage = String.format(DeliveryStatusCommand.MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.format(expectedDelivery));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(),
                true);
        expectedModel.setDelivery(model.getDeliveryBook().getById(GABRIELS_MILK.getDeliveryId()).get(),
                expectedDelivery);

        assertCommandSuccess(deliveryStatusCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidTargetId_throwsCommandException() {
        DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETED;
        Model m = model;
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(Integer.MAX_VALUE, deliveryStatus);
        assertCommandFailure(deliveryStatusCommand, model, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allFieldsValidLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETED;
        DeliveryStatusCommand deliveryStatusCommand =
                new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), deliveryStatus);

        assertCommandFailure(deliveryStatusCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_invalidTargetIdLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETED;
        DeliveryStatusCommand deliveryStatusCommand =
            new DeliveryStatusCommand(Integer.MAX_VALUE, deliveryStatus);
        assertCommandFailure(deliveryStatusCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void equals() {
        final DeliveryStatusCommand standardCommand =
                new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), DeliveryStatus.COMPLETED);

        // same values -> returns true
        DeliveryStatusCommand commandWithSameValue =
                new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), DeliveryStatus.COMPLETED);
        assertTrue(standardCommand.equals(commandWithSameValue));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeliveryStatusCommand(GAMBES_RICE.getDeliveryId(),
                DeliveryStatus.COMPLETED)));

        // different status -> returns false
        assertFalse(standardCommand.equals(new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(),
                DeliveryStatus.CANCELLED)));
    }

    @Test
    public void toStringMethod() {
        String expected = DeliveryStatusCommand.class.getCanonicalName()
                + "{targetId=" + GABRIELS_MILK.getDeliveryId() + ", status="
                + DeliveryStatus.COMPLETED + "}";
        DeliveryStatusCommand deliveryStatusCommand =
                new DeliveryStatusCommand(GABRIELS_MILK.getDeliveryId(), DeliveryStatus.COMPLETED);
        assertEquals(expected, deliveryStatusCommand.toString());
    }
}
