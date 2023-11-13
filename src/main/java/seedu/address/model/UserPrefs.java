package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.authentication.AuthenticationData;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.StorageManager;


/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path deliveryBookFilePath = Paths.get("data", "deliverybook.json");
    private Path authenticationFilePath = Paths.get("data", "authentication.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setDeliveryBookFilePath(newUserPrefs.getDeliveryBookFilePath());
        setAuthenticationFilePath(newUserPrefs.getAuthenticationFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getDeliveryBookFilePath() {
        return deliveryBookFilePath;
    }

    public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
        requireNonNull(deliveryBookFilePath);
        this.deliveryBookFilePath = deliveryBookFilePath;
    }

    /**
     * Returns the path of the authentication file.
     *
     * @return authenticationPath
     */
    public Path getAuthenticationFilePath() {
        return authenticationFilePath;
    }

    /**
     * Sets the path of the authentication file.
     *
     * @param authenticationFilePath
     */
    public void setAuthenticationFilePath(Path authenticationFilePath) {
        requireNonNull(authenticationFilePath);
        this.authenticationFilePath = authenticationFilePath;
    }

    /**
     * Returns the contents of the authentication file as a String.
     *
     * @param filePath path of the authentication file
     * @return String representation of the authentication file
     * @throws Exception if there is an error reading the file
     */
    public static String readFileAsString(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Returns the stored user.
     *
     * @return Optional of the stored user
     */
    public Optional<User> getStoredUser() {

        AuthenticationData authenticationData;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            authenticationData =
                    objectMapper.readValue(authenticationFilePath.toFile(), AuthenticationData.class);

        } catch (IOException e) {
            logger.fine("Error reading authentication file: " + e.getMessage());
            return Optional.empty();
        }

        // if username in the authentication file is empty, return empty Optional
        if ((!Username.isValidUsername(authenticationData.getUsername()))) {
            return Optional.empty();
        }

        // Create User objects
        User storedUser =
                new User(new Username(authenticationData.getUsername()),
                        new Password(authenticationData.getPassword(), true),
                        true,
                        authenticationData.getSecretQuestion(),
                        authenticationData.getAnswer());
        return Optional.ofNullable(storedUser);
    }

    /**
     * Registers the given {@code user}.
     *
     * @param user
     */
    public boolean registerUser(User user) {
        requireNonNull(user);
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Create an AuthenticationData object from the User
            AuthenticationData authenticationData =
                    new AuthenticationData(user.getUsername().toString(), user.getPassword().toString(),
                            user.getSecretQuestion(), user.getAnswer());

            // Serialize the authenticationData to a JSON file
            objectMapper.writeValue(authenticationFilePath.toFile(), authenticationData);

            logger.info("User registered: " + user.getUsername());
            return true;

        } catch (IOException e) {
            // Handle any exceptions related to file I/O or JSON serialization
            logger.warning("Error writing to authentication file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.warning("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && addressBookFilePath.equals(otherUserPrefs.addressBookFilePath)
                && deliveryBookFilePath.equals(otherUserPrefs.deliveryBookFilePath)
                && authenticationFilePath.equals(otherUserPrefs.authenticationFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, deliveryBookFilePath, authenticationFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal address data file location : " + addressBookFilePath);
        sb.append("\nLocal delivery data file location : " + deliveryBookFilePath);
        sb.append("\nLocal authentication data file location : " + authenticationFilePath);
        return sb.toString();
    }

    /**
     * Deletes the user by deleting the authentication file.
     */
    public void deleteUser() {
        try {
            Files.deleteIfExists(authenticationFilePath);
            logger.info("Files deleted successfully.");
        } catch (IOException e) {
            logger.warning("Error deleting files: " + e.getMessage());
        }
    }
}
