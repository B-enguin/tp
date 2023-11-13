package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.customer.Customer;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements BookStorage<Customer> {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    /**
     * Creates a JsonAddressBookStorage to access AddressBook data stored as a json file.
     *
     * @param filePath path to file storing data.
     */
    public JsonAddressBookStorage(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    public Path getBookFilePath() {
        return filePath;
    }

    public Path getBookParentPath() {
        return filePath.getParent();
    }

    @Override
    public Optional<ReadOnlyBook<Customer>> readBook() throws DataLoadingException {
        return readBook(filePath);
    }

    /**
     * Similar to {@link #readBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyBook<Customer>> readBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveBook(ReadOnlyBook<Customer> addressBook) throws IOException {
        requireNonNull(addressBook);

        saveBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveBook(ReadOnlyBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBook(ReadOnlyBook<Customer> addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

}
