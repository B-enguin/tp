package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.customer.CustomerAddCommand;
import seedu.address.logic.commands.customer.CustomerDeleteCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand;
import seedu.address.logic.commands.customer.CustomerFindCommand;
import seedu.address.logic.commands.customer.CustomerListCommand;
import seedu.address.logic.commands.customer.CustomerViewCommand;
import seedu.address.logic.commands.delivery.DeliveryAddCommand;
import seedu.address.logic.commands.delivery.DeliveryCreateNoteCommand;
import seedu.address.logic.commands.delivery.DeliveryDeleteCommand;
import seedu.address.logic.commands.delivery.DeliveryEditCommand;
import seedu.address.logic.commands.delivery.DeliveryFindCommand;
import seedu.address.logic.commands.delivery.DeliveryListCommand;
import seedu.address.logic.commands.delivery.DeliveryStatusCommand;
import seedu.address.logic.commands.delivery.DeliveryViewCommand;
import seedu.address.logic.commands.user.UserDeleteCommand;
import seedu.address.logic.commands.user.UserLoginCommand;
import seedu.address.logic.commands.user.UserLogoutCommand;
import seedu.address.logic.commands.user.UserRecoverAccountCommand;
import seedu.address.logic.commands.user.UserRegisterCommand;
import seedu.address.logic.commands.user.UserUpdateCommand;
import seedu.address.logic.parser.customer.CustomerAddCommandParser;
import seedu.address.logic.parser.customer.CustomerDeleteCommandParser;
import seedu.address.logic.parser.customer.CustomerEditCommandParser;
import seedu.address.logic.parser.customer.CustomerFindCommandParser;
import seedu.address.logic.parser.customer.CustomerViewCommandParser;
import seedu.address.logic.parser.delivery.DeliveryCreateNoteCommandParser;
import seedu.address.logic.parser.delivery.DeliveryEditCommandParser;
import seedu.address.logic.parser.delivery.DeliveryFindCommandParser;
import seedu.address.logic.parser.delivery.DeliveryListCommandParser;
import seedu.address.logic.parser.delivery.DeliveryStatusCommandParser;
import seedu.address.logic.parser.delivery.DeliveryViewCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.user.UserLoginCommandParser;
import seedu.address.logic.parser.user.UserRecoverAccountCommandParser;
import seedu.address.logic.parser.user.UserRegisterCommandParser;
import seedu.address.logic.parser.user.UserUpdateCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
        "(?<commandWord>customer \\S+|delivery \\S+|delete \\S+|recover \\S+|\\S+)(?<arguments>.*)"
    );
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        // ================ Customer Commands ====================================
        case CustomerAddCommand.COMMAND_WORD:
            return new CustomerAddCommandParser().parse(arguments);

        case CustomerEditCommand.COMMAND_WORD:
            return new CustomerEditCommandParser().parse(arguments);

        case CustomerDeleteCommand.COMMAND_WORD:
            return new CustomerDeleteCommandParser().parse(arguments);

        case CustomerViewCommand.COMMAND_WORD:
            return new CustomerViewCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case CustomerFindCommand.COMMAND_WORD:
            return new CustomerFindCommandParser().parse(arguments);

        case CustomerListCommand.COMMAND_WORD:
            return new CustomerListCommand();

        // ================ Delivery Commands ====================================
        case DeliveryCreateNoteCommand.COMMAND_WORD:
            return new DeliveryCreateNoteCommandParser().parse(arguments);

        case DeliveryStatusCommand.COMMAND_WORD:
            return new DeliveryStatusCommandParser().parse(arguments);

        case DeliveryViewCommand.COMMAND_WORD:
            return new DeliveryViewCommandParser().parse(arguments);

        case DeliveryListCommand.COMMAND_WORD:
            return new DeliveryListCommandParser().parse(arguments);

        case DeliveryAddCommand.COMMAND_WORD:
            return new DeliveryAddCommandParser().parse(arguments);

        case DeliveryDeleteCommand.COMMAND_WORD:
            return new DeliveryDeleteCommandParser().parse(arguments);

        case DeliveryEditCommand.COMMAND_WORD:
            return new DeliveryEditCommandParser().parse(arguments);

        case DeliveryFindCommand.COMMAND_WORD:
            return new DeliveryFindCommandParser().parse(arguments);

        // ================ System Commands ======================================
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        // ================ User Commands ========================================

        case UserLoginCommand.COMMAND_WORD:
            return new UserLoginCommandParser().parse(arguments);

        case UserLogoutCommand.COMMAND_WORD:
            return new UserLogoutCommand();

        case UserRegisterCommand.COMMAND_WORD:
            return new UserRegisterCommandParser().parse(arguments);

        case UserDeleteCommand.COMMAND_WORD:
            return new UserDeleteCommand();

        case UserRecoverAccountCommand.COMMAND_WORD:
            return new UserRecoverAccountCommandParser().parse(arguments);

        case UserUpdateCommand.COMMAND_WORD:
            return new UserUpdateCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
