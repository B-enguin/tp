package seedu.address.logic.commands.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.StorageManager;

public class UserDeleteCommandTest {

    @TempDir
    public Path tempDir;

    private Logger logger = LogsCenter.getLogger(StorageManager.class);

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        User user = new User(new Username("username"), new Password("password"), false);
        assertThrows(NullPointerException.class, () -> new UserDeleteCommand().execute(null));
    }

    @Test
    // test when storedUser is null
    public void execute_storedUserDoesNotExist_throwsCommandException() {

        UserPrefs tempPrefs = new UserPrefs();
        tempPrefs.setAuthenticationFilePath(Paths.get(""));
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                tempPrefs, false);
        UserDeleteCommand userDeleteCommand = new UserDeleteCommand();

        assertCommandFailure(userDeleteCommand, model, UserDeleteCommand.MESSAGE_NO_ACCOUNT);
    }

    @Test
    // test success when user is not logged in and storedUser exists
    public void execute_deleteWhenLoggedOutAndStoredUserExists_success() {
        UserPrefs tempPrefs = new UserPrefs();
        Path source = Paths.get("src/test/data/Authentication", "authentication.json");
        Path destination = tempDir.resolve("temp.json");
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            logger.info("Error copying file");
        }
        tempPrefs.setAuthenticationFilePath(destination);

        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                tempPrefs, false);

        UserDeleteCommand userDeleteCommand = new UserDeleteCommand();

        ModelManager expectedModel = new ModelManager(new AddressBook(), new DeliveryBook(),
                model.getUserPrefs(), false);
        expectedModel.setLoggedInUser(null);

        assertCommandSuccess(userDeleteCommand, model, UserDeleteCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_deleteWhenLoggedInAndStoredUserExists_success() {
        UserPrefs tempPrefs = new UserPrefs();
        Path source = Paths.get("src/test/data/Authentication", "authentication.json");
        Path destination = tempDir.resolve("temp.json");
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            logger.info("Error copying file");
        }
        tempPrefs.setAuthenticationFilePath(destination);

        Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(),
                tempPrefs, true);

        UserDeleteCommand userDeleteCommand = new UserDeleteCommand();

        ModelManager expectedModel = new ModelManager(new AddressBook(), new DeliveryBook(),
                model.getUserPrefs(), false);
        expectedModel.setLoggedInUser(null);

        assertCommandSuccess(userDeleteCommand, model, UserDeleteCommand.MESSAGE_SUCCESS, expectedModel, true);
    }

}
