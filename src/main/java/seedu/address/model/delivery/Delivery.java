package seedu.address.model.delivery;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;

/**
 * Represents a Delivery in the address book.
 */
public class Delivery {
    // Delivery ID Generation
    private static int deliveryCount = 1;

    // Identity fields
    private int deliveryId;
    private DeliveryName name;
    private Customer customer;
    private OrderDate orderDate;
    private DeliveryDate deliveryDate;
    private DeliveryStatus status;
    private Note note;


    /**
     * Constructor for Delivery.
     * Every field must be present and not null.
     *
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     * @param note         The note of the delivery
     */
    public Delivery(DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status,
                    Note note) {
        this.deliveryId = Delivery.deliveryCount++;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
    }

    /**
     * Constructor for Delivery.
     * Every field must be present and not null.
     *
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     */
    public Delivery(DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status) {
        this.deliveryId = Delivery.deliveryCount++;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }


    /**
     * Constructor for Delivery.
     *
     * @param deliveryId   The ID of the delivery.
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     * @param note         The note of the delivery.
     */
    public Delivery(int deliveryId, DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status, Note note) {
        Delivery.deliveryCount = Math.max(deliveryCount, deliveryId + 1);
        this.deliveryId = deliveryId;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
    }

    public void setOrderDate(OrderDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(DeliveryDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public DeliveryName getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomerId() {
        return customer.getCustomerId();
    }

    public OrderDate getOrderDate() {
        return orderDate;
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Returns true if the delivery date matches the delivery date of the delivery.
     *
     * @param date The delivery date to check.
     * @return True if the delivery date matches the delivery date of the delivery.
     */
    public boolean isSameDeliveryDate(Date date) {
        return deliveryDate.equals(date);
    }

    /**
     * Returns true if the customer ID matches the customer ID of the delivery.
     *
     * @param customerId The customer ID to check.
     * @return True if the customer ID matches the customer ID of the delivery.
     */
    public boolean isSameCustomerIdToDeliver(int customerId) {
        return customer.isSameCustomerId(customerId);
    }

    /**
     * Returns true if the delivery status matches the delivery status of the delivery.
     *
     * @param status The delivery status to check.
     * @return True if the delivery status matches the delivery status of the delivery.
     */
    public boolean isSameDeliveryStatus(DeliveryStatus status) {
        return this.status.equals(status);
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public Address getAddress() {
        return customer.getAddress();
    }

    public Note getNote() {
        return note;
    }

    /**
     * Sets the delivery count to the specified value.
     *
     * @param deliveryCount The value to set the delivery count to.
     */
    public static void setDeliveryCount(int deliveryCount) {
        Delivery.deliveryCount = deliveryCount;
    }

    /**
     * Returns the current delivery count.
     *
     * @return The current delivery count.
     */
    public static int getDeliveryCount() {
        return deliveryCount;
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     *
     * @param otherDelivery The other delivery to compare with.
     * @return True if both deliveries have the same identity and data fields.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        return otherDelivery != null
            && otherDelivery.getDeliveryId() == getDeliveryId();
    }

    /**
     * Returns true if both deliveries have the same identity fields.
     *
     * @param other The other delivery to compare with.
     * @return True if both deliveries have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;

        return otherDelivery.deliveryId == deliveryId
            && otherDelivery.deliveryDate.equals(deliveryDate)
            && otherDelivery.name.equals(name)
            && otherDelivery.customer.equals(customer)
            && Objects.equals(otherDelivery.note, note)
            && otherDelivery.orderDate.equals(orderDate)
            && otherDelivery.status.equals(status)
            && otherDelivery.getAddress().equals(customer.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, name, customer, orderDate, deliveryDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("deliveryId", deliveryId)
            .add("name", name)
            .add("customer", customer)
            .add("orderedAt", orderDate)
            .add("deliveredAt", deliveryDate)
            .add("address:", customer.getAddress())
            .add("note:", Optional.ofNullable(note)
                .map(n -> String.format("\n Note:%s", n)).orElse(""))
            .toString();
    }
}
