package seedu.address.model.util;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.OrderDate;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Customer[] getSamplePersons() {
        return new Customer[]{
            new Customer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40")),
            new Customer(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Customer(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Customer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Customer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35")),
            new Customer(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31")),
        };
    }

    public static ReadOnlyBook<Customer> getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Customer sampleCustomer : getSamplePersons()) {
            sampleAb.addCustomer(sampleCustomer);
        }
        return sampleAb;
    }

    public static Delivery[] getSampleDeliveries(ReadOnlyBook<Customer> customerBook) {
        ObservableList<Customer> customers = customerBook.getList();
        return new Delivery[]{
            new Delivery(
                new DeliveryName("Chocolate Cake"),
                customers.get(0),
                new OrderDate("2020-12-12"),
                new DeliveryDate("2023-12-12"),
                DeliveryStatus.CREATED
            ),
            new Delivery(
                new DeliveryName("Strawberry Cake"),
                customers.get(1),
                new OrderDate("2020-12-12"),
                new DeliveryDate("2021-12-12"),
                DeliveryStatus.COMPLETED
            ),
            new Delivery(
                new DeliveryName("Matcha Cake"),
                customers.get(2),
                new OrderDate("2020-12-12"),
                new DeliveryDate("2023-12-12"),
                DeliveryStatus.CREATED
            ),
            new Delivery(
                new DeliveryName("Chocolate Cake"),
                customers.get(2),
                new OrderDate("2020-12-12"),
                new DeliveryDate("2023-12-12"),
                DeliveryStatus.SHIPPED
            ),
        };
    }

    public static ReadOnlyBook<Delivery> getSampleDeliveryBook(ReadOnlyBook<Customer> customerBook) {
        DeliveryBook sampleDb = new DeliveryBook();
        for (Delivery sampleDelivery : getSampleDeliveries(customerBook)) {
            sampleDb.addDelivery(sampleDelivery);
        }
        return sampleDb;
    }

}
