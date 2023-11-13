package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.TOO_LARGE_CUSTOMER_ID;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.delivery.DeliveryAddCommand;
import seedu.address.logic.commands.delivery.DeliveryAddCommand.DeliveryAddDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.user.User;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.DeliveryAddDescriptorBuilder;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.ui.ListItem;

public class DeliveryAddCommandTest {

    @Test
    public void constructor_nullDeliveryAddDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryAddCommand(null));
    }

    @Test
    public void execute_deliveryAcceptedByModel_addSuccessful() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.build();

        ModelStubStoringDeliveries modelStub = new ModelStubStoringDeliveries();
        Delivery validDelivery = new DeliveryBuilder().withCustomer(validCustomer).build();
        DeliveryAddDescriptor deliveryAddDescriptor = new DeliveryAddDescriptorBuilder(validDelivery).build();


        CommandResult commandResult = null;
        try {
            commandResult = new DeliveryAddCommand(deliveryAddDescriptor)
                    .execute(modelStub);
        } catch (CommandException e) {
            e.printStackTrace();
        }


        assertEquals(String.format(DeliveryAddCommand.MESSAGE_SUCCESS, Messages
                .format(modelStub.getDelivery(0).get())), commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_invalidPerson_throwsCommandException() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer invalidCustomer = personBuilder.withCustomerId(TOO_LARGE_CUSTOMER_ID).build();

        ModelStub modelStub = new ModelStubAcceptingDeliveryAdded();
        Delivery validDelivery = new DeliveryBuilder().withCustomer(invalidCustomer).build();

        DeliveryAddCommand deliveryAddCommand = new DeliveryAddCommand(
                new DeliveryAddDescriptorBuilder(validDelivery).build());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX, () ->
                deliveryAddCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidDeliveryDate_throwsCommandException() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.withCustomerId(1).build();

        ModelStub modelStub = new ModelStubAcceptingDeliveryAdded();
        Delivery validDelivery =
                new DeliveryBuilder().withCustomer(validCustomer)
                        .withDeliveryDate(INVALID_DELIVERY_DATE).build();

        DeliveryAddCommand deliveryAddCommand = new DeliveryAddCommand(new
                DeliveryAddDescriptorBuilder(validDelivery).build());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_DELIVERY_DATE, () ->
                deliveryAddCommand.execute(modelStub));
    }

    @Test
    public void execute_deliveryAcceptedByModelLoggedOut_addFailure() {
        CustomerBuilder personBuilder = new CustomerBuilder();
        Customer validCustomer = personBuilder.build();

        ModelStubAcceptingDeliveryAddedLoggedOut modelStub = new ModelStubAcceptingDeliveryAddedLoggedOut();
        Delivery validDelivery = new DeliveryBuilder().withCustomer(validCustomer).build();

        DeliveryAddCommand deliveryAddCommand = new DeliveryAddCommand(new
                DeliveryAddDescriptorBuilder(validDelivery).build());
        assertThrows(CommandException.class, Messages.MESSAGE_USER_NOT_AUTHENTICATED, () ->
                deliveryAddCommand.execute(modelStub));

    }

    @Test
    public void equals() {
        Delivery gabrielMilk = new DeliveryBuilder().withName("Gabriel Milk").build();
        DeliveryAddDescriptor deliveryMilkAddDescriptor = new DeliveryAddDescriptorBuilder(gabrielMilk).build();
        DeliveryAddCommand addMilkCommand = new DeliveryAddCommand(deliveryMilkAddDescriptor);

        Delivery gambeRice = new DeliveryBuilder().withName("Gambe Rice").build();
        DeliveryAddDescriptor deliveryRiceAddDescriptor = new DeliveryAddDescriptorBuilder(gambeRice).build();
        DeliveryAddCommand addRiceCommand = new DeliveryAddCommand(deliveryRiceAddDescriptor);

        // same object -> returns true
        assertTrue(addMilkCommand.equals(addMilkCommand));

        // same values -> returns true
        DeliveryAddCommand addMilkCommandCopy = new DeliveryAddCommand(deliveryMilkAddDescriptor);
        assertTrue(addMilkCommandCopy.equals(addMilkCommandCopy));

        // different types -> returns false
        assertFalse(addMilkCommand.equals(1));

        // null -> returns false
        assertFalse(addMilkCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(addMilkCommand.equals(addRiceCommand));
    }

    @Test
    public void toStringTest() {
        DeliveryAddDescriptor deliveryAddDescriptor = new DeliveryAddDescriptorBuilder().build();
        DeliveryAddCommand deliveryAddCommand = new DeliveryAddCommand(deliveryAddDescriptor);
        String expected = new ToStringBuilder(deliveryAddCommand)
                .add("toAdd", deliveryAddDescriptor)
                .toString();
        assertEquals(expected, deliveryAddCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUiListDelivery() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUiListCustomer() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Delivery getDeliveryUsingFilteredList(int id) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ListItem> getUiList() {
            return null;
        }

        @Override
        public Path getAddressBookFilePath() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomer(Customer customer) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyBook<Customer> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Customer> getCustomer(int id) {
            return Optional.empty();
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCustomerWithSamePhone(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCustomer(Customer target) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCustomer(Customer target, Customer editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Customer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Resets the customer list to all customers.
         */
        @Override
        public void showAllFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Resets the customer list to show no customers.
         */
        @Override
        public void clearFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the number of customers in the filtered customer list.
         *
         * @return the number of customers in the filtered customer list.
         */
        @Override
        public int getFilteredCustomerListSize() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns true if the filtered customer list is empty.
         *
         * @return true if the filtered customer list is empty.
         */
        @Override
        public boolean isFilteredCustomerListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDeliveryBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBook<Delivery> getDeliveryBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Delivery> getDelivery(int id) {
            return Optional.empty();
        }

        @Override
        public Stream<Delivery> getDeliveryByCustomerId(int id) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public boolean hasDelivery(Delivery delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDelivery(Delivery target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeliveryByCustomer(Customer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDelivery(Delivery customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDelivery(Delivery target, Delivery editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Resets the delivery list to show all deliveries.
         */
        @Override
        public void showAllFilteredDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Resets the delivery list to show no deliveries.
         */
        @Override
        public void clearFilteredDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the number of deliveries in the filtered delivery list.
         *
         * @return the number of deliveries in the filtered delivery list.
         */
        @Override
        public int getFilteredDeliveryListSize() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns true if the filtered delivery list is empty.
         *
         * @return true if the filtered delivery list is empty.
         */
        @Override
        public boolean isFilteredDeliveryListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the number of deliveries in the sorted delivery list.
         *
         * @return the number of deliveries in the sorted delivery list.
         */
        @Override
        public boolean isSortedDeliveryListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getFilteredDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getSortedDeliveryList() {
            return null;
        }

        @Override
        public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedDeliveryList(Comparator<Delivery> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getUserLoginStatus() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoginSuccess() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogoutSuccess() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean userMatches(User user) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<User> getStoredUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void registerUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoggedInUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetPassword(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLoginStatus() {
            throw new AssertionError("This method should not be called.");
        }


    }

    /**
     * A Model stub that contains a single delivery.
     */
    private class ModelStubWithDelivery extends ModelStub {
        private final Delivery delivery;

        ModelStubWithDelivery(Delivery delivery) {
            requireNonNull(delivery);
            this.delivery = delivery;
        }

        @Override
        public boolean hasDelivery(Delivery delivery) {
            requireNonNull(delivery);
            return this.delivery.isSameDelivery(delivery);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingDeliveryAdded extends ModelStub {
        final ArrayList<Delivery> deliveriesAdded = new ArrayList<>();

        @Override
        public boolean hasDelivery(Delivery delivery) {
            requireNonNull(delivery);
            return deliveriesAdded.stream().anyMatch(delivery::isSameDelivery);
        }

        @Override
        public void addDelivery(Delivery delivery) {
            requireNonNull(delivery);
            deliveriesAdded.add(delivery);
        }

        @Override
        public ReadOnlyBook<Delivery> getDeliveryBook() {
            return new DeliveryBook();
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            CustomerBuilder personBuilder = new CustomerBuilder();
            Customer validCustomer = personBuilder.build();
            AddressBook addressBook = new AddressBook();
            addressBook.addCustomer(validCustomer);
            return addressBook;
        }

        @Override
        public Optional<Customer> getCustomer(int id) {
            return getAddressBook().getById(id);
        }

        @Override
        public boolean getUserLoginStatus() {
            return true;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingDeliveryAddedLoggedOut extends ModelStub {
        final ArrayList<Delivery> deliveriesAdded = new ArrayList<>();

        @Override
        public boolean hasDelivery(Delivery delivery) {
            requireNonNull(delivery);
            return deliveriesAdded.stream().anyMatch(delivery::isSameDelivery);
        }

        @Override
        public void addDelivery(Delivery delivery) {
            requireNonNull(delivery);
            deliveriesAdded.add(delivery);
        }

        @Override
        public ReadOnlyBook<Delivery> getDeliveryBook() {
            return new DeliveryBook();
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            CustomerBuilder personBuilder = new CustomerBuilder();
            Customer validCustomer = personBuilder.build();
            AddressBook addressBook = new AddressBook();
            addressBook.addCustomer(validCustomer);
            return addressBook;
        }


        @Override
        public boolean getUserLoginStatus() {
            return false;
        }


    }

    private class ModelStubStoringDeliveries extends ModelStub {
        final ArrayList<Delivery> deliveriesAdded = new ArrayList<>();


        @Override
        public void addDelivery(Delivery delivery) {
            requireNonNull(delivery);
            deliveriesAdded.add(delivery);
        }

        @Override
        public Optional<Delivery> getDelivery(int id) {
            return Optional.of(deliveriesAdded.get(id));
        }


        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            CustomerBuilder personBuilder = new CustomerBuilder();
            Customer validCustomer = personBuilder.build();
            AddressBook addressBook = new AddressBook();
            addressBook.addCustomer(validCustomer);
            return addressBook;
        }
        @Override
        public Optional<Customer> getCustomer(int id) {
            CustomerBuilder personBuilder = new CustomerBuilder();
            Customer validCustomer = personBuilder.build();
            return Optional.of(validCustomer);
        }

        @Override
        public boolean getUserLoginStatus() {
            return true;
        }


    }

}
