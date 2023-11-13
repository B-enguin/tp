package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;

/**
 * Edits the details of an existing customer in the address book.
 */
public class CustomerEditCommand extends CustomerCommand {

    /**
     * The command word.
     */
    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " " + "edit";

    /**
     * The text displayed to show what the command does and how to use it.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
        + "by the customer ID used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n\n"
        + "Parameters: CUSTOMER_ID (must be a positive integer and less than 2147483648) "
        + "[" + PREFIX_NAME + " NAME] "
        + "[" + PREFIX_PHONE + " PHONE] "
        + "[" + PREFIX_EMAIL + " EMAIL] "
        + "[" + PREFIX_ADDRESS + " ADDRESS]\n\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + " 91234567 "
        + PREFIX_EMAIL + " johndoe@example.com";

    /**
     * The text to the message displayed when the Customer is edited successfuly.
     */
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Customer:\n\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This customer already exists in the customer database.";

    private static final Logger logger = Logger.getLogger(CustomerEditCommand.class.getName());
    private final Index targetIndex;

    /**
     * The details to edit the person with.
     */
    private final CustomerEditDescriptor customerEditDescriptor;

    /**
     * Creates a CustomerEditCommand to edit the customers.
     *
     * @param targetIndex            of the person in the filtered person list to edit
     * @param customerEditDescriptor details to edit the person with
     */
    public CustomerEditCommand(Index targetIndex, CustomerEditDescriptor customerEditDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(customerEditDescriptor);

        this.targetIndex = targetIndex;
        this.customerEditDescriptor = new CustomerEditDescriptor(customerEditDescriptor);
    }

    /**
     * Executes the CustomerEditCommand.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The command result along with the message to be displayed to the user.
     * @throws CommandException If the user is not logged in or if the customer does not exist or if the edited
     *                          person is the same.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        logger.info("Executing CustomerEditCommand: "
                + "Customer ID: " + targetIndex.getOneBased() + "\n"
                + "CustomerEditDescriptor: " + customerEditDescriptor.toString() + "\n");
        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            logger.warning("User is not logged in.\n");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        model.showAllFilteredCustomerList();
        model.getFilteredCustomerList();

        Optional<Customer> targetCustomer = model.getCustomer(targetIndex.getOneBased());

        if (targetCustomer.isEmpty()) {
            logger.warning("Customer to be edited does not exist.\n");
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToEdit = targetCustomer.get();
        Customer editedCustomer = createEditedCustomer(customerToEdit, customerEditDescriptor);

        if (!customerToEdit.hasSamePhone(editedCustomer) && model.hasCustomerWithSamePhone(editedCustomer)) {
            logger.warning("Customer to be edited already exist.(has the same phone number as another customer)."
                    + "\n");
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        assert customerToEdit != null : "Customer to be edited should exist.";
        assert editedCustomer != null : "Edited Customer should exist.";

        logger.info("Customer to be edited: " + customerToEdit.toString() + "\n");
        logger.info("Edited Customer: " + editedCustomer.toString() + "\n");

        model.setCustomer(customerToEdit, editedCustomer);
        model.showAllFilteredCustomerList();

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedCustomer)), true);


    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code customerEditDescriptor}.
     */
    private static Customer createEditedCustomer(Customer customerToEdit,
                                                 CustomerEditDescriptor customerEditDescriptor) {

        assert customerToEdit != null;

        Name updatedName = customerEditDescriptor.getName().orElse(customerToEdit.getName());
        Phone updatedPhone = customerEditDescriptor.getPhone().orElse(customerToEdit.getPhone());
        Email updatedEmail = customerEditDescriptor.getEmail().orElse(customerToEdit.getEmail());
        Address updatedAddress = customerEditDescriptor.getAddress().orElse(customerToEdit.getAddress());

        logger.info("Creating edited customer with the following details: "
                + "Name: " + updatedName + "\n"
                + "Phone: " + updatedPhone + "\n"
                + "Email: " + updatedEmail + "\n"
                + "Address: " + updatedAddress + "\n");

        return new Customer(customerToEdit.getCustomerId(), updatedName, updatedPhone,
                updatedEmail, updatedAddress);
    }

    /**
     * Updates all the deliveries associated with the customer with new customer details.
     */
    protected static void updateDelivery(Model model, Customer editedCustomer) {
        int customerId = editedCustomer.getCustomerId();
        Stream<Delivery> deliveries = model.getDeliveryByCustomerId(customerId);
        deliveries.forEach(d -> {
            Delivery editedDelivery = createEditedDelivery(d, editedCustomer);
            model.setDelivery(d, editedDelivery);
        });
    }

    /**
     * Creates and returns a {@code Delivery} with the customer details edited.
     *
     * @param deliveryToEdit         {@code Delivery} which the command edits.
     * @param editedCustomer         {@code Customer} which the delivery is associated with.
     */
    private static Delivery createEditedDelivery(Delivery deliveryToEdit, Customer editedCustomer) {

        assert deliveryToEdit != null;

        DeliveryName deliveryName = deliveryToEdit.getName();
        assert deliveryName != null;

        OrderDate orderDate = deliveryToEdit.getOrderDate();
        assert orderDate != null;

        DeliveryDate deliveryDate = deliveryToEdit.getDeliveryDate();
        assert deliveryDate != null;

        DeliveryStatus deliveryStatus = deliveryToEdit.getStatus();
        assert deliveryStatus != null;

        Note note = deliveryToEdit.getNote();

        return new Delivery(deliveryToEdit.getDeliveryId(), deliveryName, editedCustomer, orderDate,
                deliveryDate, deliveryStatus, note);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerEditCommand)) {
            return false;
        }

        CustomerEditCommand otherEditCommand = (CustomerEditCommand) other;
        return targetIndex.equals(otherEditCommand.targetIndex)
                && customerEditDescriptor.equals(otherEditCommand.customerEditDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", targetIndex)
                .add("customerEditDescriptor", customerEditDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class CustomerEditDescriptor {
        private int customerId;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;

        public CustomerEditDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public CustomerEditDescriptor(CustomerEditDescriptor toCopy) {
            setCustomerId(toCopy.customerId);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof CustomerEditDescriptor)) {
                return false;
            }

            CustomerEditDescriptor otherCustomerEditDescriptor = (CustomerEditDescriptor) other;
            return Objects.equals(name, otherCustomerEditDescriptor.name)
                    && Objects.equals(phone, otherCustomerEditDescriptor.phone)
                    && Objects.equals(email, otherCustomerEditDescriptor.email)
                    && Objects.equals(address, otherCustomerEditDescriptor.address);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("customerId", customerId)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .toString();
        }
    }
}
