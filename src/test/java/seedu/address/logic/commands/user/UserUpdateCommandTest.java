package seedu.address.logic.commands.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECRET_QUESTION_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.customer.CustomerEditCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.logic.commands.user.UserUpdateCommand.UserUpdateDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.user.User;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerEditDescriptorBuilder;
import seedu.address.testutil.UpdateUserDescriptorBuilder;
import seedu.address.testutil.UserBuilder;

public class UserUpdateCommandTest {

    @TempDir
    public Path tempDir;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);
    private final Logger logger = Logger.getLogger(UserUpdateCommandTest.class.getName());

    @BeforeEach
    public void setUp() {
        Path source = Paths.get("src/test/data/Authentication", "authentication.json");
        Path target = tempDir.resolve("tempAuthentication.json");
        // copy the authentication file to the temp directory
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            logger.info("Error copying authentication.json to tempDir");
        }

        UserPrefs tempPrefs = new UserPrefs();
        tempPrefs.setAuthenticationFilePath(target);

        model.setUserPrefs(tempPrefs); // should store user based on the authentication file in tempDir

    }

    @Test
    public void execute_allFieldsSpecified_success() {

        Optional<User> updatedUser = model.getStoredUser();
        assertTrue(updatedUser.isPresent());
        User currentUpdatedUser = updatedUser.get();
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder(currentUpdatedUser).build();
        UserUpdateCommand updateCommand = new UserUpdateCommand(descriptor);

        String expectedMessage = UserUpdateCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(model.getUserPrefs()), model.getUserLoginStatus());
        expectedModel.setLoggedInUser(currentUpdatedUser);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        User updatedUser = new UserBuilder().withUsername(VALID_USERNAME_FOODBEAR)
                .withSecretQuestion(VALID_SECRET_QUESTION_FOODBEAR).withAnswer(VALID_ANSWER_FOODBEAR).build();
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder(updatedUser).build();
        UserUpdateCommand updateCommand = new UserUpdateCommand(descriptor);

        String expectedMessage = UserUpdateCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(model.getUserPrefs()), model.getUserLoginStatus());
        expectedModel.setLoggedInUser(updatedUser);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        // succeed because the check is done by the parser in the previous step
        User updatedUser = new UserBuilder().build();
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder().build();
        UserUpdateCommand updateCommand = new UserUpdateCommand(descriptor);

        String expectedMessage = UserUpdateCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(model.getUserPrefs()), model.getUserLoginStatus());
        expectedModel.setLoggedInUser(updatedUser);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_allFieldsSpecifiedButLoggedOut_failure() {
        // set state of model to be logged out
        model.setLogoutSuccess();

        Customer editedCustomer = new CustomerBuilder().build();
        CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder(editedCustomer).build();
        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_someFieldsSpecifiedButLoggedOut_failure() {
        // set state of model to be logged out
        model.setLogoutSuccess();
        User updatedUser = new UserBuilder().build();
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder(updatedUser).build();
        UserUpdateCommand updateCommand = new UserUpdateCommand(descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void equals() {
        final UserUpdateCommand standardCommand = new UserUpdateCommand(DESC_AARON);

        // same values -> returns true
        UserUpdateDescriptor copyDescriptor = new UserUpdateDescriptor(DESC_AARON);
        UserUpdateCommand commandWithSameValues = new UserUpdateCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new UserLogoutCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UserUpdateCommand(DESC_FOODBEAR)));
    }

    @Test
    public void toStringMethod() {
        UserUpdateDescriptor userUpdateDescriptor = new UserUpdateDescriptor();
        UserUpdateCommand updateCommand = new UserUpdateCommand(userUpdateDescriptor);
        String expected = UserUpdateCommand.class.getCanonicalName() + "{updateUserDescriptor="
                + userUpdateDescriptor + "}";
        assertEquals(expected, updateCommand.toString());
    }
}
