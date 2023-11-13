package ui.graphical.lists;

import java.util.Set;

// Represents a JPanel for the list of tags for a cafe.
public class TagsList extends CharmingCafesList {

    public TagsList() {
        super("tags:");
    }

    // MODIFIES: this
    // EFFECTS: loads the tags for the cafe
    public void loadTags(Set<String> tags) {
        if (tags != null) {
            listModel.clear();
            for (String tag : tags) {
                listModel.addElement(tag);
            }
        }
    }
}
