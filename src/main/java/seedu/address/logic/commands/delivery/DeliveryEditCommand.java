package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DATE;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
 * Edits the details of an existing Delivery in the address book.
 */
public class DeliveryEditCommand extends DeliveryCommand {


    /**
     * The command word.
     */
    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "edit";

    /**
     * The text displayed to show what the command does and how to use it.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the delivery identified "
        + "by the DELIVERY_ID used in the displayed delivery list. "
        + "Existing values will be overwritten by the input values.\n\n"
        + "Parameters: DELIVERY_ID (must be a positive integer and less than 2147483648)\n\n"
        + "At least one field must be specified."
        + "[" + PREFIX_NAME + " DELIVERY_NAME] "
        + "[" + PREFIX_CUSTOMER_ID + " CUSTOMER_ID] "
        + "[" + PREFIX_DATE + " DELIVERY_DATE] "
        + "[" + PREFIX_STATUS + " STATUS] "
        + "[" + PREFIX_NOTE + " NOTE]...\n\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_NAME + " 10 Chocolate Cakes "
        + PREFIX_DATE + " 2025-12-12";

    /**
     * The text to the message displayed when the Delivery is edited successfuly.
     */
    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery:\n\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";

    private static final Logger logger = Logger.getLogger(DeliveryEditCommand.class.getName());

    private final Index targetIndex;
    private final DeliveryEditDescriptor deliveryEditDescriptor;

