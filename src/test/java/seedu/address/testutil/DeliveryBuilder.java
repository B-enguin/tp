package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;

/**
 * A utility class to help with building Delivery objects.
 */
public class DeliveryBuilder {
    public static final int DEFAULT_ID = 1;
    public static final DeliveryName DEFAULT_NAME = new DeliveryName("Gabriels");
    public static final Customer DEFAULT_CUSTOMER = new CustomerBuilder().withName("Gabriel Seethor")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").build();

    public static final DeliveryDate DEFAULT_DELIVERED_AT = new DeliveryDate("2023-12-12");
    public static final DeliveryStatus DEFAULT_STATUS = DeliveryStatus.CREATED;

    public static final Note DEFAULT_NOTE = null;


    private int deliveryId;
    private DeliveryName name;
    private Customer customer;
    private OrderDate orderedAt;
    private DeliveryDate deliveredAt;
    private DeliveryStatus status;
    private Note note;
    private Address address;


    /**
     * Creates a {@code DeliveryBuilder} with the default details.
     */
    public DeliveryBuilder() {
        LocalDate defaultNow = LocalDate.now();
        this.deliveryId = DEFAULT_ID;
        this.name = DEFAULT_NAME;
        this.customer = DEFAULT_CUSTOMER;
        this.orderedAt = new OrderDate(defaultNow.toString());
        this.deliveredAt = DEFAULT_DELIVERED_AT;
        this.status = DEFAULT_STATUS;
        this.address = DEFAULT_CUSTOMER.getAddress();
        this.note = DEFAULT_NOTE;
    }

    /**
     * Initializes the DeliveryBuilder with the data of {@code deliveryToCopy}.
     *
     * @param delivery The delivery to copy.
     */
    public DeliveryBuilder(Delivery delivery) {
        this.deliveryId = delivery.getDeliveryId();
        this.name = delivery.getName();
        this.customer = delivery.getCustomer();
        this.orderedAt = delivery.getOrderDate();
        this.deliveredAt = delivery.getDeliveryDate();
        this.status = delivery.getStatus();
        this.address = DEFAULT_CUSTOMER.getAddress();
        this.note = delivery.getNote();
    }

    /**
     * Sets the {@code id} of the {@code Delivery} that we are building.
     *
     * @param id The id to set.
     * @return The DeliveryBuilder with the id set.
     */
    public DeliveryBuilder withId(int id) {
        this.deliveryId = id;
        return this;
    }

    /**
     * Sets the {@code name} of the {@code Delivery} that we are building.
     *
     * @param name The name to set.
     * @return The DeliveryBuilder with the name set.
     */
    public DeliveryBuilder withName(String name) {
        this.name = new DeliveryName(name);
        return this;
    }

    /**
     * Sets the {@code customer} of the {@code Delivery} that we are building.
     *
     * @param customer The customer to set.
     * @return The DeliveryBuilder with the customer set.
     */
    public DeliveryBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    /**
     * Sets the {@code orderedAt} of the {@code Delivery} that we are building.
     *
     * @param orderDate The orderedAt to set.
     * @return The DeliveryBuilder with the orderedAt set.
     */
    public DeliveryBuilder withOrderDate(String orderDate) {
        this.orderedAt = new OrderDate(orderDate);
        return this;
    }

    /**
     * Sets the {@code deliveredAt} of the {@code Delivery} that we are building.
     *
     * @param deliveryDate The deliveredAt to set.
     * @return The DeliveryBuilder with the deliveredAt set.
     */
    public DeliveryBuilder withDeliveryDate(String deliveryDate) {
        this.deliveredAt = new DeliveryDate(deliveryDate);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Delivery} that we are building.
     *
     * @param status The status to set.
     * @return The DeliveryBuilder with the status set.
     */
    public DeliveryBuilder withStatus(DeliveryStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code note} of the {@code Delivery} that we are building.
     *
     * @param note The status to set.
     * @return The DeliveryBuilder with the status set.
     */
    public DeliveryBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Builds the {@code Delivery} with the given parameters.
     *
     * @return The Delivery with the given parameters.
     */
    public Delivery build() {
        return new Delivery(deliveryId, name, customer, orderedAt, deliveredAt, status, note);
    }

    /**
     * Builds the {@code Delivery} with the given parameters with id automatically generated.
     *
     * @return The Delivery with the given parameters.
     */
    public Delivery autoBuild() {
        return new Delivery(name, customer, orderedAt, deliveredAt, status, note);
    }
}
