package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Sort;
import seedu.address.logic.commands.user.UserRegisterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "ID must be an integer more than 0 and less than 2147483648.";


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Id} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parseId(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedIndex);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String deliveryName} into an {@code DeliveryName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DeliveryName} is invalid.
     */
    public static DeliveryName parseDeliveryName(String deliveryName) throws ParseException {
        requireNonNull(deliveryName);
        String trimmedDeliveryName = deliveryName.trim();
        if (!DeliveryName.isValidName(deliveryName)) {
            throw new ParseException(DeliveryName.MESSAGE_CONSTRAINTS);
        }
        return new DeliveryName(trimmedDeliveryName);
    }

    /**
     * Parses a {@code String date} into an {@code DeliveryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DeliveryDate} is invalid.
     */
    public static DeliveryDate parseDeliveryDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DeliveryDate.isValidDate(date)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        try {
            return new DeliveryDate(trimmedDate);
        } catch (DateTimeException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String date} into an {@code DeliveryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DeliveryDate} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(date)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }

        try {
            return new Date(trimmedDate);
        } catch (DateTimeException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValid(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses a {@code String status} into an {@code DeliveryStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static DeliveryStatus parseDeliveryStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim().toUpperCase();

        if (!DeliveryStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(DeliveryStatus.MESSAGE_CONSTRAINTS);
        }

        return DeliveryStatus.valueOf(trimmedStatus);
    }

    /**
     * Parses a {@code String sort} into a {@code Sort}.
     *
     * @param sort the sort to be parsed.
     * @return the parsed sort.
     * @throws ParseException if the given {@code sort} is invalid.
     */
    public static Sort parseSort(String sort) throws ParseException {
        requireNonNull(sort);
        String trimmedSort = sort.trim().toUpperCase();

        if (!Sort.isValidSort(trimmedSort)) {
            throw new ParseException(Sort.MESSAGE_CONSTRAINTS);
        }

        Sort sortEnum = Sort.valueOf(trimmedSort);
        if (!sortEnum.equals(Sort.ASC) && !sortEnum.equals(Sort.DESC)) {
            throw new ParseException(Sort.MESSAGE_CONSTRAINTS);
        }
        return sortEnum;
    }

    /**
     * Parses a {@code String tag} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code String tag} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_CONSTRAINTS);
        }

        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code String secretQuestion} into a {@code String}.
     *
     * @param secretQuestion The user input secret question.
     * @return String of trimmed secret question.
     */
    public static String parseSecretQuestion(String secretQuestion) throws ParseException {
        requireNonNull(secretQuestion);
        secretQuestion = secretQuestion.trim();
        // reject if empty string
        if (secretQuestion.isEmpty()) {
            throw new ParseException(UserRegisterCommand.MESSAGE_EMPTY_SECRET_QUESTION);
        }
        return secretQuestion;
    }

    /**
     * Parses a {@code String answer} into a {@code String}.
     *
     * @param answer The user input answer.
     * @return String of trimmed answer.
     */
    public static String parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        answer = answer.trim();
        // reject if empty string
        if (answer.isEmpty()) {
            throw new ParseException(UserRegisterCommand.MESSAGE_EMPTY_ANSWER);
        }
        return answer;
    }

}
