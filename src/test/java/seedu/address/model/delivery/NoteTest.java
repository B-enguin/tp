package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void getNote_returnsNote() {
        String validNote = "FedEx";
        Note note = new Note(validNote);
        assertEquals(validNote, note.getNote());
    }

    @Test
    public void isValid() {
        // null name
        assertThrows(NullPointerException.class, () -> Note.isValid(null));

        // invalid note -> returns false
        assertFalse(Note.isValid("")); // empty string

        // valid note -> returns false
        assertTrue(Note.isValid("peter jack")); // alphabets only

        // valid note -> returns false
        assertTrue(Note.isValid("az 09")); // alphanumeric

        // valid note -> returns false
        assertTrue(Note.isValid("AZ 123")); // alphanumeric with capital letters

        // invalid note -> returns false
        assertFalse(Note.isValid("@")); // includes special characters, boundary of ASCII A

        // invalid note -> returns false
        assertFalse(Note.isValid("[")); // includes special characters, boundary of ASCII Z

        // invalid note -> returns false
        assertFalse(Note.isValid("`")); // includes special characters, boundary of ASCII a

        // invalid note -> returns false
        assertFalse(Note.isValid("{")); // includes special characters, boundary of ASCII z

        // invalid note -> returns false
        assertFalse(Note.isValid("/")); // includes special characters, boundary of ASCII 0

        // invalid note -> returns false
        assertFalse(Note.isValid(":")); // includes special characters, boundary of ASCII 9

    }

    @Test
    public void equals() {
        Note note = new Note("Valid Name");

        // same values -> returns true
        assertTrue(note.equals(new Note("Valid Name")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Other Valid Name")));
    }
}
