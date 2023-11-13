package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
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
 * Represents a Command to create a note for a delivery
 */
public class DeliveryCreateNoteCommand extends Command {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the delivery identified "
        + "by the DELIVERY_ID of the delivery. Existing note if any will be replaced with the input note.\n\n"
        + "Parameters: DELIVERY_ID (must be a integer representing a valid ID) "
        + PREFIX_NOTE + " Note\n\n"
        + "Example: " + COMMAND_WORD + " 1 --note This is a note";

    public static final String MESSAGE_NOTE_SUCCESS = "Added Note to Delivery:\n\n%1$s";

    private static final Logger logger = Logger.getLogger(DeliveryCreateNoteCommand.class.getName());

    private final int targetId;
    private final Note newNote;

    /**
     * Creates a DeliveryCreateNoteCommand to create a note of {@code newNote} for the delivery specified by
     * {@code targetId}.
     *
     * @param targetId the delivery to create a note for.
     * @param newNote  the note to be added.
     */
    public DeliveryCreateNoteCommand(int targetId, Note newNote) {
        requireNonNull(newNote);

        assert targetId > 0;

        this.targetId = targetId;
        this.newNote = newNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeliveryCreateNoteCommand:"
            + " deliveryId " + targetId
            + " and note " + newNote);

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
        Delivery editedDelivery = createDeliveryWithNewNote(targetDelivery.get(), newNote);

        logger.info("Updating Delivery:"
            + " deliveryId " + targetId
            + ", oldNote " + targetDelivery.get().getNote()
            + " and newNote " + newNote);

        // Update Delivery
        model.setDelivery(targetDelivery.get(), editedDelivery);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(String.format(MESSAGE_NOTE_SUCCESS, Messages.format(editedDelivery)), true);
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code deliveryToEdit}
     * edited with {@code newStatus}.
     */
    private static Delivery createDeliveryWithNewNote(Delivery deliveryToEdit, Note newNote) {
        assert deliveryToEdit != null;
        assert newNote != null;

        int updatedId = deliveryToEdit.getDeliveryId();
        DeliveryName updatedName = deliveryToEdit.getName();
        Customer updatedCustomer = deliveryToEdit.getCustomer();
        OrderDate updatedOrderDate = deliveryToEdit.getOrderDate();
        DeliveryDate updatedDeliveryDate = deliveryToEdit.getDeliveryDate();
        DeliveryStatus updatedStatus = deliveryToEdit.getStatus();
        Note updatedNote = newNote;

        return new Delivery(updatedId, updatedName, updatedCustomer, updatedOrderDate,
            updatedDeliveryDate, updatedStatus, updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryCreateNoteCommand)) {
            return false;
        }

        DeliveryCreateNoteCommand otherStatusCommand = (DeliveryCreateNoteCommand) other;
        return targetId == otherStatusCommand.targetId
            && newNote.equals(otherStatusCommand.newNote);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetId", targetId)
            .add("note", newNote)
            .toString();
    }
}
