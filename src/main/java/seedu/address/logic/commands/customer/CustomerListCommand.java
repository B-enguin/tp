package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.logging.Logger;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all customers in the address book to the user.
 */
public class CustomerListCommand extends CustomerCommand {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " " + "list";
    public static final String MESSAGE_SUCCESS = "Listed Customers";
    public static final String MESSAGE_EMPTY = "There are currently no customers to be listed.";
    private static final Logger logger = Logger.getLogger(CustomerListCommand.class.getName());


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing CustomerListCommand");

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        if (model.getFilteredCustomerListSize() == 0) {
            return new CommandResult(MESSAGE_EMPTY, true);
        }

        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
