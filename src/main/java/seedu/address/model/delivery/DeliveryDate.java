package seedu.address.model.delivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Delivery's delivery date in the delivery list.
 */
public class DeliveryDate extends Date {

    /**
     * Constructs a {@code DeliveryDate}.
     *
     * @param date A valid date.
     */
    public DeliveryDate(String date) {
        super(date);
    }

    /**
     * Constructs a {@code DeliveryDate}.
     *
     * @param date A valid {@code LocalDate}.
     */
    public DeliveryDate(LocalDate date) {
        super(date);
    }

    /**
     * Returns true if a given string is a valid delivery date.
     *
     * @param date A string representing a date.
     * @return True if the date is valid.
     */
    public static boolean isFutureDate(String date) {
        LocalDate deliveryDate = LocalDate.parse(date);
        return !deliveryDate.isBefore(LocalDate.now());
    }

    /**
     * Returns true if a given string is a valid delivery date.
     *
     * @param test A string representing a date.
     * @return True if the date is valid.
     */
    public static boolean isValidDeliveryDate(String test) {
        return Date.isValidDate(test) && isFutureDate(test);
    }

    /**
     * Returns a string in the application's format
     *
     * @return A string representing the date
     */
    @Override
    public String toString() {
        return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
