package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;

/**
 * Represents a Command to update DeliveryStatus
 */
public class DeliveryStatusCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the status of the delivery identified "
        + "by the ID of the delivery. Existing status will be overwritten by the input status.\n\n"
        + "Parameters: ID (must be a integer representing a valid ID) "
        + "STATUS (must be one of CREATED/SHIPPED/COMPLETED/CANCELLED)\n\n"
        + "Example: " + COMMAND_WORD + " 1 COMPLETED";

    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery:\n\n%1$s";

    private static final Logger logger = Logger.getLogger(DeliveryStatusCommand.class.getName());

    private final int targetId;
    private final DeliveryStatus updatedStatus;

    /**
     * Constructor for a DeliveryStatus Command.
     *
     * @param targetId      target delivery to update.
     * @param updatedStatus new status to update with.
     */
    public DeliveryStatusCommand(int targetId, DeliveryStatus updatedStatus) {
        requireNonNull(updatedStatus);

        assert targetId > 0;

        this.targetId = targetId;
        this.updatedStatus = updatedStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeliveryStatusCommand:"
            + " deliveryId " + targetId
            + " and status " + updatedStatus);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in!");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        // Find Delivery
        Optional<Delivery> targetDelivery = model.getDelivery(targetId);

        if (targetDelivery.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        // Edit Delivery
        Delivery editedDelivery = createDeliveryWithNewStatus(targetDelivery.get(), updatedStatus);

        logger.info("Updating Delivery:"
            + " deliveryId " + targetId
            + ", oldStatus " + targetDelivery.get().getStatus()
            + " and newStatus " + updatedStatus);

        // Update Delivery
        model.setDelivery(targetDelivery.get(), editedDelivery);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(String.format(MESSAGE_EDIT_DELIVERY_SUCCESS, Messages.format(editedDelivery)), true);
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code deliveryToEdit}
     * edited with {@code newStatus}.
     */
    private static Delivery createDeliveryWithNewStatus(Delivery deliveryToEdit, DeliveryStatus newStatus) {
        assert deliveryToEdit != null;
        assert newStatus != null;

        int updatedId = deliveryToEdit.getDeliveryId();
        DeliveryName updatedName = deliveryToEdit.getName();
        Customer updatedCustomer = deliveryToEdit.getCustomer();
        OrderDate updatedOrderDate = deliveryToEdit.getOrderDate();
        DeliveryDate updatedDeliveryDate = deliveryToEdit.getDeliveryDate();
        DeliveryStatus updatedStatus = newStatus;
        Note updatedNote = deliveryToEdit.getNote();


        return new Delivery(updatedId, updatedName, updatedCustomer, updatedOrderDate,
            updatedDeliveryDate, updatedStatus, updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryStatusCommand)) {
            return false;
        }

        DeliveryStatusCommand otherStatusCommand = (DeliveryStatusCommand) other;
        return targetId == otherStatusCommand.targetId
            && updatedStatus.equals(otherStatusCommand.updatedStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetId", targetId)
            .add("status", updatedStatus)
            .toString();
    }
}
