package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<ListItem> listItems;

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList}.
     *
     * @param items list of items to display.
     */
    public ListPanel(ObservableList<ListItem> items) {
        super(FXML);
        listItems.setItems(items);
        listItems.setCellFactory(listView -> new ItemListViewCell());
    }

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList}.
     *
     * @param items list of items to display.
     */
    public void setListItems(ObservableList<ListItem> items) {
        listItems.setItems(items);
        listItems.setCellFactory(listView -> new ItemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ItemListViewCell extends ListCell<ListItem> {
        @Override
        protected void updateItem(ListItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ItemCard(item).getRoot());
            }
        }
    }

}
