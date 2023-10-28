package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void stringConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((String) null));
    }

    @Test
    public void localDateConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((LocalDate) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void constructor_validDateString_success() {
        String validDate = "2023-12-12";
        assertDoesNotThrow(() -> new Date(validDate));
    }

    @Test
    public void constructor_validDateLocalDate_success() {
        assertDoesNotThrow(() -> new Date(LocalDate.now()));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("10-12-2023")); // wrong format
        assertFalse(Date.isValidDate("2023=12+32")); // wrong format
        assertFalse(Date.isValidDate("2023-12")); // missing day

        // valid date
        assertTrue(Date.isValidDate("2023-12-12")); // correct date
        assertTrue(Date.isValidDate("2021-12-12")); // correct date
    }

    @Test
    public void equals() {
        Date date = new Date("2023-12-12");
        Date dateCopy = new Date("2023-12-12");
        Date dateDiff = new Date("2021-12-12");
        Date dateDiff2 = new Date("2023-12-13");

        // same object -> returns true
        assertTrue(date.equals(date));

        // same values -> returns true
        assertTrue(date.equals(dateCopy));

        // different types -> returns false
        assertFalse(date.equals(1));

        // null -> returns false
        assertFalse(date.equals(null));

        // different date -> returns false
        assertFalse(date.equals(dateDiff));

        // different date -> returns false
        assertFalse(date.equals(dateDiff2));
    }
}
