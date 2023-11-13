package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ItemCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ListItem item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label accessory;

    @FXML
    private Label mainAccessory;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ItemCard(ListItem item) {
        super(FXML);
        this.item = item;

        this.title.setText(item.getTitle());
        this.description.setText(item.getDescription());
        this.mainAccessory.setText(item.getMainAccessory());
        this.accessory.setText(item.getAccessory());
    }
}
