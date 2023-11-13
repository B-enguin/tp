package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DeliveryBook;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;


/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableDeliveryBook {

    public static final String MESSAGE_DUPLICATE_DELIVERY = "Persons list contains duplicate deliveries(s).";

    private final List<JsonAdaptedDelivery> deliveries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDeliveriesBook} with the given deliveries.
     */
    @JsonCreator
    public JsonSerializableDeliveryBook(@JsonProperty("deliveries") List<JsonAdaptedDelivery> persons) {
        assert persons != null;

        this.deliveries.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableDeliveryBook(ReadOnlyBook<Delivery> source) {
        assert source != null;

        deliveries.addAll(source.getList().stream().map(JsonAdaptedDelivery::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DeliveryBook toModelType(Optional<ReadOnlyBook<Customer>> customerBook) throws IllegalValueException {
        assert customerBook.isPresent();

        DeliveryBook deliveryBook = new DeliveryBook();
        for (JsonAdaptedDelivery jsonAdaptedDelivery : deliveries) {
            Delivery delivery = jsonAdaptedDelivery.toModelType(customerBook);
            if (deliveryBook.hasDelivery(delivery)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DELIVERY);
            }
            deliveryBook.addDelivery(delivery);
        }
        return deliveryBook;
    }

}
