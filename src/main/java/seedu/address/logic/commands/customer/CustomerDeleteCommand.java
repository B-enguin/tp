package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class CustomerDeleteCommand extends CustomerCommand {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " " + "delete";

    /**
     * The message usage of the delete customer command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the customer identified by the customer ID used in the displayed customer list.\n\n"
        + "Parameters: CUSTOMER_ID (must be a positive integer and less than 2147483648)\n\n"
        + "Example: " + COMMAND_WORD + " 1";

    /**
     * The pre-text to the message displayed when the customer is deleted successfully.
     */
    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer:\n\n%1$s";

    /**
     * The logger instance for CustomerDeleteCommand.
     */
    private static final Logger logger = Logger.getLogger(CustomerDeleteCommand.class.getName());

    /**
     * The identification (ID) of the customer to be deleted.
     */
    private final Index customerID;

    /**
     * Creates a CustomerDeleteCommand to delete the specified {@code Customer}.
     *
     * @param customerID The identification (ID) of the customer to be deleted.
     */
    public CustomerDeleteCommand(Index customerID) {
        this.customerID = customerID;
    }

    /**
     * Executes the delete customer command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException If the customer to be deleted does not exist or the user is not logged in.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing CustomerDeleteCommand:"
            + " customerID: " + customerID.getOneBased() + "\n");

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in.\n");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        int referenceCustomerID = customerID.getOneBased();
        Optional<Customer> customerToDelete = model.getCustomer(referenceCustomerID);

        if (customerToDelete.isEmpty()) {
            logger.warning("Customer to be deleted does not exist.\n");
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        logger.info("Customer to be deleted: " + customerToDelete.get().toString() + "\n");
        assert customerToDelete.isPresent() : "Customer to be deleted should be present.";

        model.deleteCustomer(customerToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS,
            Messages.format(customerToDelete.get())), true);
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a CustomerDeleteCommand with the same Customer ID, and false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerDeleteCommand)) {
            return false;
        }

        CustomerDeleteCommand otherDeleteCommand = (CustomerDeleteCommand) other;
        return customerID.equals(otherDeleteCommand.customerID);
    }

    /**
     * Generates a String representing this CustomerDeleteCommand and its target index.
     *
     * @return A String representation of this CustomerDeleteCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("customerID", customerID)
            .toString();
    }
}
