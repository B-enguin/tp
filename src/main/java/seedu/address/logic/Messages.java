package seedu.address.logic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n\n%1$s";
    public static final String MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX = "The CUSTOMER_ID provided is invalid";
    public static final String MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX = "The DELIVERY_ID provided is invalid";
    public static final String MESSAGE_INVALID_DELIVERY_ID = "The DELIVERY_ID provided is invalid";
    public static final String MESSAGE_CUSTOMERS_MATCHED_LISTED = "%1$d customers listed!";
    public static final String MESSAGE_DELIVERY_LISTED_OVERVIEW = "%1$d deliveries listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple values specified for the following"
        + " single-valued field(s): ";
    public static final String MESSAGE_USER_NOT_AUTHENTICATED = "Access denied! You are currently not logged in.";
    public static final String MESSAGE_WELCOME = "Welcome to HomeBoss!\n\n" + "You are currently logged out.\n"
            + "Login or register to start using HomeBoss.";
    public static final String MESSAGE_INVALID_DELIVERY_DATE =
            "Expected Delivery Date cannot be before today.";

    public static final String MESSAGE_INVALID_ORDER_DATE =
            "Order Date cannot be after today.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Customer customer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("[%d]", customer.getCustomerId()))
                .append(String.format(" %s", customer.getName()))
                .append(String.format("\n\nPhone: %s", customer.getPhone().toString()))
                .append(String.format("\n\nEmail: %s", customer.getEmail().toString()))
                .append(String.format("\n\nAddress: %s", customer.getAddress().toString()));
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the delivery.
     */
    public static String format(Delivery delivery) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("[%d]", delivery.getDeliveryId()))
                .append(String.format(" %s", delivery.getName()))
                .append(String.format("\n\nStatus: %s", delivery.getStatus().toString()))
                .append(String.format("\n\nCustomer: %s", delivery.getCustomer().getName()))
                .append(String.format("\n\nCustomer ID: %d", delivery.getCustomer().getCustomerId()))
                .append(String.format("\n\nAddress: %s", delivery.getAddress().toString()))
                .append(String.format("\n\nOrdered On: %s", delivery.getOrderDate().toString()))
                .append(String.format("\n\nExpected Delivery Date: %s", delivery.getDeliveryDate().toString()))
                .append(Optional.ofNullable(delivery.getNote())
                        .map(n -> String.format("\n\nNote: %s", n)).orElse(""));

        return builder.toString();
    }
}
