package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class CustomerTest {

    @Test
    public void isSameCustomer() {
        // same object -> returns true
        assertTrue(ALICE.isSameCustomer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCustomer(null));

        // same customerId and name, all other attributes different -> returns true
        Customer editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same customerId, different name, all other attributes same -> returns true
        editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same customerId, name differs in case, all other attributes same -> returns true
        Customer editedBob = new CustomerBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameCustomer(editedBob));

        // same customerId and phone, all other attributes different -> returns true
        editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same customerId, different phone, all other attributes same -> returns true
        editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // different customerId, same phone, different name, all other attributes same -> returns true
        editedAlice = new CustomerBuilder(ALICE).withCustomerId(100).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // name differs in case, different customerId, same phone, all other attributes same -> returns true
        editedBob = new CustomerBuilder(BOB).withCustomerId(101).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameCustomer(editedBob));

        // same name, different customerId, all other attributes different -> returns false
        editedAlice = new CustomerBuilder(ALICE).withCustomerId(103).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.isSameCustomer(editedAlice));

        // name has trailing spaces, same customerId, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new CustomerBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameCustomer(editedBob));

        // name has trailing spaces, different customerId, all other attributes same -> returns true
        editedBob = new CustomerBuilder(BOB).withCustomerId(104).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameCustomer(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Customer aliceCopy = new CustomerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different customer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Customer editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CustomerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Customer.class.getCanonicalName() + "{customerId=" + ALICE.getCustomerId() + ", name="
                + ALICE.getName() + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address="
                + ALICE.getAddress() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
