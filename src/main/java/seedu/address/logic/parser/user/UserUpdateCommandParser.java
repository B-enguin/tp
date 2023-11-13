package seedu.address.logic.parser.user;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.logging.Logger;

import seedu.address.logic.commands.user.UserUpdateCommand;
import seedu.address.logic.commands.user.UserUpdateCommand.UserUpdateDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Password;

/**
 * Parses input arguments and creates a new UserUpdateCommand object
 */
public class UserUpdateCommandParser implements Parser<UserUpdateCommand> {

    private static final Logger logger = Logger.getLogger(UserUpdateCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the UserUpdateCommand
     * and returns a UserUpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UserUpdateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                        PREFIX_SECRET_QUESTION, PREFIX_ANSWER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                PREFIX_SECRET_QUESTION, PREFIX_ANSWER);

        UserUpdateDescriptor userUpdateDescriptor = new UserUpdateDescriptor();

        if (!argMultimap.isEmptyPreamble()) {
            logger.severe("Could not parse command");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserUpdateCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_USER).isPresent()) {
            userUpdateDescriptor.setUsername(ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USER).get()));
        }

        userUpdateDescriptor = parsePasswords(argMultimap, userUpdateDescriptor);

        userUpdateDescriptor = parseSecretQuestionAndAnswer(argMultimap, userUpdateDescriptor);

        if (!userUpdateDescriptor.isAnyFieldEdited()) {
            logger.warning("No fields provided");
            throw new ParseException(
                    String.format(UserUpdateCommand.MESSAGE_MISSING_FIELDS, UserUpdateCommand.MESSAGE_USAGE));
        }

        return new UserUpdateCommand(userUpdateDescriptor);
    }

    /**
     * Parses the password and confirm password fields, and checks if are either both present or both absent.
     *
     * @param argMultimap          the argument multimap that stores the user input with the respective prefixes
     * @param userUpdateDescriptor the {@code UserUpdateDescriptor} to be updated
     * @return the updated {@code UserUpdateDescriptor}
     * @throws ParseException if the password and confirm password fields are not both present or both absent,
     *                        and if they are both present but does not match each other
     */
    public UserUpdateDescriptor parsePasswords(ArgumentMultimap argMultimap, UserUpdateDescriptor userUpdateDescriptor)
            throws ParseException {
        // Either one of password or confirm password is missing.
        if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()
                && argMultimap.getValue(PREFIX_PASSWORD_CONFIRM).isEmpty()) {
            throw new ParseException(UserUpdateCommand.MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);
        }
        if (argMultimap.getValue(PREFIX_PASSWORD_CONFIRM).isPresent()
                && argMultimap.getValue(PREFIX_PASSWORD).isEmpty()) {
            throw new ParseException(UserUpdateCommand.MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);
        }
        // Both password and confirm password are present.
        if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()
                && argMultimap.getValue(PREFIX_PASSWORD_CONFIRM).isPresent()) {
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            Password confirmPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD_CONFIRM).get());
            // Passwords mismatch
            if (!password.equals(confirmPassword)) {
                throw new ParseException(UserUpdateCommand.MESSAGE_PASSWORD_MISMATCH);
            }
            userUpdateDescriptor.setPassword(password);
        }
        return userUpdateDescriptor;
    }

    /**
     * Parses the secret question and answer fields, and checks if are either both present or both absent.
     *
     * @param argMultimap          the argument multimap that stores the user input with the respective prefixes
     * @param userUpdateDescriptor the {@code UserUpdateDescriptor} to be updated
     * @return the updated {@code UserUpdateDescriptor}
     * @throws ParseException if the secret question and answer fields are not both present or both absent.
     */
    public UserUpdateDescriptor parseSecretQuestionAndAnswer(ArgumentMultimap argMultimap,
                                                             UserUpdateDescriptor userUpdateDescriptor)
            throws ParseException {
        // Either one of secret question or answer is missing.
        if (argMultimap.getValue(PREFIX_SECRET_QUESTION).isPresent()
                && argMultimap.getValue(PREFIX_ANSWER).isEmpty()) {
            throw new ParseException(UserUpdateCommand.MESSAGE_QUESTION_OR_ANSWER_MISSING);
        }
        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()
                && argMultimap.getValue(PREFIX_SECRET_QUESTION).isEmpty()) {
            throw new ParseException(UserUpdateCommand.MESSAGE_QUESTION_OR_ANSWER_MISSING);
        }
        // Both secret question and answer are present.
        if (argMultimap.getValue(PREFIX_SECRET_QUESTION).isPresent()
                && argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            userUpdateDescriptor.setSecretQuestion(ParserUtil.parseSecretQuestion(
                    argMultimap.getValue(PREFIX_SECRET_QUESTION).get()));
            userUpdateDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }
        return userUpdateDescriptor;
    }
}
