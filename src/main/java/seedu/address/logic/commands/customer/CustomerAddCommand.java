package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Adds a person to the address book.
 */
public class CustomerAddCommand extends CustomerCommand {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " " + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the HomeBoss database."
            + "\n\nParameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_PHONE + " PHONE "
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_ADDRESS + " ADDRESS\n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe "
            + PREFIX_PHONE + " 98765432 "
            + PREFIX_EMAIL + " johnd@example.com "
            + PREFIX_ADDRESS + " 311, Clementi Ave 2, #02-25";

    public static final String MESSAGE_SUCCESS = "New customer added:\n\n%1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in HomeBoss";
    private static final Logger logger = Logger.getLogger(CustomerAddCommand.class.getName());

    private final Customer toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Customer}
     */
    public CustomerAddCommand(Customer customer) {
        requireNonNull(customer);
        toAdd = customer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing CustomerAddCommand: name "
                + toAdd.getName() + ", phone "
                + toAdd.getPhone() + ", email "
                + toAdd.getEmail() + ", address "
                + toAdd.getAddress());

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            // reset the customer count to the previous value
            Customer.resetPrevCustomerCount();
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        if (model.hasCustomer(toAdd)) {
            // reset the customer count to the previous value
            Customer.resetPrevCustomerCount();
            logger.warning("Duplicate customer found!");
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.addCustomer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerAddCommand)) {
            return false;
        }

        CustomerAddCommand otherCustomerAddCommand = (CustomerAddCommand) other;
        return toAdd.equals(otherCustomerAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
