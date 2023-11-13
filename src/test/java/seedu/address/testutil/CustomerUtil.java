package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.customer.CustomerAddCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.model.customer.Customer;

/**
 * A utility class for Person.
 */
public class CustomerUtil {

    /**
     * Returns an add command string for adding the {@code customer}.
     */
    public static String getAddCommand(Customer customer) {
        return CustomerAddCommand.COMMAND_WORD + " " + getCustomerDetails(customer);
    }

    /**
     * Returns the part of command string for the given {@code customer}'s details.
     */
    public static String getCustomerDetails(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + customer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + customer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + customer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + customer.getAddress().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code CustomerEditDescriptor}'s details.
     */

    public static String getEditCustomerDescriptorDetails(CustomerEditDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }
}
