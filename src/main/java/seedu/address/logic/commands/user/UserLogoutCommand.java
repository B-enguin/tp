package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logs out the user.
 */
public class UserLogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "Bye!";
    public static final String MESSAGE_ALREADY_LOGGED_OUT = "You are already logged out!";
    private static final Logger logger = Logger.getLogger(UserLogoutCommand.class.getName());


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UserLogoutCommand");

        // Logged out user cannot log out again
        if (!model.getUserLoginStatus()) {
            logger.warning("User is already logged out!");
            throw new CommandException(MESSAGE_ALREADY_LOGGED_OUT);
        }

        // Set status in model to logged out, and update filtered lists
        model.setLogoutSuccess();
        model.clearFilteredDeliveryList();
        model.clearFilteredCustomerList();

        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
