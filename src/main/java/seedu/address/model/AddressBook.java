package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.UniqueCustomerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class AddressBook implements ReadOnlyBook<Customer> {

    private final UniqueCustomerList customers;

    public AddressBook() {
        customers = new UniqueCustomerList();
    }

    /**
     * Creates an AddressBook using the Data in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyBook<Customer> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBook<Customer> newData) {
        requireNonNull(newData);

        setCustomers(newData.getList());
    }

    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }

    /**
     * Returns true if a customer with the same {@code Phone} as {@code customer} exists in the address book.
     */
    public boolean hasCustomerWithSamePhone(Customer customer) {
        requireNonNull(customer);
        return customers.containsPhone(customer);
    }

    /**
     * Adds a customer to the address book.
     * The customer must not already exist in the address book.
     */
    public void addCustomer(Customer p) {
        customers.add(p);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the
     * address book.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        customers.setCustomer(target, editedCustomer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCustomer(Customer key) {
        customers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("customers", customers)
                .toString();
    }

    @Override
    public ObservableList<Customer> getList() {
        return customers.asUnmodifiableObservableList();
    }

    /**
     * Returns the item with the specified id if it exists
     *
     * @param id the id of the item
     * @return the item specified by the id
     */
    @Override
    public Optional<Customer> getById(int id) {
        return customers.getById(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return customers.equals(otherAddressBook.customers);
    }

    @Override
    public int hashCode() {
        return customers.hashCode();
    }
}
