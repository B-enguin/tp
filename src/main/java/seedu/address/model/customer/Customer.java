package seedu.address.model.customer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer {

    // Customer ID Generation
    private static int customerCount = 1;

    // Identity fields
    private final int customerId;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.customerId = customerCount++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Creates a customer with a given customerId.
     * To be used only when creating customer from storage file.
     *
     * @param customerId Customer id of the customer.
     * @param name       Name of the customer.
     * @param phone      Phone number of the customer.
     * @param email      Email of the customer.
     * @param address    Address of the customer.
     */
    public Customer(int customerId, Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public static void setCustomerCount(int count) {
        Customer.customerCount = count + 1;
    }

    /**
     * Returns current customerCount.
     * To be used by {@code CustomerBuilder} to create customer with expected customerId.
     *
     * @return customerCount
     */
    public static int getCustomerCount() {
        return Customer.customerCount;
    }

    /**
     * Decrements customerCount by 1.
     */
    public static void resetPrevCustomerCount() {
        Customer.customerCount -= 1;
    }

    /**
     * Returns true if both Customers have the same customerId or {@code Phone}.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
            && (customerId == otherCustomer.customerId
            || phone.equals(otherCustomer.getPhone()));
    }

    /**
     * Returns true if the customer has the same customerId.
     *
     * @param customerId Customer id to compare with.
     * @return True if the customer has the same customerId.
     */
    public boolean isSameCustomerId(int customerId) {
        return this.customerId == customerId;
    }

    /**
     * Returns true if both Customers have the same {@code Phone}.
     */
    public boolean hasSamePhone(Customer otherCustomer) {
        return otherCustomer != null
            && phone.equals(otherCustomer.getPhone());
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     * This defines a stronger notion of equality between two customers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return customerId == otherCustomer.customerId
            && name.equals(otherCustomer.name)
            && phone.equals(otherCustomer.phone)
            && email.equals(otherCustomer.email)
            && address.equals(otherCustomer.address);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(customerId, name, phone, email, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("customerId", customerId)
            .add("name", name)
            .add("phone", phone)
            .add("email", email)
            .add("address", address)
            .toString();
    }

}
