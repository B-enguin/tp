package seedu.address.logic.commands.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

public class UserLoginCommandTest {
    @Test
    public void execute_userAlreadyLoggedIn_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), true);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(user);
        UserLoginCommand userLoginCommand = new UserLoginCommand(user);

        assertCommandFailure(userLoginCommand, model, UserLoginCommand.MESSAGE_ALREADY_LOGGED_IN);
    }

    @Test
    public void execute_userLogin_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), false);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(user);
        UserLoginCommand userLoginCommand = new UserLoginCommand(user);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.setLoggedInUser(user);
        expectedModel.setLoginSuccess();
        expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        assertCommandSuccess(userLoginCommand, model, UserLoginCommand.MESSAGE_SUCCESS,
                expectedModel, true);
    }

    @Test
    public void execute_userLoginWrongCredentials_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), false);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(user);

        User otherUser = new User(new Username("username"), new Password("password123"), false);
        UserLoginCommand userLoginCommand = new UserLoginCommand(otherUser);

        assertCommandFailure(userLoginCommand, model, UserLoginCommand.MESSAGE_WRONG_CREDENTIALS);
    }

    @Test
    public void execute_userLoginButNoStoredUserFound_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                new UserPrefs(), false);
        User user = new User(new Username("username"), new Password("password"), false);
        model.setLoggedInUser(null);
        UserLoginCommand userLoginCommand = new UserLoginCommand(user);

        assertCommandFailure(userLoginCommand, model, UserLoginCommand.MESSAGE_NO_REGISTERED_ACCOUNT_FOUND);
    }

    @Test
    public void equals() {
        User user = new User(new Username("username"), new Password("password"), false);
        UserLoginCommand userLoginCommand = new UserLoginCommand(user);

        // same object -> returns true
        assertTrue(userLoginCommand.equals(userLoginCommand));

        // same values -> returns true
        UserLoginCommand userLoginCommandCopy = new UserLoginCommand(user);
        assertTrue(userLoginCommand.equals(userLoginCommandCopy));

        // different types -> returns false
        assertFalse(userLoginCommand.equals(1));

        // null -> returns false
        assertFalse(userLoginCommand.equals(null));

        // different person -> returns false
        User user2 = new User(new Username("username2"), new Password("password2"), false);
        UserLoginCommand userLoginCommand2 = new UserLoginCommand(user2);
        assertFalse(userLoginCommand.equals(userLoginCommand2));
    }
}
