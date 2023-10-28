package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;


public class DeliveryDateTest {

    @Test
    public void stringConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryDate((String) null));
    }

    @Test
    public void localDateConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryDate((LocalDate) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(invalidDate));
    }

    @Test
    public void constructor_validDateString_success() {
        String validDate = "2023-12-12";
        assertDoesNotThrow(() -> new DeliveryDate(validDate));
    }

    @Test
    public void constructor_validDateLocalDate_success() {
        assertDoesNotThrow(() -> new DeliveryDate(LocalDate.now()));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDate(null));

        // invalid date
        assertFalse(DeliveryDate.isValidDate("")); // empty string
        assertFalse(DeliveryDate.isValidDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDate("10-12-2023")); // wrong format
        assertFalse(DeliveryDate.isValidDate("2023=12+32")); // wrong format
        assertFalse(DeliveryDate.isValidDate("2023-12")); // missing day

        // valid date
        assertTrue(DeliveryDate.isValidDate("2023-12-12")); // correct date
        assertTrue(DeliveryDate.isValidDate("2021-12-12")); // correct date
    }

    @Test
    public void isNotPastDate() {
        // null date
        assertThrows(NullPointerException.class, () -> DeliveryDate.isFutureDate(null));

        // future date
        assertTrue(DeliveryDate.isFutureDate("2023-12-12"));

        // past date
        assertFalse(DeliveryDate.isFutureDate("2021-12-12"));
    }

    @Test
    public void isValidDeliveryDate() {
        // null date
        assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDeliveryDate(null));

        // invalid date
        assertFalse(DeliveryDate.isValidDeliveryDate("")); // empty string
        assertFalse(DeliveryDate.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDeliveryDate("10-12-2023")); // wrong format
        assertFalse(DeliveryDate.isValidDeliveryDate("2023=12+32")); // wrong format
        assertFalse(DeliveryDate.isValidDeliveryDate("2023-12")); // missing day
        assertThrows(DateTimeException.class, () -> DeliveryDate.isValidDeliveryDate("2023-13-12")); // wrong month
        assertThrows(DateTimeException.class, () -> DeliveryDate.isValidDeliveryDate("2023-12-32")); // wrong day
        assertFalse(DeliveryDate.isValidDeliveryDate("2023-08-12")); // correct date but past date

        // valid date
        assertTrue(DeliveryDate.isValidDeliveryDate("2023-12-12")); // correct date
    }
}
