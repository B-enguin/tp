package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = "help";

    /**
     * The message usage of the help command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * The introductory message displayed when the help command is executed.
     */
    public static final String STARTING_HELP = "Ensure you have registered an account / have logged"
            + " in before using the following basic commands, except for the ones indicated with a `.\n\n";

    /**
     * The message displayed showing the user commands.
     */
    public static final String USER_HELP = "USER\n"
            + "These are commands for managing your user account in HomeBoss.\n"
            + "* `register - Registers a new user account to use HomeBoss.\n"
            + "* `login - Logs in to your user account.\n"
            + "* update - Updates your user account details.\n"
            + "* `recover account - Recovers your user account.\n"
            + "* `logout - Logs out of your user account.\n"
            + "* `delete account - Deletes your user account.\n\n";

    /**
     * The message displayed showing the customer commands.
     */
    public static final String CUSTOMER_HELP = "CUSTOMER\n"
            + "These are commands for managing your customers in HomeBoss.\n"
            + "* customer add - Adds a customer to the address book.\n"
            + "* customer find - Finds customers whose names contain any of the given keywords.\n"
            + "* customer list - Lists all customers in the address book.\n"
            + "* customer view - Shows the details of the specified customer.\n"
            + "* customer edit - Updates the details of an existing customer in the address book.\n"
            + "* customer delete - Deletes the specified customer from the address book.\n\n";

    /**
     * The message displayed showing the delivery commands.
     */
    public static final String DELIVERY_HELP = "DELIVERY\n"
            + "These are commands for managing your deliveries in HomeBoss.\n"
            + "* delivery add - Adds a delivery to the delivery book.\n"
            + "* delivery find - Finds deliveries whose names contain any of the given keywords.\n"
            + "* delivery list - Lists all deliveries in the delivery book.\n"
            + "* delivery view - Shows the details of the specified delivery.\n"
            + "* delivery edit - Updates the details of an existing delivery in the delivery book.\n"
            + "* delivery status - Changes the status of a specified delivery.\n"
            + "* delivery note - Creates a note for a specified delivery.\n"
            + "* delivery delete - Deletes the specified delivery from the delivery book.\n\n";

    /**
     * The message displayed showing the miscellaneous commands.
     */
    public static final String MISC_HELP = "MISCELLANEOUS\n"
            + "These are general commands in HomeBoss.\n"
            + "* `exit - Exits the program.\n"
            + "* `help - Shows a list of commands and their usage.\n"
            + "* clear - Clears both customer and delivery database. *Warning:* This action is irreversible.\n\n";

    /**
     * The ending message displayed when the help command is executed.
     */
    public static final String ENDING_HELP = "Refer to the URL in the pop-up window for"
            + " our full user guide.\n\n";

    /**
     * The full help message.
     */
    public static final String HELP_MESSAGE = STARTING_HELP + USER_HELP + CUSTOMER_HELP
            + DELIVERY_HELP + MISC_HELP + ENDING_HELP;
    /**
     * The logger instance for HelpCommand.
     */
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());

    /**
     * Executes the help command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     */
    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing HelpCommand.\n");
        return new CommandResult(HELP_MESSAGE, true, false, false);
    }
}
