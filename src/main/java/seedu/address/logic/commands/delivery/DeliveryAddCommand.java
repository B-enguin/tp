package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DATE;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.Objects;
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
import seedu.address.model.delivery.OrderDate;

/**
 * Adds a delivery to the delivery book.
 */
public class DeliveryAddCommand extends DeliveryCommand {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "add";

    /**
     * The text displayed to show what the command does and how to use it.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Delivery to the HomeBoss database.\n\n"
        + "Parameters: "
        + "DELIVERY_NAME "
        + PREFIX_CUSTOMER_ID + " CUSTOMER_ID "
        + PREFIX_DATE + " DELIVERY_DATE\n\n"
        + "Example: " + COMMAND_WORD + " "
        + "furniture "
        + PREFIX_CUSTOMER_ID + " 5 "
        + PREFIX_DATE + " 2023-12-03 ";

    /**
     * The text to the message displayed when the Delivery is added successfuly.
     */
    public static final String MESSAGE_SUCCESS = "New delivery added:\n\n%1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "This delivery already exists in HomeBoss";

    /**
     * The logger instance for DeliveryAddCommand.
     */
    private static final Logger logger = Logger.getLogger(DeliveryAddCommand.class.getName());

    private final DeliveryAddDescriptor deliveryAddDescriptor;

    /**
     * Creates a DeliveryAddCommand to add the specified {@code Delivery}
     */
    public DeliveryAddCommand(DeliveryAddDescriptor deliveryAddDescriptor) {
        requireNonNull(deliveryAddDescriptor);
        this.deliveryAddDescriptor = deliveryAddDescriptor;
    }

    /**
     * Executes the DeliveryAddCommand.
     * @param model {@code Model} which the command should operate on.
     * @return The command result along with the message to be displayed to the user.
     * @throws CommandException If the user is not logged in.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        logger.info("Executing DeliveryAddCommand with the following DeliveryAddDescriptor: "
                + deliveryAddDescriptor.toString() + "\n");

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in.\n");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }


        //Create Delivery
        Delivery toAdd = createDelivery(model, deliveryAddDescriptor);
        logger.info("Delivery to be added: " + toAdd.toString() + "\n");
        assert toAdd != null : "Delivery to be added should not be null.";

        //Add Delivery
        model.addDelivery(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), true);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryAddCommand)) {
            return false;
        }

        DeliveryAddCommand otherAddCommand = (DeliveryAddCommand) other;
        return deliveryAddDescriptor.equals(otherAddCommand.deliveryAddDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", deliveryAddDescriptor)
            .toString();
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code deliveryAddDescriptor}.
     * @param model {@code Model} which the command should operate on.
     * @param deliveryAddDescriptor The descriptor of the delivery to be added.
     * @return The delivery to be added.
     * @throws CommandException If the customer ID is invalid or the delivery date is not a future date.
     */

    private static Delivery createDelivery(Model model,
                                           DeliveryAddDescriptor deliveryAddDescriptor) throws CommandException {

        logger.info("Creating Delivery with:" + "\n"
            + "Delivery Name: " + deliveryAddDescriptor.getDeliveryName().get() + "\n"
            + "Customer ID: " + deliveryAddDescriptor.getCustomerId().get() + "\n"
            + "Delivery Date: " + deliveryAddDescriptor.getDate().get() + "\n");

        int customerId = deliveryAddDescriptor.getCustomerId().get();

        DeliveryDate deliveryDate = null;
        DeliveryStatus newDeliveryStatus = DeliveryStatus.CREATED;
        LocalDate now = LocalDate.now();
        OrderDate orderDate = new OrderDate(now.toString());

        Optional<Customer> targetCustomer = model.getCustomer(customerId);
        DeliveryName deliveryName = deliveryAddDescriptor.getDeliveryName().get();

        if (targetCustomer.isEmpty()) {
            logger.warning("Customer to be added to this Delivery does not exist.\n");
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }
        assert targetCustomer.isPresent() : "Customer to be added to this Delivery should exist.";
        Customer customer = targetCustomer.get();

        if (!DeliveryDate.isFutureDate(deliveryAddDescriptor.getDate().get().toString())) {
            logger.warning("Delivery date is not valid since it is not a future date.\n");
            throw new CommandException(MESSAGE_INVALID_DELIVERY_DATE);
        }

        deliveryDate = deliveryAddDescriptor.getDate().get();

        return new Delivery(deliveryName, customer, orderDate, deliveryDate, newDeliveryStatus);

    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class DeliveryAddDescriptor {
        private DeliveryName deliveryName;
        private int customerId;
        private DeliveryDate deliveryDate;

        public DeliveryAddDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DeliveryAddDescriptor(DeliveryAddDescriptor toCopy) {
            setCustomerId(toCopy.customerId);
            setDeliveryName(toCopy.deliveryName);
            setDeliveryDate(toCopy.deliveryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public void setDeliveryName(DeliveryName deliveryName) {
            this.deliveryName = deliveryName;
        }

        public Optional<DeliveryName> getDeliveryName() {
            return Optional.of(deliveryName);
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public Optional<Integer> getCustomerId() {
            return Optional.of(customerId);
        }

        public void setDeliveryDate(DeliveryDate deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Optional<DeliveryDate> getDate() {
            return Optional.of(deliveryDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeliveryAddDescriptor)) {
                return false;
            }

            DeliveryAddDescriptor otherDeliveryAddDescriptor = (DeliveryAddDescriptor) other;

            return Objects.equals(deliveryName, otherDeliveryAddDescriptor.deliveryName)
                && Objects.equals(customerId, otherDeliveryAddDescriptor.customerId)
                && Objects.equals(deliveryDate, otherDeliveryAddDescriptor.deliveryDate);

        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", deliveryName)
                .add("customer", customerId)
                .add("deliveredAt", deliveryDate)
                .toString();
        }
    }
}

