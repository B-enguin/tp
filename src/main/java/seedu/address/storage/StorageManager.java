package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BookStorage<Customer> addressBookStorage;
    private BookStorageWithReference<Delivery, Customer> deliveryBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(
        BookStorage<Customer> addressBookStorage,
        BookStorageWithReference<Delivery, Customer> deliveryBookStorage,
        UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.deliveryBookStorage = deliveryBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getBookFilePath();
    }

    public Path getAddressBookParentPath() {
        return addressBookStorage.getBookParentPath();
    }

    @Override
    public Optional<ReadOnlyBook<Customer>> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getBookFilePath());
    }

    @Override
    public Optional<ReadOnlyBook<Customer>> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyBook<Customer> addressBook) throws IOException {
        assert addressBook != null;

        saveAddressBook(addressBook, addressBookStorage.getBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyBook<Customer> addressBook, Path filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveBook(addressBook, filePath);
    }

    // ================ DeliveryBook methods ==============================

    @Override
    public Path getDeliveryBookFilePath() {
        return deliveryBookStorage.getBookFilePath();
    }

    @Override
    public void setDeliveryBookReference(ReadOnlyBook<Customer> customerBook) {
        deliveryBookStorage.setReferencingBook(customerBook);
    }

    @Override
    public Optional<ReadOnlyBook<Delivery>> readDeliveryBook() throws DataLoadingException {
        return readDeliveryBook(deliveryBookStorage.getBookFilePath());
    }

    @Override
    public Optional<ReadOnlyBook<Delivery>> readDeliveryBook(Path filePath) throws DataLoadingException {
        assert filePath != null;

        logger.fine("Attempting to read data from file: " + filePath);
        return deliveryBookStorage.readBook(filePath);
    }

    @Override
    public void saveDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) throws IOException {
        assert deliveryBook != null;

        saveDeliveryBook(deliveryBook, deliveryBookStorage.getBookFilePath());
    }

    @Override
    public void saveDeliveryBook(ReadOnlyBook<Delivery> deliveryBook, Path filePath) throws IOException {
        assert deliveryBook != null;
        assert filePath != null;

        logger.fine("Attempting to write to data file: " + filePath);
        deliveryBookStorage.saveBook(deliveryBook, filePath);
    }

}
