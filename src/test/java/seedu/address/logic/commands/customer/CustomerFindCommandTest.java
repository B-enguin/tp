package seedu.address.logic.commands.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CUSTOMERS_MATCHED_LISTED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.CARL;
import static seedu.address.testutil.TypicalCustomers.ELLE;
import static seedu.address.testutil.TypicalCustomers.FIONA;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class CustomerFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
            new UserPrefs(), true);
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
            new UserPrefs(), true);

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        CustomerFindCommand findFirstCommand = new CustomerFindCommand(firstPredicate);
        CustomerFindCommand findSecondCommand = new CustomerFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        CustomerFindCommand findFirstCommandCopy = new CustomerFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCustomerFound() {

        String expectedMessage = String.format(MESSAGE_CUSTOMERS_MATCHED_LISTED, 0, "");

        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        CustomerFindCommand command = new CustomerFindCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true);
        assertEquals(Collections.emptyList(), model.getFilteredCustomerList());
    }

    @Test
    public void execute_multipleKeywords_multipleCustomersFound() {

        String expectedMessage = String.format(MESSAGE_CUSTOMERS_MATCHED_LISTED, 3, "Kurz Elle Kunz");

        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        CustomerFindCommand command = new CustomerFindCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCustomerList());
    }

    @Test
    public void execute_zeroKeywordsLoggedOut_failure() {
        model.setLogoutSuccess();
        expectedModel.setLogoutSuccess();
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        CustomerFindCommand command = new CustomerFindCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandFailure(command, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_multipleKeywordsLoggedOut_failure() {
        model.setLogoutSuccess();
        expectedModel.setLogoutSuccess();
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        CustomerFindCommand command = new CustomerFindCommand(predicate);
        expectedModel.updateFilteredCustomerList(predicate);
        assertCommandFailure(command, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        CustomerFindCommand customerFindCommand = new CustomerFindCommand(predicate);
        String expected = CustomerFindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, customerFindCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
