package seedu.address.logic.commands.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Note;
import seedu.address.testutil.DeliveryBuilder;

public class DeliveryCreateNoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);

    @Test
    public void execute_replaceNote_success() {
        Note note = new Note("This is a test note");
        Delivery expectedDelivery = new DeliveryBuilder(GABRIELS_MILK).withNote(note.note).build();
        DeliveryCreateNoteCommand deliveryCreateNoteCommand =
                new DeliveryCreateNoteCommand(GABRIELS_MILK.getDeliveryId(), note);

        String expectedMessage = String.format(DeliveryCreateNoteCommand.MESSAGE_NOTE_SUCCESS,
                Messages.format(expectedDelivery));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(),
                true);
        expectedModel.setDelivery(model.getDeliveryBook().getById(GABRIELS_MILK.getDeliveryId()).get(),
                expectedDelivery);

        assertCommandSuccess(deliveryCreateNoteCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_newNote_success() {
        Note note = new Note("This is a new note");
        Delivery expectedDelivery = new DeliveryBuilder(GAMBES_RICE).withNote(note.note).build();
        DeliveryCreateNoteCommand deliveryCreateNoteCommand =
                new DeliveryCreateNoteCommand(GAMBES_RICE.getDeliveryId(), note);

        String expectedMessage = String.format(DeliveryCreateNoteCommand.MESSAGE_NOTE_SUCCESS,
                Messages.format(expectedDelivery));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(),
                true);
        expectedModel.setDelivery(model.getDeliveryBook().getById(GAMBES_RICE.getDeliveryId()).get(),
                expectedDelivery);

        assertCommandSuccess(deliveryCreateNoteCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidTargetId_throwsCommandException() {
        Note note = new Note("This is a note");
        DeliveryCreateNoteCommand deliveryCreateNoteCommand = new DeliveryCreateNoteCommand(Integer.MAX_VALUE, note);
        assertCommandFailure(deliveryCreateNoteCommand, model, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_newNoteLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        Note note = new Note("This is a new note");
        DeliveryCreateNoteCommand deliveryCreateNoteCommand =
                new DeliveryCreateNoteCommand(GAMBES_RICE.getDeliveryId(), note);

        assertCommandFailure(deliveryCreateNoteCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_invalidLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        Note note = new Note("This is a note");
        DeliveryCreateNoteCommand deliveryCreateNoteCommand = new DeliveryCreateNoteCommand(Integer.MAX_VALUE, note);
        assertCommandFailure(deliveryCreateNoteCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void equals() {
        Note note = new Note("This is a note");
        final DeliveryCreateNoteCommand standardCommand =
                new DeliveryCreateNoteCommand(GABRIELS_MILK.getDeliveryId(), note);

        // same values -> returns true
        DeliveryCreateNoteCommand commandWithSameValue =
                new DeliveryCreateNoteCommand(GABRIELS_MILK.getDeliveryId(), note);
        assertTrue(standardCommand.equals(commandWithSameValue));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeliveryCreateNoteCommand(GAMBES_RICE.getDeliveryId(),
                note)));

        // different note -> returns false
        assertFalse(standardCommand.equals(new DeliveryCreateNoteCommand(GABRIELS_MILK.getDeliveryId(),
                new Note("Different Note"))));
    }

    @Test
    public void toStringMethod() {
        Note note = new Note("This is a note");
        String expected = DeliveryCreateNoteCommand.class.getCanonicalName()
                + "{targetId=" + GABRIELS_MILK.getDeliveryId() + ", note="
                + note + "}";
        DeliveryCreateNoteCommand deliveryStatusCommand =
                new DeliveryCreateNoteCommand(GABRIELS_MILK.getDeliveryId(), note);
        assertEquals(expected, deliveryStatusCommand.toString());
    }
}
