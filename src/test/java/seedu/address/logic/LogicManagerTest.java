package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.customer.CustomerAddCommand;
import seedu.address.logic.commands.customer.CustomerListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonDeliveryBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.ui.ListItem;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        model.setLoginSuccess();
        JsonAddressBookStorage addressBookStorage =
            new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonDeliveryBookStorage deliveryBookStorage =
            new JsonDeliveryBookStorage(temporaryFolder.resolve("deliveryBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, deliveryBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "customer delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = CustomerListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, CustomerListCommand.MESSAGE_EMPTY, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
            LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
            LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredCustomerList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
            new UserPrefs(), model.getUserLoginStatus());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e               the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveBook(ReadOnlyBook<Customer> addressBook, Path filePath) throws IOException {
                throw e;
            }
        };
        JsonDeliveryBookStorage deliveryBookStorage =
            new JsonDeliveryBookStorage(temporaryFolder.resolve("deliveryBook.json"));

        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, deliveryBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = CustomerAddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;

        Customer expectedCustomer = new CustomerBuilder(AMY)
            .withCustomerId(Customer.getCustomerCount()).build();


        ModelManager expectedModel = new ModelManager();
        // sets the expected model to be in logged in state
        expectedModel.setLoginSuccess();
        expectedModel.addCustomer(expectedCustomer);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getUiListCustomer() {
        ObservableList<ListItem> customers = this.model.getFilteredDeliveryList().stream()
            .map(delivery -> new ListItem(String.format("[%d] %s", delivery.getDeliveryId(), delivery.getName()),
                delivery.getOrderDate().toString(), delivery.getDeliveryDate().toString()))
            .collect(Collectors.toCollection(
                FXCollections::observableArrayList));

        model.updateFilteredCustomerList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        assertEquals(logic.getUiList(), customers);

        ObservableList<ListItem> deliveries = this.model.getFilteredDeliveryList().stream().map(
                delivery -> new ListItem(String.format("[%d] %s", delivery.getDeliveryId(), delivery.getName()),
                    delivery.getOrderDate().toString(), delivery.getDeliveryDate().toString()))
            .collect(Collectors.toCollection(
                FXCollections::observableArrayList));

        model.updateFilteredDeliveryList(Model.PREDICATE_SHOW_ALL_DELIVERIES);
        assertEquals(logic.getUiList(), deliveries);

        // sort
        model.updateSortedDeliveryList(Comparator.comparing(Delivery::getName));

        assertEquals(logic.getUiList(), deliveries);
    }

    @Test
    public void getLoginStatus() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(temporaryFolder.resolve("authentication.json"));
        model.setUserPrefs(userPrefs);
        JsonAddressBookStorage addressBookStorage =
            new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonDeliveryBookStorage deliveryBookStorage =
            new JsonDeliveryBookStorage(temporaryFolder.resolve("deliveryBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, deliveryBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        assertEquals(logic.getLoginStatus(), model.getLoginStatus());
    }
}
