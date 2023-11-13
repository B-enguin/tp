package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.logic.Sort;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryStatus;

/**
 * Lists all deliveries in the delivery list to the user.
 */
public class DeliveryListCommand extends DeliveryCommand {
    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "list";
    public static final String MESSAGE_SUCCESS = "Listed Deliveries";
    public static final String MESSAGE_EMPTY = "There are currently no deliveries to be listed.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all deliveries or the deliveries filtered and "
        + "sorted by the specified filters or sort.\n\n"
        + "Parameters: \n"
        + "Any of the fields can be specified.\n"
        + "[" + PREFIX_STATUS + " STATUS] "
        + "[" + PREFIX_CUSTOMER_ID + " CUSTOMER_ID] "
        + "[" + PREFIX_DATE + " DATE] "
        + "[" + PREFIX_SORT + " SORT]\n\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STATUS + " CREATED " + PREFIX_SORT + " ASC "
        + PREFIX_CUSTOMER_ID + " 1 " + PREFIX_DATE + " 2020-10-10";
    private static final Logger logger = Logger.getLogger(DeliveryListCommand.class.getName());
    private DeliveryStatus status;
    private final Integer customerId;
    private final Date deliveryDate;
    private Sort sortType = Sort.DESC;


    /**
     * Constructor for a DeliveryList Command with status filter and sort type.
     *
     * @param status   status to filter by.
     * @param sortType asc or desc.
     */
    public DeliveryListCommand(DeliveryStatus status, Integer customerId, Date deliveryDate, Sort sortType) {

        this.status = status;
        this.customerId = customerId;
        this.deliveryDate = deliveryDate;

        if (sortType != null) {
            this.sortType = sortType;
        }
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeliveryListCommand: status " + status + ", customerId " + customerId
            + " deliveryDate " + deliveryDate + " and sortType " + sortType);
        if (!model.getUserLoginStatus()) {
            logger.warning("Executing DeliveryListCommand failed: user not logged in");
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }
        assert model.getUserLoginStatus() : "User should be logged in";

        Predicate<Delivery> filters = this.createDeliveryListFilters();
        assert filters != null : "Filters should not be null";

        // apply filter
        model.updateFilteredDeliveryList(filters);

        if (model.isFilteredDeliveryListEmpty()) {
            logger.info("Executing DeliveryListCommand: The list is empty");
            return new CommandResult(MESSAGE_EMPTY, true);
        }

        // apply sort if delivery list is not empty
        Comparator<Delivery> sortOrder = createDeliveryListSort();
        assert sortOrder != null : "Sort order should not be null";

        // sort by expected delivery date
        model.updateSortedDeliveryList(sortOrder);

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    /**
     * Applies the filters to the delivery list.
     *
     * @return predicate with filters applied.
     */
    private Predicate<Delivery> createDeliveryListFilters() {
        Optional<DeliveryStatus> status = Optional.ofNullable(this.status);
        Optional<Integer> customerId = Optional.ofNullable(this.customerId);
        Optional<Date> deliveryDate = Optional.ofNullable(this.deliveryDate);

        Predicate<Delivery> filters = PREDICATE_SHOW_ALL_DELIVERIES;

        if (status.isPresent()) {
            // filter by status
            filters = filters.and(delivery -> delivery.isSameDeliveryStatus(status.get()));
        }

        if (customerId.isPresent()) {
            // filter by customer id
            filters = filters.and(delivery -> delivery.isSameCustomerIdToDeliver(customerId.get()));
        }

        if (deliveryDate.isPresent()) {
            // filter by expected delivery date
            filters = filters.and(delivery -> delivery.isSameDeliveryDate(deliveryDate.get()));
        }

        logger.info("Filters: " + filters.toString());
        return filters;
    }

    /**
     * Sorts the delivery list by expected delivery date.
     *
     * @param model model to sort.
     */
    private Comparator<Delivery> createDeliveryListSort() {
        Comparator<Delivery> sortAscending = Comparator.comparing(Delivery::getDeliveryDate);
        Comparator<Delivery> sortDescending = Comparator.comparing(Delivery::getDeliveryDate).reversed();
        boolean isAscending = sortType.equals(Sort.ASC);
        logger.info("Sorting delivery list by expected delivery date: " + isAscending);

        return isAscending ? sortAscending : sortDescending;
    }


    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof DeliveryListCommand)) {
            return false;
        }

        DeliveryListCommand otherDeliveryListCommand = (DeliveryListCommand) other;

        if (this.status != otherDeliveryListCommand.status) {
            return false;
        }

        if (!Objects.equals(this.customerId, otherDeliveryListCommand.customerId)) {
            return false;
        }

        if (this.deliveryDate != null && otherDeliveryListCommand.deliveryDate != null
            && !this.deliveryDate.equals(otherDeliveryListCommand.deliveryDate)) {
            return false;
        }

        return sortType.equals(otherDeliveryListCommand.sortType);
    }
}
