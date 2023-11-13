package seedu.address.logic.commands.customer;

import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandListSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CustomerListCommand.
 */
public class CustomerListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);
        expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() {
        Model emptyModel =
                new ModelManager(new AddressBook(), getTypicalDeliveryBook(), new UserPrefs(), false);
        CommandTestUtil.assertCommandFailure(
                new CustomerListCommand(),
                emptyModel,
                MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_customerListIsEmpty_showsEmpty() {
        Model emptyModel =
                new ModelManager(new AddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);
        CommandTestUtil.assertCommandListSuccess(
                new CustomerListCommand(),
                emptyModel,
                CustomerListCommand.MESSAGE_EMPTY, emptyModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandListSuccess(
                new CustomerListCommand(),
                model,
                CustomerListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandListSuccess(
                new CustomerListCommand(),
                model,
                CustomerListCommand.MESSAGE_SUCCESS, expectedModel);

    }
}
