package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;
import seedu.address.model.person.Customer;

/**
 * Represents a Command to update DeliveryStatus
 */
public class DeliveryStatusCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the status of the delivery identified "
            + "by the ID of the delivery. Existing status will be overwritten by the input status.\n"
            + "Parameters: ID (must be a integer representing a valid ID) "
            + "STATUS (must be one of CREATED/SHIPPED/COMPLETED/CANCELLED)\n"
            + "Example: " + COMMAND_WORD + " 1 COMPLETED";

    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery: %1$s";

    private final int targetId;
    private final DeliveryStatus updatedStatus;

    /**
     * Constructor for a DeliveryStatus Command.
     * @param targetId target delivery to update.
     * @param updatedStatus new status to update with.
     */
    public DeliveryStatusCommand(int targetId, DeliveryStatus updatedStatus) {
        requireNonNull(updatedStatus);

        this.targetId = targetId;
        this.updatedStatus = updatedStatus;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        // Find Delivery
        Optional<Delivery> targetDelivery = model.getDelivery(targetId);

        // Delivery ID must match a valid delivery
        if (targetDelivery.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        // Edit Delivery
        Delivery editedDelivery = createDeliveryWithNewStatus(targetDelivery.get(), updatedStatus);

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

        int updatedId = deliveryToEdit.getDeliveryId();
        DeliveryName updatedName = deliveryToEdit.getName();
        Customer updatedCustomer = deliveryToEdit.getCustomer();
        OrderDate updatedOrderDate = deliveryToEdit.getOrderDate();
        DeliveryStatus updatedStatus = newStatus;
        Note updatedNote = deliveryToEdit.getNote();

        // If set to completed
        // bring forward delivery date
        DeliveryDate updatedDeliveryDate;
        if (updatedStatus == DeliveryStatus.COMPLETED) {
            updatedDeliveryDate = new DeliveryDate(LocalDate.now());
        } else {
            updatedDeliveryDate = deliveryToEdit.getDeliveryDate();
        }


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
