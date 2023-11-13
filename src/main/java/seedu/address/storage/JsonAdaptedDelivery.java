package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;

/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String deliveryId;
    private final String name;
    private final String customerId;
    private final String orderDate;
    private final String deliveryDate;
    private final String status;
    private final String note;


    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("deliveryId") String deliveryId,
                               @JsonProperty("name") String name,
                               @JsonProperty("customerId") String customerId,
                               @JsonProperty("orderDate") String orderDate,
                               @JsonProperty("deliveryDate") String deliveryDate,
                               @JsonProperty("status") String status,
                               @JsonProperty("note") String note) {

        this.deliveryId = deliveryId;
        this.name = name;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        requireNonNull(source);

        deliveryId = String.valueOf(source.getDeliveryId());
        name = source.getName().deliveryName;
        customerId = String.valueOf(source.getCustomer().getCustomerId());
        orderDate = source.getOrderDate().toString();
        deliveryDate = source.getDeliveryDate().toString();
        status = source.getStatus().name();
        note = Optional.ofNullable(source.getNote()).map(n -> n.note).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted delivery object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted delivery.
     */
    public Delivery toModelType(Optional<ReadOnlyBook<Customer>> customerBook) throws IllegalValueException {
        requireNonNull(customerBook);

        // Name
        if (name == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryName.class.getSimpleName()));
        }
        if (!DeliveryName.isValidName(name)) {
            throw new IllegalValueException(DeliveryName.MESSAGE_CONSTRAINTS);
        }
        final DeliveryName modelName = new DeliveryName(name);

        // Customer
        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Customer ID"));
        }
        if (customerBook.isEmpty()) {
            throw new IllegalValueException("customerBook cannot be empty");
        }
        final int customerIdInt;
        try {
            customerIdInt = Integer.parseInt(customerId);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Customer ID should only contain numbers");
        }
        Optional<Customer> c = customerBook.get().getById(customerIdInt);
        if (c.isEmpty()) {
            throw new IllegalValueException("Customer ID does not exist");
        }
        final Customer modelCustomer = c.get();

        // Order Date
        if (orderDate == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderDate.class.getSimpleName()));
        }
        if (!OrderDate.isValidDate(orderDate)) {
            throw new IllegalValueException(OrderDate.MESSAGE_CONSTRAINTS);
        }
        if (!OrderDate.isPastDate(orderDate)) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_ORDER_DATE);
        }
        final OrderDate modelOrderDate = new OrderDate(orderDate);

        // Expected Delivery Date
        if (deliveryDate == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDate.class.getSimpleName()));
        }
        if (!DeliveryDate.isValidDate(deliveryDate)) {
            throw new IllegalValueException(DeliveryDate.MESSAGE_CONSTRAINTS);
        }
        final DeliveryDate modelDeliveryDate = new DeliveryDate(deliveryDate);

        // Delivery Status
        if (status == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryStatus.class.getSimpleName()));
        }
        if (!DeliveryStatus.isValidStatus(status)) {
            throw new IllegalValueException(DeliveryStatus.MESSAGE_CONSTRAINTS);
        }
        final DeliveryStatus modelDeliveryStatus = DeliveryStatus.valueOf(status);

        // Delivery Note
        final Note modelNote;
        if (note != null) {
            if (!Note.isValid(note)) {
                throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
            }
            modelNote = new Note(note);
        } else {
            modelNote = null;
        }

        // For deliveries without deliveryId
        if (deliveryId == null) {
            return new Delivery(
                modelName,
                modelCustomer,
                modelOrderDate,
                modelDeliveryDate,
                modelDeliveryStatus,
                modelNote);
        }

        final int modelDeliveryId;

        try {
            modelDeliveryId = Integer.parseInt(deliveryId);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Delivery ID should only contain numbers");
        }

        if (modelDeliveryId <= 0) {
            throw new IllegalValueException("Delivery ID must be an integer more than 0.");
        }

        return new Delivery(
            modelDeliveryId,
            modelName,
            modelCustomer,
            modelOrderDate,
            modelDeliveryDate,
            modelDeliveryStatus,
            modelNote);
    }

}
