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
import seedu.address.model.delivery.Delivery;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonDeliveryBookStorage extends BookStorageWithReference<Delivery, Customer> {

    private static final Logger logger = LogsCenter.getLogger(JsonDeliveryBookStorage.class);

    private Path filePath;

    /**
     * Constructor for JsonDeliveryBookStorage.
     *
     * @param filePath path to delivery data file.
     */
    public JsonDeliveryBookStorage(Path filePath) {
        super();

        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public void setReferencingBook(ReadOnlyBook<Customer> referenceBook) {
        requireNonNull(referenceBook);

        super.setReferenceBook(Optional.of(referenceBook));
    }

    public Path getBookFilePath() {
        return filePath;
    }

    public Path getBookParentPath() {
        return filePath.getParent();
    }

    @Override
    public Optional<ReadOnlyBook<Delivery>> readBook() throws DataLoadingException {
        if (super.getReferenceBook().isEmpty()) {
            throw new DataLoadingException(
                new NullPointerException(String.format("%s requires reference to be set before reading",
                    BookStorageWithReference.class.getSimpleName())));
        }
        return readBook(filePath);
    }

    /**
     * Similar to {@link #readBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyBook<Delivery>> readBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        if (super.getReferenceBook().isEmpty()) {
            throw new DataLoadingException(
                new NullPointerException(String.format("%s requires reference to be set before reading",
                    BookStorageWithReference.class.getSimpleName())));
        }

        Optional<JsonSerializableDeliveryBook> jsonDeliveryBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableDeliveryBook.class);

        if (!jsonDeliveryBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDeliveryBook.get().toModelType(super.getReferenceBook()));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveBook(ReadOnlyBook<Delivery> deliveryBook) throws IOException {
        requireNonNull(deliveryBook);

        saveBook(deliveryBook, filePath);
    }

    /**
     * Similar to {@link #saveBook(ReadOnlyBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBook(ReadOnlyBook<Delivery> deliveryBook, Path filePath) throws IOException {
        requireNonNull(deliveryBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDeliveryBook(deliveryBook), filePath);
    }

}
