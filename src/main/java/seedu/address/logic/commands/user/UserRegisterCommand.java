package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Logs in the user and allows the user to access other functionalities.
 */
public class UserRegisterCommand extends Command {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = "register";
    /**
     * The message usage of the register command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Register an account for HomeBoss.\n\n"
            + "Parameters: "
            + PREFIX_USER + " USERNAME " + PREFIX_PASSWORD + " PASSWORD " + PREFIX_PASSWORD_CONFIRM
            + " CONFIRM_PASSWORD " + PREFIX_SECRET_QUESTION + " SECRET_QUESTION " + PREFIX_ANSWER
            + " ANSWER\n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USER + " yourUsername "
            + PREFIX_PASSWORD + " yourPassword "
            + PREFIX_PASSWORD_CONFIRM + " yourPassword "
            + PREFIX_SECRET_QUESTION + " yourSecretQuestion "
            + PREFIX_ANSWER + " yourAnswer";
    /**
     * The message displayed when the user has registered successfully.
     */
    public static final String MESSAGE_SUCCESS = "Registration successful. Welcome to HomeBoss!";
    /**
     * The message displayed when there's a mismatch between the password and password confirm fields.
     */
    public static final String MESSAGE_PASSWORD_MISMATCH = "Passwords do not match. Please try again.";
    /**
     * The message displayed when the user has an account already,
     * and is the pre-text to showing the stored user's username.
     */
    public static final String MESSAGE_ALREADY_HAVE_ACCOUNT = "You have an account already with username %s. ";
    /**
     * The message displayed when the user misses out the secret question field.
     */
    public static final String MESSAGE_EMPTY_SECRET_QUESTION = "Secret question cannot be empty.";
    /**
     * The message displayed when the user misses out the answer field.
     */
    public static final String MESSAGE_EMPTY_ANSWER = "Answer cannot be empty.";
    /**
     * The logger instance for UserRegisterCommand.
     */
    private static final Logger logger = Logger.getLogger(UserRegisterCommand.class.getName());
    /**
     * Stores the user to be registered.
     */
    private final User user;

    /**
     * Creates a UserRegisterCommand to log in the specified {@code User}
     *
     * @param user The user to be registered. This cannot be null.
     */
    public UserRegisterCommand(User user) {
        requireNonNull(user);
        this.user = user;
    }

    /**
     * Executes the register user command.
     *
     * @param model {@code Model} which the command should operate on. This cannot be null.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException if the user is already logged in or the user credentials are wrong.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing UserRegisterCommand.\n");

        Optional<User> storedUser = model.getStoredUser();

        // Throws exception is user already has an account
        // Only one user account is allowed
        if (storedUser.isPresent()) {
            logger.warning("User already has an account.\n");
            User currentStoredUser = storedUser.get();
            Username username = currentStoredUser.getUsername();
            String outputMessage = String.format(MESSAGE_ALREADY_HAVE_ACCOUNT, username);
            throw new CommandException(outputMessage);
        }

        logger.info("User to be registered: " + user.toString() + "\n");
        assert storedUser.isEmpty() : "User should not be present.";

        model.registerUser(this.user);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    /**
     * Returns true if both users have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserRegisterCommand)) {
            return false;
        }

        UserRegisterCommand otherUserRegisterCommand = (UserRegisterCommand) other;
        return user.equals(otherUserRegisterCommand.user);
    }
}
