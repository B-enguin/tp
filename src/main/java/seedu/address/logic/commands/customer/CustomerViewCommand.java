package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a CustomerViewCommand which displays a single customer.
 */
public class CustomerViewCommand extends CustomerCommand {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a single customer.\n\n"
            + "Parameters: "
            + "CUSTOMER_ID\n\n"
            + "Example: " + COMMAND_WORD + " "
            + "1";

    public static final String MESSAGE_SUCCESS = "Customer displayed: %1$s";

    private static final Logger logger = Logger.getLogger(CustomerViewCommand.class.getName());

    private final int customerId;

    /**
     * Creates a CustomerViewCommand to display the specified {@code Customer}.
     */
    public CustomerViewCommand(int customerId) {
        assert customerId > 0;

        this.customerId = customerId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing CustomerViewCommand: customerId "
            + customerId);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        Optional<Customer> customer = model.getCustomer(customerId);

        if (customer.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        return new CommandResult(Messages.format(customer.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerViewCommand // instanceof handles nulls
                && customerId == ((CustomerViewCommand) other).customerId); // state check
    }
}
