package seedu.address.logic.parser.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_CONFIRM_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_CONFIRM_DESC_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_FOODBEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.user.UserRecoverAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Password;

public class UserRecoverAccountCommandParserTest {

    private final UserRecoverAccountCommandParser parser = new UserRecoverAccountCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                        + PASSWORD_DESC_AARON
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_AARON,
                new UserRecoverAccountCommand(VALID_ANSWER_AARON, new Password(VALID_PASSWORD_AARON)));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_FOODBEAR
                        + PASSWORD_DESC_FOODBEAR
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_FOODBEAR,
                new UserRecoverAccountCommand(VALID_ANSWER_FOODBEAR, new Password(VALID_PASSWORD_FOODBEAR)));
    }

    @Test
    public void parse_allFieldAbsent_success() throws ParseException {
        // all prefixes are absent
        UserRecoverAccountCommand expectedCommand = parser.parse("");
        assertTrue(expectedCommand.isShowSecretQuestion());
    }

    @Test
    public void parse_someFieldsAbsent_failure() {
        // missing answer
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_AARON
                + PASSWORD_CONFIRM_DESC_AARON, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UserRecoverAccountCommand.MESSAGE_USAGE));

        // missing first password
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                + PASSWORD_CONFIRM_DESC_AARON, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UserRecoverAccountCommand.MESSAGE_USAGE));

        // missing confirm password
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                + PASSWORD_DESC_AARON, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UserRecoverAccountCommand.MESSAGE_USAGE));


    }

    @Test
    public void parse_passwordMismatch_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                        + PASSWORD_DESC_AARON
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_FOODBEAR,
                UserRecoverAccountCommand.MESSAGE_PASSWORD_MISMATCH);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // multiple answers
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                        + ANSWER_DESC_FOODBEAR
                        + PASSWORD_DESC_AARON
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_AARON,
                MESSAGE_DUPLICATE_FIELDS + "--answer");

        // multiple passwords
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                        + PASSWORD_DESC_AARON
                        + PASSWORD_DESC_FOODBEAR
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_AARON,
                MESSAGE_DUPLICATE_FIELDS + "--password");

        // multiple confirm passwords
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ANSWER_DESC_AARON
                        + PASSWORD_DESC_AARON
                        // second password is for confirmation
                        + PASSWORD_CONFIRM_DESC_AARON
                        + PASSWORD_CONFIRM_DESC_FOODBEAR,
                MESSAGE_DUPLICATE_FIELDS + "--confirmPass");
    }

}
