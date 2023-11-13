package seedu.address.logic.commands.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandListSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.Sort;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryStatus;

public class DeliveryListCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandListSuccess(new DeliveryListCommand(null, null, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
    }


    @Test
    public void execute_listIsFiltered_showsEverything() {

        assertCommandListSuccess(new DeliveryListCommand(null, null, null, null),
            model, DeliveryListCommand.MESSAGE_SUCCESS, model);

        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.CREATED, null, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.COMPLETED, null, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS,
            model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.CANCELLED, null, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        // customer id
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.COMPLETED, 2, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.COMPLETED, 2, null, null), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);

        // expected delivery date
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.COMPLETED, null, new DeliveryDate(VALID_DELIVERY_DATE_3), null),
            model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_listIsFiltered_empty() {
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.CREATED, 1, new DeliveryDate("2001-12-12"), null), model,
            DeliveryListCommand.MESSAGE_EMPTY, model);
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.SHIPPED, 1, new DeliveryDate("2001-12-12"), null), model,
            DeliveryListCommand.MESSAGE_EMPTY, model);
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.COMPLETED, 1, new DeliveryDate("2001-12-12"), null), model,
            DeliveryListCommand.MESSAGE_EMPTY, model);
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.CANCELLED, 1, new DeliveryDate("2001-12-12"), null), model,
            DeliveryListCommand.MESSAGE_EMPTY, model);
    }

    @Test
    public void execute_listIsFilteredAndSortedAscending_showsSameList() {
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.CREATED, null, null, Sort.ASC), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.SHIPPED, 2, null, Sort.ASC), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
        assertCommandListSuccess(
            new DeliveryListCommand(DeliveryStatus.COMPLETED, 2, new DeliveryDate(VALID_DELIVERY_DATE_3), Sort.ASC),
            model,
            DeliveryListCommand.MESSAGE_SUCCESS,
            model);
        assertCommandListSuccess(new DeliveryListCommand(DeliveryStatus.CANCELLED, null, null, Sort.ASC), model,
            DeliveryListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_listIsSortedAscending_showsSameList() {
        assertCommandListSuccess(new DeliveryListCommand(null, null, null, Sort.ASC), model,
            DeliveryListCommand.MESSAGE_SUCCESS,
            model);
    }

    @Test
    public void execute_listIsSortedDescending_showsSameList() {
        assertCommandListSuccess(new DeliveryListCommand(null, null, null, Sort.DESC), model,
            DeliveryListCommand.MESSAGE_SUCCESS,
            model);
    }

    @Test
    public void execute_listIsNotFilteredLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        assertCommandFailure(new DeliveryListCommand(null, null, null, null), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_listIsFilteredLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.CREATED, null, null, null), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, null), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.COMPLETED, null, null, null), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.CANCELLED, null, null, null), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_listIsFilteredAndSortedAscendingLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.CREATED, null, null, Sort.ASC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, Sort.ASC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.COMPLETED, null, null, Sort.ASC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
        assertCommandFailure(new DeliveryListCommand(DeliveryStatus.CANCELLED, null, null, Sort.ASC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_listIsSortedAscendingLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        assertCommandFailure(new DeliveryListCommand(null, null, null, Sort.ASC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_listIsSortedDescendingLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        assertCommandFailure(new DeliveryListCommand(null, null, null, Sort.DESC), model,
            Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }


    @Test
    public void equals() {
        DeliveryListCommand deliveryListCommand = new DeliveryListCommand(null, null, null, null);
        DeliveryListCommand deliveryListCommand1 = new DeliveryListCommand(DeliveryStatus.CREATED, null, null, null);
        DeliveryListCommand deliveryListCommand2 = new DeliveryListCommand(DeliveryStatus.CREATED, null, null,
            Sort.ASC);
        DeliveryListCommand deliveryListCommand3 = new DeliveryListCommand(DeliveryStatus.CREATED, null, null,
            Sort.DESC);
        DeliveryListCommand deliveryListCommand4 = new DeliveryListCommand(DeliveryStatus.COMPLETED, null, null,
            Sort.ASC);
        DeliveryListCommand deliveryListCommand5 = new DeliveryListCommand(DeliveryStatus.COMPLETED, 1, null,
            Sort.ASC);
        DeliveryListCommand deliveryListCommand6 = new DeliveryListCommand(DeliveryStatus.COMPLETED, 2, null,
            Sort.ASC);
        DeliveryListCommand deliveryListCommand7 = new DeliveryListCommand(DeliveryStatus.COMPLETED, 1,
            new DeliveryDate(VALID_DELIVERY_DATE_1),
            Sort.ASC);
        DeliveryListCommand deliveryListCommand8 = new DeliveryListCommand(DeliveryStatus.COMPLETED, 1,
            new DeliveryDate(VALID_DELIVERY_DATE_2),
            Sort.ASC);
        // same object -> returns true
        assertTrue(deliveryListCommand.equals(deliveryListCommand));

        // same values -> returns true
        DeliveryListCommand deliveryListCommandCopy = new DeliveryListCommand(null, null, null, null);
        assertTrue(deliveryListCommand.equals(deliveryListCommandCopy));

        // different types -> returns false
        assertFalse(deliveryListCommand.equals(1));

        // null -> returns false
        assertFalse(deliveryListCommand.equals(null));

        // different delivery status -> returns false
        assertFalse(deliveryListCommand.equals(deliveryListCommand1));

        // different sort -> returns false
        assertNotEquals(deliveryListCommand1, deliveryListCommand2);
        assertEquals(deliveryListCommand1, deliveryListCommand3);
        assertNotEquals(deliveryListCommand1, deliveryListCommand4);

        // different customer id -> returns false
        assertNotEquals(deliveryListCommand6, deliveryListCommand5);

        // different expected delivery date -> returns false
        assertNotEquals(deliveryListCommand7, deliveryListCommand8);
    }
}
