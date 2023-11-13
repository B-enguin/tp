package seedu.address.logic.parser.user;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.stream.Stream;

import seedu.address.logic.commands.user.UserRegisterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Parses input arguments and creates a new UserRegisterCommand object
 */
public class UserRegisterCommandParser implements Parser<UserRegisterCommand> {

    /**
     * Stores the argumentMultimap for the UserRegisterCommandParser
     */
    private ArgumentMultimap argMultimap;

    /**
     * Parses the given {@code String} of arguments in the context of the UserRegisterCommand
     * and returns an UserRegisterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UserRegisterCommand parse(String args) throws ParseException {

        argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                        PREFIX_SECRET_QUESTION, PREFIX_ANSWER);

        if (!arePrefixesPresent(argMultimap, PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                PREFIX_SECRET_QUESTION, PREFIX_ANSWER)
                || !argMultimap.isEmptyPreamble()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserRegisterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                PREFIX_SECRET_QUESTION, PREFIX_ANSWER);

        Username username = parseUsername();
        Password password = parsePassword();
        Password confirmPassword = parseConfirmPassword();
        String secretQuestion = parseSecretQuestion();
        String answer = parseAnswer();

        if (!password.equals(confirmPassword)) {
            throw new ParseException(UserRegisterCommand.MESSAGE_PASSWORD_MISMATCH);
        }

        User user = new User(username, password, true, secretQuestion, answer);

        return new UserRegisterCommand(user);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private Username parseUsername() throws ParseException {
        return ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USER).get());
    }

    private Password parsePassword() throws ParseException {
        return ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
    }

    private Password parseConfirmPassword() throws ParseException {
        return ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD_CONFIRM).get());
    }

    private String parseSecretQuestion() throws ParseException {
        return ParserUtil.parseSecretQuestion(argMultimap.getValue(PREFIX_SECRET_QUESTION).get());
    }

    private String parseAnswer() throws ParseException {
        return ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
    }

}
