package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ===== AddressBook =================================================================
    Path getAddressBookFilePath();

    Path getAddressBookParentPath();

    Optional<ReadOnlyBook<Customer>> readAddressBook() throws DataLoadingException;

    Optional<ReadOnlyBook<Customer>> readAddressBook(Path filePath) throws DataLoadingException;

    void saveAddressBook(ReadOnlyBook<Customer> addressBook) throws IOException;

    void saveAddressBook(ReadOnlyBook<Customer> addressBook, Path filePath) throws IOException;

    // ===== DeliveryBook =================================================================
    Path getDeliveryBookFilePath();

    void setDeliveryBookReference(ReadOnlyBook<Customer> customerBook);

    Optional<ReadOnlyBook<Delivery>> readDeliveryBook() throws DataLoadingException;

    Optional<ReadOnlyBook<Delivery>> readDeliveryBook(Path filePath) throws DataLoadingException;

    void saveDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) throws IOException;

    void saveDeliveryBook(ReadOnlyBook<Delivery> deliveryBook, Path filePath) throws IOException;

}
