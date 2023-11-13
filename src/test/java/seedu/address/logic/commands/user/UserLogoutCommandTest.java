package seedu.address.logic.commands.user;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_CUSTOMERS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_DELIVERIES;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

public class UserLogoutCommandTest {
    @Test
    public void execute_userAlreadyLoggedOut_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), false);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(user);
        UserLogoutCommand userLogoutCommand = new UserLogoutCommand();

        assertCommandFailure(userLogoutCommand, model, UserLogoutCommand.MESSAGE_ALREADY_LOGGED_OUT);
    }

    @Test
    public void execute_userLogout_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), true);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(user);
        UserLogoutCommand userLogoutCommand = new UserLogoutCommand();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.setLoggedInUser(user);
        expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_NO_CUSTOMERS);
        expectedModel.updateFilteredDeliveryList(PREDICATE_SHOW_NO_DELIVERIES);
        expectedModel.setUiListCustomer();
        expectedModel.setLogoutSuccess();

        assertCommandSuccess(userLogoutCommand, model, UserLogoutCommand.MESSAGE_SUCCESS, expectedModel, true);
    }
}
