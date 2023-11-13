package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.NameContainsKeywordsPredicate;

/**
 * Finds and lists all customers in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CustomerFindCommand extends Command {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private static final Logger logger = Logger.getLogger(CustomerFindCommand.class.getName());

    private final NameContainsKeywordsPredicate predicate;

    public CustomerFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing CustomerFindCommand: keyword "
                + predicate.getKeywordsAsString());

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        model.updateFilteredCustomerList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_MATCHED_LISTED,
                        model.getFilteredCustomerListSize()), true);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerFindCommand)) {
            return false;
        }

        CustomerFindCommand otherCustomerFindCommand = (CustomerFindCommand) other;
        return predicate.equals(otherCustomerFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
