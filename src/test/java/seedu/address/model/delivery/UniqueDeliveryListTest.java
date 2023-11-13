package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAMES_MILK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.UniqueCustomerList;
import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;
import seedu.address.model.delivery.exceptions.DuplicateDeliveryException;
import seedu.address.testutil.DeliveryBuilder;

public class UniqueDeliveryListTest {

    private final UniqueDeliveryList uniqueDeliveryList = new UniqueDeliveryList();

    @Test
    public void contains_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.contains(null));
    }

    @Test
    public void contains_deliveryNotInList_returnsFalse() {
        assertFalse(uniqueDeliveryList.contains(GABRIELS_MILK));
    }

    @Test
    public void contains_deliveryInList_returnsTrue() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        assertTrue(uniqueDeliveryList.contains(GABRIELS_MILK));
    }

    @Test
    public void contains_deliveryWithSameIdDifferentFields_returnsTrue() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        Delivery delivery = new DeliveryBuilder(GAMBES_RICE).withId(1).build();
        assertTrue(uniqueDeliveryList.contains(delivery));
    }

    @Test
    public void add_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.add(null));
    }

    @Test
    public void add_duplicateDelivery_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        assertThrows(DuplicateDeliveryException.class, () -> uniqueDeliveryList.add(GABRIELS_MILK));
    }

    @Test
    public void add_deliveryWithSameIdDifferentFields_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        Delivery delivery = new DeliveryBuilder().withId(1).build();
        assertThrows(DuplicateDeliveryException.class, () -> uniqueDeliveryList.add(delivery));
    }

    @Test
    public void setDelivery_nullTargetDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, GABRIELS_MILK));
    }

    @Test
    public void setDelivery_nullEditedDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(GABRIELS_MILK, null));
    }

    @Test
    public void setDelivery_targetDeliveryNotInList_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.setDelivery(GABRIELS_MILK,
            GABRIELS_MILK));
    }

    @Test
    public void setDelivery_editedDeliveryIsSameDelivery_success() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        uniqueDeliveryList.setDelivery(GABRIELS_MILK, GABRIELS_MILK);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(GABRIELS_MILK);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasSameIdentity_success() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        Delivery editedDelivery = new DeliveryBuilder(GABRIELS_MILK).withName(VALID_NAME_JAMES_MILK).build();
        uniqueDeliveryList.setDelivery(GABRIELS_MILK, editedDelivery);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(editedDelivery);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasDifferentIdentity_success() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        uniqueDeliveryList.setDelivery(GABRIELS_MILK, GAMBES_RICE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(GAMBES_RICE);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasNonUniqueIdentity_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        uniqueDeliveryList.add(GAMBES_RICE);
        assertThrows(DuplicateDeliveryException.class, ()
            -> uniqueDeliveryList.setDelivery(GABRIELS_MILK, GAMBES_RICE));
    }

    @Test
    public void remove_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.remove(null));
    }

    @Test
    public void remove_deliveryDoesNotExists_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.remove(GABRIELS_MILK));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        uniqueDeliveryList.remove(GABRIELS_MILK);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullUniqueDeliveryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((UniqueDeliveryList) null));
    }

    @Test
    public void setDeliveries_uniqueDeliveryList_replacesOwnListWithProvidedUniqueDeliveryList() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(GAMBES_RICE);
        uniqueDeliveryList.setDeliveries(expectedUniqueDeliveryList);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((List<Delivery>) null));
    }

    @Test
    public void setDeliveries_list_replacesOwnListWithProvidedList() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        List<Delivery> deliveryList = Collections.singletonList(GAMBES_RICE);
        uniqueDeliveryList.setDeliveries(deliveryList);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(GAMBES_RICE);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_listWithDuplicateDeliveries_throwsDuplicatePersonException() {
        List<Delivery> deliveryListWithDuplicateDeliveries = Arrays.asList(GABRIELS_MILK, GABRIELS_MILK);
        assertThrows(DuplicateDeliveryException.class, ()
            -> uniqueDeliveryList.setDeliveries(deliveryListWithDuplicateDeliveries));
    }

    @Test
    public void getById_validId_givesPresentDeliveryOptional() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        Optional<Delivery> d = uniqueDeliveryList.getById(GABRIELS_MILK.getDeliveryId());
        assertTrue(d.isPresent());
        assertEquals(d.get(), GABRIELS_MILK);
    }

    @Test
    public void getById_negativeId_givesEmptyDeliveryOptional() {
        Optional<Delivery> d = uniqueDeliveryList.getById(-1);
        assertTrue(d.isEmpty());
    }

    @Test
    public void getById_invalidId_givesEmptyDeliveryOptional() {
        uniqueDeliveryList.add(GABRIELS_MILK);
        Optional<Delivery> d = uniqueDeliveryList.getById(GABRIELS_MILK.getDeliveryId() + 1);
        assertTrue(d.isEmpty());
    }

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDeliveryList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueDeliveryList.asUnmodifiableObservableList().toString(), uniqueDeliveryList.toString());
    }

    @Test
    public void equals() {
        UniqueDeliveryList firstList = new UniqueDeliveryList();
        UniqueDeliveryList secondList = new UniqueDeliveryList();
        UniqueCustomerList customerList = new UniqueCustomerList();

        // same list -> true
        assertTrue(firstList.equals(firstList));

        // different list both empty -> true
        assertTrue(firstList.equals(secondList));

        // different list -> false
        assertFalse(firstList.equals(customerList));

        secondList.add(GAMBES_RICE);

        // different items -> false
        assertFalse(firstList.equals(secondList));

        firstList.add(GAMBES_RICE);

        // same items -> true
        assertTrue(firstList.equals(secondList));
    }
}
