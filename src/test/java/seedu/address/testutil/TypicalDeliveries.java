package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeliveryBook;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryStatus;

/**
 * A utility class containing a list of {@code Delivery} objects to be used in tests.
 */
public class TypicalDeliveries {
    public static final Delivery GABRIELS_MILK = new DeliveryBuilder().withId(1).withName("Gabriel Milk")
            .withCustomer(TypicalCustomers.ALICE).withNote("Note").withOrderDate("2021-12-12").build();

    public static final Delivery GAMBES_RICE = new DeliveryBuilder().withId(2).withName("Gambe Rice")
            .withCustomer(TypicalCustomers.BENSON).withOrderDate("2021-12-12").build();

    public static final Delivery JY_CAKE = new DeliveryBuilder().withId(3).withName("Jian Yang Cake")
            .withCustomer(TypicalCustomers.BENSON).withNote("Note").build();

    public static final Delivery BRYANS_RICE = new DeliveryBuilder().withId(4).withName("Bryan Rice")
            .withCustomer(TypicalCustomers.BENSON).withOrderDate("2021-12-12").withStatus(DeliveryStatus.CANCELLED)
            .build();

    public static final Delivery REYNONS_BANANA = new DeliveryBuilder().withId(5).withName("Reynons Banana")
            .withCustomer(TypicalCustomers.BENSON).withOrderDate("2021-12-12").withStatus(DeliveryStatus.SHIPPED)
            .build();

    public static final Delivery JULIUS_PEACHES = new DeliveryBuilder().withId(6).withName("Julius Peaches")
            .withCustomer(TypicalCustomers.BENSON).withOrderDate("2021-12-12").withStatus(DeliveryStatus.COMPLETED)
            .build();

    private TypicalDeliveries() {
    } // prevents instantiation

    /**
     * Returns an {@code DeliveryBook} with all the typical deliveries.
     */
    public static DeliveryBook getTypicalDeliveryBook() {
        DeliveryBook db = new DeliveryBook();
        for (Delivery delivery : getTypicalDeliveries()) {
            db.addDelivery(delivery);
        }
        return db;
    }

    public static List<Delivery> getTypicalDeliveries() {
        return new ArrayList<>(Arrays.asList(GABRIELS_MILK, GAMBES_RICE, BRYANS_RICE, REYNONS_BANANA, JULIUS_PEACHES));
    }
}
