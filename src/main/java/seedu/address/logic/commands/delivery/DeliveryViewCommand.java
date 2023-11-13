package seedu.address.logic.commands.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_ID;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

/**
 * Represents a DeliveryViewCommand which displays a single delivery order.
 */
public class DeliveryViewCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a single delivery order.\n\n"
        + "Parameters: "
        + "DELIVERY_ID (Must be a positive integer and less than 2147483648)\n\n"
        + "Example: " + COMMAND_WORD + " "
        + "1";

    public static final String MESSAGE_SUCCESS = "Delivery order displayed: %1$s";
    private static final Logger logger = Logger.getLogger(DeliveryViewCommand.class.getName());

    private final int deliveryId;

    /**
     * Creates a DeliveryViewCommand to display the specified {@code Delivery}.
     */
    public DeliveryViewCommand(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing DeliverViewCommand: deliveryId "
            + deliveryId);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("Executing DeliveryViewCommand failed: user not logged in");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        assert model.getUserLoginStatus() : "User should be logged in";

        Optional<Delivery> delivery = model.getDelivery(deliveryId);

        if (delivery.isEmpty()) {
            logger.warning("Executing DeliveryViewCommand failed: Delivery not found");
            throw new CommandException(MESSAGE_INVALID_DELIVERY_ID);
        }

        return new CommandResult(Messages.format(delivery.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeliveryViewCommand // instanceof handles nulls
            && deliveryId == ((DeliveryViewCommand) other).deliveryId); // state check
    }
}
