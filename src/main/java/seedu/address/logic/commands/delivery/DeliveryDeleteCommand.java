package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

/**
 * Deletes a delivery identified using it's displayed index from the address book.
 */
public class DeliveryDeleteCommand extends DeliveryCommand {


    /**
     * The command word.
     */
    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the delivery identified by the delivery ID used in the displayed delivery list.\n\n"
        + "Parameters: DELIVERY_ID (must be a positive integer and less than 2147483648)\n\n"
        + "Example: " + COMMAND_WORD + " 1";

    /**
     * The text to the message displayed when the Delivery is delete successfuly.
     */
    public static final String MESSAGE_DELETE_DELIVERY_SUCCESS = "Deleted Delivery:\n\n%1$s";

    /**
     * The logger instance for DeliveryDeleteCommand.
     */
    private static final Logger logger = Logger.getLogger(DeliveryDeleteCommand.class.getName());

    private final Index targetIndex;

    /**
     * Creates a DeliveryDeleteCommand to delete the specified {@code Delivery}
     *
     * @param targetIndex
     */
    public DeliveryDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the DeliveryDeleteCommand.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The command result along with the message to be displayed to the user.
     * @throws CommandException If the delivery to be deleted does not exist or the user is not logged in.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing DeliveryDeleteCommand: "
                        + "Delivery ID: " + targetIndex.getOneBased() + "\n");

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in.");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        model.showAllFilteredDeliveryList();

        Optional<Delivery> targetDelivery = model.getDelivery(targetIndex.getOneBased());

        if (targetDelivery.isEmpty()) {
            logger.warning("Delivery to be deleted does not exist.\n");
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        Delivery deliveryToDelete = targetDelivery.get();

        logger.info("Delivery to be deleted:" + deliveryToDelete + "\n");
        assert targetDelivery.isPresent() : "Delivery to be deleted should exist.";

        model.deleteDelivery(deliveryToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.format(deliveryToDelete)), true);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryDeleteCommand)) {
            return false;
        }

        DeliveryDeleteCommand otherDeleteCommand = (DeliveryDeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
