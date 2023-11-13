package ui.graphical.lists;

import model.Cafe;

import java.util.List;
import java.util.Set;

// Represents a JPanel for the list of cafes in a log.
public class CafeList extends CharmingCafesList {

    // EFFECTS: creates a new cafe list
    public CafeList() {
        super("cafes:");
    }

    // MODIFIES: this
    // EFFECTS: loads the cafe list from file
    public void loadCafes(List<Cafe> cafes) {
        if (cafes != null) {
            listModel.clear();
            for (Cafe cafe : cafes) {
                String name = cafe.getName();
                listModel.addElement(name);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: filters the cafes by user input tag
    public void filterCafes(List<Cafe> cafes, String tag) {
        for (Cafe cafe : cafes) {
            Set<String> tags = cafe.getTags();
            String name = cafe.getName();
            if (tag.equals("")) {
                if (!listModel.contains(name)) {
                    listModel.addElement(name);
                }
            } else if (!tags.contains(tag)) {
                if (listModel.contains(name)) {
                    listModel.removeElement(name);
                }
            } else {
                if (!listModel.contains(name)) {
                    listModel.addElement(name);
                }
            }
        }
    }
}
