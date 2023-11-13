package seedu.address.logic.commands.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELIVERY_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DeliveryNameContainsKeywordsPredicate;

public class DeliveryFindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
            new UserPrefs(), true);
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
            new UserPrefs(), true);

    @Test
    public void equals() {
        DeliveryNameContainsKeywordsPredicate firstPredicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DeliveryNameContainsKeywordsPredicate secondPredicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("second"));

        DeliveryFindCommand deliveryFindFirstCommand = new DeliveryFindCommand(firstPredicate);
        DeliveryFindCommand deliveryFindSecondCommand = new DeliveryFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(deliveryFindFirstCommand.equals(deliveryFindFirstCommand));

        // same values -> returns true
        DeliveryFindCommand findFirstCommandCopy = new DeliveryFindCommand(firstPredicate);
        assertTrue(deliveryFindFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(deliveryFindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deliveryFindFirstCommand.equals(null));

        // different query -> returns false
        assertFalse(deliveryFindFirstCommand.equals(deliveryFindSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDeliveryFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_LISTED_OVERVIEW, 0);
        DeliveryNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        DeliveryFindCommand command = new DeliveryFindCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true);
        assertEquals(Collections.emptyList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_multipleKeywords_multipleDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_LISTED_OVERVIEW, 2);
        DeliveryNameContainsKeywordsPredicate predicate = preparePredicate("Gabriel Gambe");
        DeliveryFindCommand command = new DeliveryFindCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true);
        assertEquals(Arrays.asList(GABRIELS_MILK, GAMBES_RICE), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_zeroKeywordsLoggedOut_failure() {
        model.setLogoutSuccess();
        expectedModel.setLogoutSuccess();
        DeliveryNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        DeliveryFindCommand command = new DeliveryFindCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandFailure(command, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_multipleKeywordsLoggedOut_failure() {
        model.setLogoutSuccess();
        expectedModel.setLogoutSuccess();
        DeliveryNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        DeliveryFindCommand command = new DeliveryFindCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandFailure(command, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void toStringMethod() {
        DeliveryNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        String expected = DeliveryFindCommand.class.getCanonicalName()
            + "{predicate=" + predicate + "}";
        DeliveryFindCommand deliveryFindCommand = new DeliveryFindCommand(predicate);
        assertEquals(expected, deliveryFindCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DeliveryNameContainsKeywordsPredicate}.
     */
    private DeliveryNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DeliveryNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
