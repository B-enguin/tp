package seedu.address.logic.commands.customer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class CustomerAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);
    }

    @Test
    public void execute_newCustomer_success() {
        Customer validCustomer = new CustomerBuilder().withCustomerId(50).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.addCustomer(validCustomer);

        assertCommandSuccess(new CustomerAddCommand(validCustomer), model,
                String.format(CustomerAddCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                expectedModel, true);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer customerInList = model.getAddressBook().getList().get(0);
        assertCommandFailure(new CustomerAddCommand(customerInList), model,
                CustomerAddCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicateCustomerWithSamePhone_throwsCommandException() {
        // same phone, different name and customerId
        Customer customerInList = model.getAddressBook().getList().get(0);
        Customer customerWithSamePhone = new CustomerBuilder(customerInList).withName("Different Name")
                .withCustomerId(51).build();
        assertCommandFailure(new CustomerAddCommand(customerWithSamePhone), model,
                CustomerAddCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_newCustomerLoggedOut_failure() {
        model.setLogoutSuccess();
        Customer validCustomer = new CustomerBuilder().withCustomerId(50).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.addCustomer(validCustomer);

        assertCommandFailure(new CustomerAddCommand(validCustomer), model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_duplicateCustomerLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        Customer customerInList = model.getAddressBook().getList().get(0);
        assertCommandFailure(new CustomerAddCommand(customerInList), model,
                Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

}
