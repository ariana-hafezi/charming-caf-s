package ui.graphical.lists;

import model.Cafe;
import model.MenuItem;

import java.util.List;

// Represents a JPanel for the list of items at a cafe.
public class ItemsList extends CharmingCafesList {

    public ItemsList() {
        super("items:");
    }

    // MODIFIES: this
    // EFFECTS: loads the items for the cafe
    public void loadItems(List<MenuItem> items) {
        if (items != null) {
            listModel.clear();
            for (MenuItem item : items) {
                String name = item.getName();
                listModel.addElement(name);
            }
        }
    }

}
