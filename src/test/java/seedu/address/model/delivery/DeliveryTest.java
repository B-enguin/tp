package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.TypicalCustomers;


public class DeliveryTest {


    @Test
    public void deliveryId_success() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        Delivery delivery1 = new DeliveryBuilder().autoBuild();
        assertTrue(delivery.getDeliveryId() != delivery1.getDeliveryId());
    }

    @Test
    public void deliveryId_failure() {
        Delivery delivery = new DeliveryBuilder().autoBuild();

        Delivery delivery1 = new DeliveryBuilder().autoBuild();

        assertFalse(delivery.getDeliveryId() == delivery1.getDeliveryId());
    }

    @Test
    public void setDeliveryCount_success() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        Delivery delivery1 = new DeliveryBuilder().autoBuild();
        Delivery delivery2 = new DeliveryBuilder().autoBuild();

        Delivery.setDeliveryCount(1);
        assertEquals(1, Delivery.getDeliveryCount());
    }

    @Test
    public void setOrderDate() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        OrderDate orderDate = new OrderDate("2020-10-10");
        delivery.setOrderDate(orderDate);
        assertTrue(delivery.getOrderDate().equals(orderDate));
    }

    @Test
    public void setDeliveryDate() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        DeliveryDate deliveryDate = new DeliveryDate("2020-10-10");
        delivery.setDeliveryDate(deliveryDate);
        assertTrue(delivery.getDeliveryDate().equals(deliveryDate));
    }

    @Test
    public void setStatus() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        DeliveryStatus status = DeliveryStatus.SHIPPED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
        status = DeliveryStatus.COMPLETED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
        status = DeliveryStatus.CANCELLED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
    }

    @Test
    public void setNote() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        Note note = new Note("Hi");
        delivery.setNote(note);
        assertTrue(delivery.getNote().equals(note));
    }

    @Test
    public void correctAddress() {
        Delivery delivery = new DeliveryBuilder().withCustomer(TypicalCustomers.ALICE).build();

        assertTrue(delivery.getAddress().equals(TypicalCustomers.ALICE.getAddress()));
    }

    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(GABRIELS_MILK.isSameDelivery(GABRIELS_MILK));

        // null -> returns false
        assertFalse(GABRIELS_MILK.isSameDelivery(null));

        // same id, other attributes different -> returns true
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated").build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different status -> returns true
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.COMPLETED).build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different customer -> returns true
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalCustomers.BOB).build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // different id, all other attributes same -> returns false
        Delivery gambesRice = new DeliveryBuilder(GAMBES_RICE).build();
        assertFalse(GABRIELS_MILK.isSameDelivery(gambesRice));

        // different id, all other attributes different -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalCustomers.BOB).withOrderDate("2019-12-12")
            .withDeliveryDate("2024-12-12").autoBuild();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));
    }

    @Test
    public void getCustomerId_success() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        assertTrue(delivery.getCustomerId() == delivery.getCustomer().getCustomerId());
    }

    @Test
    public void equals() {
        Delivery gabrielsMilkCopy = new DeliveryBuilder(GABRIELS_MILK).build();

        // same object -> returns true
        assertTrue(GABRIELS_MILK.equals(GABRIELS_MILK));

        // null -> returns false
        assertFalse(GABRIELS_MILK.equals(null));

        // different type -> returns false
        assertFalse(GABRIELS_MILK.equals(5));

        // different delivery -> returns false
        assertFalse(GABRIELS_MILK.equals(GAMBES_RICE));

        // different name -> returns false
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different status -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.COMPLETED).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different customer -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalCustomers.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different id -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withId(2).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // same id, different attributes -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalCustomers.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different id, all other attributes different -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalCustomers.BOB).withOrderDate("2019-12-12")
            .withDeliveryDate("2024-12-12").autoBuild();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // different delivery address, all other attributes same -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalCustomers.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different expected delivery date, all other attributes same -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withDeliveryDate("2024-12-12").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different order date, all other attributes same -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withOrderDate("2019-12-12").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different delivery note, all other attributes same -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withNote(
            "Different note").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));
    }
}
