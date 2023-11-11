package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

/**
 * Deletes the user's account.
 * Available both when logged in and logged out.
 * If logged in, the user will be logged out after the account is deleted.
 */
public class UserDeleteCommand extends Command {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = "delete account";
    /**
     * The message displayed when the user has an account, and it is deleted successfully.
     */
    public static final String MESSAGE_SUCCESS = "User deleted successfully.";
    /**
     * The message displayed when the user has no account.
     */
    public static final String MESSAGE_NO_ACCOUNT = "No accounts found. Please register an account first.";

    /**
     * Executes the delete user command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException If there is no user to delete.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<User> storedUser = model.getStoredUser();

        // No user to delete
        if (storedUser.isEmpty()) {
            throw new CommandException(MESSAGE_NO_ACCOUNT);
        }

        model.deleteUser();
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

}
