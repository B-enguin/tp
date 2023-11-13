package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Updates the user's account details.
 */
public class UserUpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update your account details.\n\n"
            + "Parameters: "
            + "[" + PREFIX_USER + " USERNAME] "
            + "[" + PREFIX_PASSWORD + " PASSWORD "
            + PREFIX_PASSWORD_CONFIRM + " CONFIRM_PASSWORD] "
            + "[" + PREFIX_SECRET_QUESTION + " SECRET_QUESTION "
            + PREFIX_ANSWER + " ANSWER]\n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + " yourNewPassword "
            + PREFIX_PASSWORD_CONFIRM + " yourNewPassword ";
    public static final String MESSAGE_SUCCESS = "Update successful.";
    public static final String MESSAGE_MISSING_FIELDS = "Please provide at least one field to update! \n%1$s";
    public static final String MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING = "Password and Confirm Password "
            + "have to be either all present or all absent. Try again.";
    public static final String MESSAGE_PASSWORD_MISMATCH = "Passwords do not match. Please try again.";
    public static final String MESSAGE_QUESTION_OR_ANSWER_MISSING = "Secret Question and Answer have to be "
            + "either all present or all absent. Try again.";
    private static final Logger logger = Logger.getLogger(UserUpdateCommand.class.getName());

    private final UserUpdateDescriptor userUpdateDescriptor;

    /**
     * Creates a UserUpdateCommand to update the details of the {@code User}
     */
    public UserUpdateCommand(UserUpdateDescriptor userUpdateDescriptor) {
        requireNonNull(userUpdateDescriptor);
        this.userUpdateDescriptor = userUpdateDescriptor;
    }

    /**
     * Executes the update user command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException if the user is not logged in.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UserUpdateCommand");

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        Optional<User> storedUser = model.getStoredUser();
        // there should always be a stored user after registering/to be able to log in
        assert storedUser.isPresent();
        User currentStoredUser = storedUser.get();

        User updatedUser = createUpdatedUser(currentStoredUser, userUpdateDescriptor);

        model.updateUser(updatedUser);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    /**
     * Creates and returns a {@code User} with the details of in {@code userUpdateDescriptor}.
     */
    private static User createUpdatedUser(User storedUser, UserUpdateDescriptor userUpdateDescriptor) {
        assert storedUser != null;

        Username updatedUsername = userUpdateDescriptor.getUsername().orElse(storedUser.getUsername());
        Password updatedPassword = userUpdateDescriptor.getPassword().orElse(storedUser.getPassword());
        String updatedSecretQuestion = userUpdateDescriptor.getSecretQuestion().orElse(storedUser.getSecretQuestion());
        String updatedAnswer = userUpdateDescriptor.getAnswer().orElse(storedUser.getAnswer());

        return new User(updatedUsername, updatedPassword, true, updatedSecretQuestion, updatedAnswer);
    }

    /**
     * Returns true if both {@code UserUpdateCommand} have the same {@code UserUpdateDescriptor}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserUpdateCommand)) {
            return false;
        }

        UserUpdateCommand otherUserUpdateCommand = (UserUpdateCommand) other;
        return userUpdateDescriptor.equals(otherUserUpdateCommand.userUpdateDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("updateUserDescriptor", userUpdateDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the user with. Each non-empty field value will replace the
     * corresponding field value of the user.
     */
    public static class UserUpdateDescriptor {
        private Username username;
        private Password password;
        private String secretQuestion;
        private String answer;

        public UserUpdateDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public UserUpdateDescriptor(UserUpdateDescriptor toCopy) {
            setUsername(toCopy.username);
            setPassword(toCopy.password);
            setSecretQuestion(toCopy.secretQuestion);
            setAnswer(toCopy.answer);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(username, password, secretQuestion, answer);
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        public void setSecretQuestion(String secretQuestion) {
            this.secretQuestion = secretQuestion;
        }

        public Optional<String> getSecretQuestion() {
            return Optional.ofNullable(secretQuestion);
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Optional<String> getAnswer() {
            return Optional.ofNullable(answer);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UserUpdateDescriptor)) {
                return false;
            }

            UserUpdateDescriptor otherUserUpdateDescriptor = (UserUpdateDescriptor) other;
            return Objects.equals(username, otherUserUpdateDescriptor.username)
                    && Objects.equals(password, otherUserUpdateDescriptor.password)
                    && Objects.equals(secretQuestion, otherUserUpdateDescriptor.secretQuestion)
                    && Objects.equals(answer, otherUserUpdateDescriptor.answer);
        }

        @Override
        public String toString() {
            // does not show password for security reasons
            return new ToStringBuilder(this)
                    .add("username", username)
                    .add("secretQuestion", secretQuestion)
                    .add("answer", answer)
                    .toString();
        }
    }
}
