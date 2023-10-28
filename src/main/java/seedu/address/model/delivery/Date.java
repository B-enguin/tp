package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Represents a Date in the address book.
 */
public class Date implements Comparable<Date> {
    public static final String MESSAGE_CONSTRAINTS =
        "Order dates should be in the format YYYY-MM-DD";

    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid {@code LocalDate}.
     */
    protected Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param test The string to test.
     * @return True if the string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date o = (Date) other;
        return date.getYear() == o.date.getYear()
            && date.getMonth() == o.date.getMonth()
            && date.getDayOfMonth() == o.date.getDayOfMonth();
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public int compareTo(Date o) {
        return Comparator.comparing(Date::getDate).compare(this, o);
    }
}
