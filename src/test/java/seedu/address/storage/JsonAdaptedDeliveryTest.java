package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDelivery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;

public class JsonAdaptedDeliveryTest {
    private static final String INVALID_DELIVERY_NAME = "G@briel Milk";
    private static final String INVALID_DELIVERY_DATE = "2020-12-12";
    private static final String INVALID_DELIVERY_DATE_FORMAT = "12-12-2023";
    private static final String INVALID_DELIVERY_STATUS = "INVALID";
    private static final String INVALID_ORDER_DATE = "2023-12-12";
    private static final String INVALID_ORDER_DATE_FORMAT = "12-12-2023";
    private static final String INVALID_DELIVERY_NOTE = "";
    private static final String NON_EXISTENT_CUSTOMER = "-1";
    private static final String INVALID_CUSTOMER_FORMAT = "A";
    private static final String INVALID_DELIVERY_ID = "-1";
    private static final String INVALID_DELIVERY_ID_FORMAT = "A";

    private static final String VALID_DELIVERY_NAME = GABRIELS_MILK.getName().toString();
    private static final String VALID_DELIVERY_DATE = GABRIELS_MILK.getDeliveryDate().toString();
    private static final String VALID_DELIVERY_STATUS = GABRIELS_MILK.getStatus().toString();
    private static final String VALID_ORDER_DATE = GABRIELS_MILK.getOrderDate().toString();
    private static final String VALID_DELIVERY_NOTE = GABRIELS_MILK.getNote().toString();
    private static final String VALID_CUSTOMER = String.valueOf(GABRIELS_MILK.getCustomer().getCustomerId());

    @Test
    public void toModelType_emptyCustomerBook_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(GABRIELS_MILK);
        String expectedMessage = "customerBook cannot be empty";
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.empty()));
    }

    @Test
    public void toModelType_validDeliveryDetails_returnsDelivery() throws Exception {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(GABRIELS_MILK);
        assertEquals(GABRIELS_MILK, delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        INVALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = DeliveryName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        null,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidCustomerFormat_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        INVALID_CUSTOMER_FORMAT,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = "Customer ID should only contain numbers";
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nonExistentCustomer_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        NON_EXISTENT_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = "Customer ID does not exist";
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullCustomer_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_CUSTOMER,
                        null,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Customer ID");
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidOrderDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        INVALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DATE;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidOrderDateFormat_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        INVALID_ORDER_DATE_FORMAT,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = OrderDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullOrderDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        null,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidDeliveryDateFormat_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        INVALID_DELIVERY_DATE_FORMAT,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = DeliveryDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullDeliveryDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        null,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        INVALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = DeliveryStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullDeliveryStatus_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        null,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        INVALID_DELIVERY_NOTE);
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_nullNote_doesNotThrowIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        null,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryStatus.class.getSimpleName());
        assertDoesNotThrow(() -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidDeliveryId_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        INVALID_DELIVERY_ID,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = "Delivery ID must be an integer more than 0.";
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }

    @Test
    public void toModelType_invalidDeliveryIdFormat_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(
                        INVALID_DELIVERY_ID_FORMAT,
                        VALID_DELIVERY_NAME,
                        VALID_CUSTOMER,
                        VALID_ORDER_DATE,
                        VALID_DELIVERY_DATE,
                        VALID_DELIVERY_STATUS,
                        VALID_DELIVERY_NOTE);
        String expectedMessage = "Delivery ID should only contain numbers";
        assertThrows(IllegalValueException.class, expectedMessage, ()
                -> delivery.toModelType(Optional.of(getTypicalAddressBook())));
    }
}
