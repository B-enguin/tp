package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

public class UserPrefsTest {

    @TempDir
    public Path tempDir;

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setAuthenticationPath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAuthenticationFilePath(null));
    }

    @Test
    public void readFileAsString_invalidFilePath_throwsException() {
        assertThrows(Exception.class, () -> UserPrefs.readFileAsString("invalidFilePath"));
    }

    @Test
    public void getStoredUser_noUserStored_returnsNull() {
        // assume authentication file is empty
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("src/test/data/Authentication",
                "authentication_noStoredUser.json"));
        assertTrue(userPrefs.getStoredUser().isEmpty());
    }

    @Test
    public void getStoredUser_validUserStored_returnsUser() {
        UserPrefs userPrefs = new UserPrefs();
        Username username = new Username("username");
        Password password = new Password("password");
        String secretQuestion = "Question?";
        String answer = "answer";
        User user = new User(username, password, true, secretQuestion, answer);
        userPrefs.setAuthenticationFilePath(Paths.get("src/test/data/Authentication", "authentication.json"));

        Optional<User> storedUser = userPrefs.getStoredUser();
        assertTrue(storedUser.isPresent());
        User currentStoredUser = storedUser.get();
        assertTrue(userPrefs.getStoredUser().isPresent());
        assertEquals(userPrefs.getStoredUser(), Optional.ofNullable(user));
    }

    @Test
    public void registerUser_nullUser_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.registerUser(null));
    }

    @Test
    public void registerUser_validUser_success() {
        Path authTestPath = tempDir.resolve("authTest.json");
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(authTestPath);
        Username username = new Username("username");
        Password password = new Password("password");
        String secretQuestion = "Question?";
        String answer = "answer";
        User user = new User(username, password, true, secretQuestion, answer);

        assertTrue(userPrefs.registerUser(user));
        assertEquals(userPrefs.getStoredUser(), Optional.ofNullable(user));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();

        // null -> returns false
        assertFalse(userPrefs.equals(null));

        // different types -> returns false
        assertFalse(userPrefs.equals(5));

        // different addressBookFilePath -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(userPrefs.equals(differentUserPrefs));

        // different authenticationPath -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAuthenticationFilePath(Paths.get("differentFilePath"));
        assertFalse(userPrefs.equals(differentUserPrefs));
    }

    @Test
    public void hashCode_sameHashCode_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();
        assertTrue(userPrefs.hashCode() == userPrefs.hashCode());
    }

    @Test
    public void hashCode_differentHashCode_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(userPrefs.hashCode() == differentUserPrefs.hashCode());
    }

    @Test
    public void getAuthenticationFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("data", "authentication.json"));
        assertTrue(userPrefs.getAuthenticationFilePath().equals(Paths.get("data", "authentication.json")));
    }

    @Test
    public void deleteUser_noFilesFound_noExceptionThrown() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("data", ""));
        userPrefs.deleteUser();
    }

}