    /**
     * Creates a DeliveryEditCommand to edit the specified {@code Delivery}
     *
     * @param targetIndex            of the delivery in the delivery list to edit
     * @param deliveryEditDescriptor details to edit the delivery with
     */
    public DeliveryEditCommand(Index targetIndex, DeliveryEditDescriptor deliveryEditDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(deliveryEditDescriptor);
        this.targetIndex = targetIndex;
        this.deliveryEditDescriptor = new DeliveryEditDescriptor(deliveryEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // User cannot perform this operation before logging in
        logger.info("Executing DeliveryEditCommand: "
            + "Delivery ID: " + targetIndex.getOneBased() + "\n"
            + "Delivery Edit Descriptor:" + deliveryEditDescriptor.toString() + "\n");

        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in.");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        model.showAllFilteredDeliveryList();

        Optional<Delivery> targetDelivery = model.getDelivery(targetIndex.getOneBased());

        if (targetDelivery.isEmpty()) {
            logger.warning("Delivery to be edited does not exist.\n");
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        Delivery deliveryToEdit = targetDelivery.get();
        Delivery editedDelivery = createEditedDelivery(model, deliveryToEdit, deliveryEditDescriptor);

        assert deliveryToEdit != null : "Delivery to be edited should exist.";
        assert editedDelivery != null : "Edited Delivery should exist.";

        logger.info("Delivery to be edited: " + deliveryToEdit.toString() + "\n");
        logger.info("Edited Delivery: " + editedDelivery.toString() + "\n");

        model.setDelivery(deliveryToEdit, editedDelivery);
        model.showAllFilteredDeliveryList();

        return new CommandResult(String.format(MESSAGE_EDIT_DELIVERY_SUCCESS,
            Messages.format(editedDelivery)), true);

    }

    /**
     * Creates and returns a {@code Delivery} with the details of edited with {@code editDeliveryDescriptor}.
     *
     * @param model                  {@code Model} which the delivery is edited in.
     * @param deliveryToEdit         {@code Delivery} which the command edits.
     * @param deliveryEditDescriptor {@code editDeliveryDescriptor} details to edit the delivery with.
     */
    private static Delivery createEditedDelivery(Model model, Delivery deliveryToEdit, DeliveryEditDescriptor
            deliveryEditDescriptor) throws CommandException {

        assert deliveryToEdit != null;

        DeliveryName updatedDeliveryName =
                deliveryEditDescriptor.getDeliveryName().orElse(deliveryToEdit.getName());

        int customerId = deliveryEditDescriptor.getCustomerId().orElse(deliveryToEdit.getCustomerId());
        Customer updatedCustomer = null;

        OrderDate orderDate = deliveryToEdit.getOrderDate();

        DeliveryDate updatedDeliveryDate =
                deliveryEditDescriptor.getDeliveryDate().orElse(deliveryToEdit.getDeliveryDate());

        DeliveryStatus updatedDeliveryStatus =
                deliveryEditDescriptor.getStatus().orElse(deliveryToEdit.getStatus());

        Note updatedNote = deliveryEditDescriptor.getNote().orElse(deliveryToEdit.getNote());

        Optional<Customer> targetCustomer = model.getCustomer(customerId);

        if (targetCustomer.isPresent()) {
            updatedCustomer = targetCustomer.get();
        } else {
            logger.warning("Customer to be added to this Delivery does not exist.\n");
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }
        if (!DeliveryDate.isFutureDate(updatedDeliveryDate.toString())) {
            logger.warning("Delivery date is not valid since it is not a future date.\n");
            throw new CommandException(MESSAGE_INVALID_DELIVERY_DATE);
        }

        logger.info("Creating Delivery with:" + "\n"
            + "Delivery Name: " + updatedDeliveryName + "\n"
            + "Customer ID: " + updatedCustomer.getCustomerId() + "\n"
            + "Delivery Date: " + updatedDeliveryDate + "\n"
            + "Delivery Status: " + updatedDeliveryStatus + "\n"
            + "Note: " + updatedNote + "\n");

        return new Delivery(deliveryToEdit.getDeliveryId(), updatedDeliveryName, updatedCustomer, orderDate,
                updatedDeliveryDate,
                updatedDeliveryStatus,
                updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryEditCommand)) {
            return false;
        }

        DeliveryEditCommand otherEditCommand = (DeliveryEditCommand) other;
        return targetIndex.equals(otherEditCommand.targetIndex)
                && deliveryEditDescriptor.equals(otherEditCommand.deliveryEditDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", targetIndex)
                .add("deliveryEditDescriptor", deliveryEditDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class DeliveryEditDescriptor {
        private int deliveryId;
        private DeliveryName deliveryName;
        private Integer customerId = null;
        private OrderDate orderDate;
        private DeliveryDate deliveryDate;
        private DeliveryStatus status;
        private Note note;

        public DeliveryEditDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy is used internally.
         */
        public DeliveryEditDescriptor(DeliveryEditDescriptor toCopy) {
            setDeliveryId(toCopy.deliveryId);
            setDeliveryName(toCopy.deliveryName);
            setCustomerId(toCopy.customerId);
            setOrderDate(toCopy.orderDate);
            setDeliveryDate(toCopy.deliveryDate);
            setStatus(toCopy.status);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(deliveryName, customerId, deliveryDate, status, note);
        }

        public Optional<Integer> getDeliveryId() {
            return Optional.ofNullable(deliveryId);
        }

        public void setDeliveryId(int deliveryId) {
            this.deliveryId = deliveryId;
        }

        public void setDeliveryName(DeliveryName deliveryName) {
            this.deliveryName = deliveryName;
        }

        public Optional<DeliveryName> getDeliveryName() {
            return Optional.ofNullable(deliveryName);
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public Optional<Integer> getCustomerId() {
            return Optional.ofNullable(customerId);
        }

        public void setOrderDate(OrderDate orderDate) {
            this.orderDate = orderDate;
        }

        public Optional<OrderDate> getOrderDate() {
            return Optional.ofNullable(orderDate);
        }

        public void setDeliveryDate(DeliveryDate deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Optional<DeliveryDate> getDeliveryDate() {
            return Optional.ofNullable(deliveryDate);
        }

        public void setStatus(DeliveryStatus status) {
            this.status = status;
        }

        public Optional<DeliveryStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeliveryEditDescriptor)) {
                return false;
            }

            DeliveryEditDescriptor otherEditDeliveryDescriptor = (DeliveryEditDescriptor) other;
            return Objects.equals(deliveryName, otherEditDeliveryDescriptor.deliveryName)
                    && Objects.equals(customerId, otherEditDeliveryDescriptor.customerId)
                    && Objects.equals(deliveryDate, otherEditDeliveryDescriptor.deliveryDate)
                    && Objects.equals(status, otherEditDeliveryDescriptor.status)
                    && Objects.equals(note, otherEditDeliveryDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("Delivery Name", deliveryName)
                    .add("Customer Id", customerId)
                    .add("Delivery Date", deliveryDate)
                    .add("Status", status)
                    .add("Note", note)
                    .toString();
        }
    }
}



