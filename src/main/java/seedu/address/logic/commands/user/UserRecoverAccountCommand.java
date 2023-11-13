package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;

/**
 * Recover the user account if they have forgotten their password.
 */
public class UserRecoverAccountCommand extends Command {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = "recover account";
    /**
     * The message usage of the recover account command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Recover your account for HomeBoss.\n\n"
            + "Parameters: ["
            + PREFIX_ANSWER + " ANSWER " + PREFIX_PASSWORD + " NEW_PASSWORD " + PREFIX_PASSWORD_CONFIRM
            + " NEW_PASSWORD]\n\n"
            + "Example 1: " + COMMAND_WORD + "\n\n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_ANSWER + " yourAnswer "
            + PREFIX_PASSWORD + " yourNewPassword "
            + PREFIX_PASSWORD_CONFIRM + " yourNewPassword";
    /**
     * The message displayed when the user has an account, and is the pre-text to showing the secret question.
     */
    public static final String MESSAGE_SUCCESS_WITHOUT_FLAGS = "Your secret question is: %s";
    /**
     * The message displayed when the user has an account, and it is recovered successfully using this command.
     */
    public static final String MESSAGE_SUCCESS_WITH_FLAGS = "Your account has been recovered successfully."
            + " Welcome back to HomeBoss.";
    /**
     * The message displayed when the user has an account but the answer is wrong.
     */
    public static final String MESSAGE_WRONG_ANSWER = "Wrong answer to secret question. Either try again"
            + " or delete account (permanent loss of stored data).";
    /**
     * The message displayed when the password and password confirm fields do not match.
     */
    public static final String MESSAGE_PASSWORD_MISMATCH = "Passwords do not match. Please try again.";

    /**
     * The logger instance for UserRecoverAccountCommand.
     */
    private static final Logger logger = Logger.getLogger(UserRecoverAccountCommand.class.getName());

    /**
     * Indicates whether the secret question should be shown.
     * This is true by default, unless the user has provided the answer to the secret question and the new password.
     */
    private boolean isShowSecretQuestion = true;
    /**
     * The answer to the secret question.
     */
    private String answer;
    /**
     * The new password to be set.
     */
    private Password newPassword;
    /**
     * The additional message to be displayed after the secret question is shown.
     */
    private String additionalMessage = "\n\n" + "Please answer the question using the following command:\n"
            + UserRecoverAccountCommand.MESSAGE_USAGE;

    /**
     * Creates a UserRecoverAccountCommand to recover the specified {@code User}
     */
    public UserRecoverAccountCommand(String answer, Password newPassword) {
        isShowSecretQuestion = false;
        this.answer = answer;
        this.newPassword = newPassword;
    }

    /**
     * Creates a UserRecoverAccountCommand to show the secret question of the specified {@code User}
     */
    public UserRecoverAccountCommand() {
        // empty constructor
    }

    /**
     * Executes the recover account command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException If the user is already logged in or the user credentials are wrong.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing UserRecoverAccountCommand.\n");

        Optional<User> storedUser = model.getStoredUser();

        // ensure that the user has an account to recover
        if (storedUser.isEmpty()) {
            logger.warning("No user to recover.\n");
            throw new CommandException(UserDeleteCommand.MESSAGE_NO_ACCOUNT);
        }

        User currentStoredUser = storedUser.get();

        // show secret question
        // considered as a success
        if (isShowSecretQuestion) {
            String secretQuestion = currentStoredUser.getSecretQuestion();
            logger.info("Showing secret question: " + secretQuestion + "\n");
            String output = String.format(MESSAGE_SUCCESS_WITHOUT_FLAGS, secretQuestion);
            output += additionalMessage;
            return new CommandResult(output);
        }

        // ensure that the user has provided the right answer to the secret question
        // this check has to be done after isShowSecretQuestion is checked
        // because if isShowSecretQuestion is true, then the user has not provided the answer yet
        if (!currentStoredUser.checkAnswerEquals(answer)) {
            logger.warning("Wrong answer to secret question.\n");
            throw new CommandException(MESSAGE_WRONG_ANSWER);
        }

        logger.info("Executing UserRecoverAccountCommand: "
                + "answer: " + answer + "\n"
                + "newPassword: " + newPassword + "\n"
                + "user: " + currentStoredUser.toString() + "\n");

        assert currentStoredUser.checkAnswerEquals(answer) : "Answer should be correct.";

        // answer is correct, proceed to reset password
        User newUser = new User(currentStoredUser.getUsername(), newPassword, true,
                currentStoredUser.getSecretQuestion(), currentStoredUser.getAnswer());
        model.resetPassword(newUser);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS_WITH_FLAGS, true);
    }

    /**
     * Indicates whether the secret question should be shown.
     *
     * @return true if the secret question should be shown, false otherwise.
     */
    public boolean isShowSecretQuestion() {
        return isShowSecretQuestion;
    }

    /**
     * Indicates if an instance of this command is equal to another object.
     *
     * @param other The other object to be compared.
     * @return true if the other object is an instance of this command and has the same fields.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserRecoverAccountCommand // instanceof handles nulls
                && isShowSecretQuestion == ((UserRecoverAccountCommand) other).isShowSecretQuestion
                && answer.equals(((UserRecoverAccountCommand) other).answer)
                && newPassword.equals(((UserRecoverAccountCommand) other).newPassword));
    }
}
