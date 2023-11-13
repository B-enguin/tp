package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_CUSTOMERS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_DELIVERIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.BENSON;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.DeliveryBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void getCustomer_validId_returnsOptionalCustomer() {
        modelManager.addCustomer(ALICE);
        Optional<Customer> customer = modelManager.getCustomer(1);
        assertEquals(customer.get(), ALICE);
    }

    @Test
    public void getCustomer_invalidId_returnsEmptyOptional() {
        modelManager.addCustomer(ALICE);
        Optional<Customer> customer = modelManager.getCustomer(0);
        assertTrue(customer.isEmpty());
    }

    @Test
    public void getCustomerUsingFilteredList_invalidId_retunsNull() {
        Optional<Customer> customer = modelManager.getCustomer(1);
        assertEquals(Optional.empty(), customer);
    }

    @Test
    public void getCustomerUsingFilteredList_validId_returnsCustomer() {
        modelManager.setLoginSuccess();
        modelManager.addCustomer(ALICE);
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        Optional<Customer> customerOptional = modelManager.getCustomer(1);
        assertEquals(ALICE, customerOptional.get());
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        assertTrue(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomerWithSamePhone_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCustomerWithSamePhone(null));
    }

    @Test
    public void hasCustomerWithSamePhone_customerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCustomerWithSamePhone(ALICE));
    }

    @Test
    public void hasCustomerWithSamePhone_customerInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        assertTrue(modelManager.hasCustomerWithSamePhone(ALICE));
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCustomerList().remove(0));
    }

    // Delivery
    @Test
    public void setDeliveryBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDeliveryBookFilePath(null));
    }

    @Test
    public void setDeliveryBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("delivery/book/file/path");
        modelManager.setDeliveryBookFilePath(path);
        assertEquals(path, modelManager.getDeliveryBookFilePath());
    }

    @Test
    public void getDelivery_validId_returnsOptionalDelivery() {
        modelManager.addDelivery(GABRIELS_MILK);
        Optional<Delivery> delivery = modelManager.getDelivery(1);
        assertEquals(delivery.get(), GABRIELS_MILK);
    }

    @Test
    public void getDelivery_invalidId_returnsEmptyOptional() {
        Optional<Delivery> delivery = modelManager.getDelivery(0);
        assertTrue(delivery.isEmpty());
    }

    @Test
    public void hasDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDelivery(null));
    }

    @Test
    public void hasDelivery_deliveryNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDelivery(GABRIELS_MILK));
    }

    @Test
    public void hasDelivery_deliveryInAddressBook_returnsTrue() {
        modelManager.addDelivery(GABRIELS_MILK);
        assertTrue(modelManager.hasDelivery(GABRIELS_MILK));
    }

    @Test
    public void getFilteredDeliveryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDeliveryList().remove(0));
    }

    @Test
    public void sortDeliveryBookByNameAsc() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.updateSortedDeliveryList(Comparator.comparing(Delivery::getName));
        assertEquals(Arrays.asList(GABRIELS_MILK, GAMBES_RICE), modelManager.getSortedDeliveryList());
    }

    @Test
    public void sortDeliveryBookByNameDesc() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.updateSortedDeliveryList(Comparator.comparing(Delivery::getName).reversed());
        assertEquals(Arrays.asList(GAMBES_RICE, GABRIELS_MILK), modelManager.getSortedDeliveryList());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        DeliveryBook deliveryBook =
                new DeliveryBookBuilder().withDelivery(GABRIELS_MILK).withDelivery(GAMBES_RICE).build();
        AddressBook differentAddressBook = new AddressBook();
        DeliveryBook differentDeliveryBook = new DeliveryBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, deliveryBook, userPrefs, true);
        ModelManager modelManagerCopy = new ModelManager(addressBook, deliveryBook, userPrefs, true);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, deliveryBook, userPrefs, true)));

        // different deliverybook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentDeliveryBook, userPrefs, true)));

        // different filteredCustomerList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, userPrefs, true)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different filteredDeliveryList -> returns false
        String[] deliveryKeywords = GABRIELS_MILK.getName().deliveryName.split("\\s+");
        modelManager.updateFilteredDeliveryList(new DeliveryNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, userPrefs, true)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, differentUserPrefs, true)));
    }

    @Test
    public void showAllCustomerList_success() {
        modelManager.setLoginSuccess();
        modelManager.addCustomer(ALICE);
        modelManager.addCustomer(BENSON);
        modelManager.showAllFilteredCustomerList();
        assertEquals(Arrays.asList(ALICE, BENSON), modelManager.getFilteredCustomerList());
    }

    @Test
    public void showAllDeliveryList_success() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.showAllFilteredDeliveryList();
        assertEquals(Arrays.asList(GABRIELS_MILK, GAMBES_RICE), modelManager.getFilteredDeliveryList());
    }

    @Test
    public void getFilteredCustomerListSize_success() {
        modelManager.setLoginSuccess();
        modelManager.addCustomer(ALICE);
        modelManager.addCustomer(BENSON);
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        assertEquals(2, modelManager.getFilteredCustomerListSize());
    }

    @Test
    public void getFilteredDeliveryListSize_success() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        assertEquals(2, modelManager.getFilteredDeliveryListSize());
    }

    @Test
    public void isFilteredCustomerListEmpty_success() {
        modelManager.setLoginSuccess();
        modelManager.addCustomer(ALICE);
        modelManager.addCustomer(BENSON);
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        assertFalse(modelManager.isFilteredCustomerListEmpty());

        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_NO_CUSTOMERS);
        assertTrue(modelManager.isFilteredCustomerListEmpty());
    }

    @Test
    public void isFilteredDeliveryListEmpty_success() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        assertFalse(modelManager.isFilteredDeliveryListEmpty());

        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_NO_DELIVERIES);
        assertTrue(modelManager.isFilteredDeliveryListEmpty());
    }

    @Test
    public void isSortedDeliveryListEmpty_success() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(GAMBES_RICE);
        modelManager.updateSortedDeliveryList(Comparator.comparing(Delivery::getName));
        assertFalse(modelManager.isSortedDeliveryListEmpty());

        modelManager.updateSortedDeliveryList(Comparator.comparing(Delivery::getName).reversed());
        assertFalse(modelManager.isSortedDeliveryListEmpty());
    }

    @Test
    public void getDelivery_returnsDelivery() {
        modelManager.addDelivery(GABRIELS_MILK);
        assertEquals(modelManager.getDelivery(1), Optional.of(GABRIELS_MILK));
    }

    @Test
    public void userMatches() {
        Model modelManager = new ModelManager();
        User user = new User(new Username("user1"), new Password("password1"), true);
        User wrongUser = new User(new Username("user2"), new Password("password2"), true);
        modelManager.setLoggedInUser(user);
        assertTrue(modelManager.userMatches(user));
        assertFalse(modelManager.userMatches(wrongUser));
    }

    @Test
    public void getLoginStatus_storedUserAndLoggedIn_success() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        DeliveryBook deliveryBook =
                new DeliveryBookBuilder().withDelivery(GABRIELS_MILK).withDelivery(GAMBES_RICE).build();
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("src/test/data/Authentication", "authentication.json"));
        Model modelManager = new ModelManager(addressBook, deliveryBook, userPrefs, true);
        Optional<User> loggedInUser = modelManager.getStoredUser();
        assertTrue(loggedInUser.isPresent());
        User currentLoggedInUser = loggedInUser.get();

        String expectedMessage = "Hello " + currentLoggedInUser.getUsername() + ".";
        String actualMessage = modelManager.getLoginStatus();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getLoginStatus_storedUserAndLoggedOut_success() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        DeliveryBook deliveryBook =
                new DeliveryBookBuilder().withDelivery(GABRIELS_MILK).withDelivery(GAMBES_RICE).build();
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("src/test/data/Authentication", "authentication.json"));
        Model modelManager = new ModelManager(addressBook, deliveryBook, userPrefs, false);

        String expectedMessage = "Logged out. Please login to continue.";
        String actualMessage = modelManager.getLoginStatus();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getLoginStatus_noStoredUser_success() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
        DeliveryBook deliveryBook =
                new DeliveryBookBuilder().withDelivery(GABRIELS_MILK).withDelivery(GAMBES_RICE).build();
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAuthenticationFilePath(Paths.get("src/test/data/Authentication", "authentication.json"));
        Model modelManager = new ModelManager(addressBook, deliveryBook, userPrefs, false);
        modelManager.setLoggedInUser(null);

        String expectedMessage = "No account found. Please register an account.";
        String actualMessage = modelManager.getLoginStatus();

        assertEquals(expectedMessage, actualMessage);
    }
}
