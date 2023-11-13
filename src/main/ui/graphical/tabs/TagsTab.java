package ui.graphical.tabs;

import model.Cafe;
import ui.graphical.CafeUI;
import ui.graphical.lists.TagsList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static ui.graphical.CharmingCafesUI.COLOUR;

// Represents the tab for tags for a cafe.
public class TagsTab extends Tab {
    private static final ImageIcon icon = new ImageIcon("./data/matcha.png");
    private TagsList tagsList;
    private JPanel buttonPanel;
    private final Cafe cafe;

    // EFFECTS: creates a new tags tab with the given home
    public TagsTab(CafeUI home) {
        super(home);

        cafe = home.getCafe();

        tagsList = new TagsList();
        tagsList.loadTags(cafe.getTags());
        add(tagsList, BorderLayout.CENTER);

        createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);

        JPanel south = createColourPanel();
        JPanel west = createColourPanel();
        add(south, BorderLayout.SOUTH);
        add(west, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates a button panel
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        buttonPanel.add(new JButton(new TagsTab.AddTagAction()));
        buttonPanel.add(new JButton(new TagsTab.DeleteTagAction()));
        buttonPanel.add(createColourPanel());
        buttonPanel.add(new JLabel(icon));
        buttonPanel.setBackground(Color.decode(COLOUR));
    }

    // Represents an action to add an item to the cafe.
    private class AddTagAction extends AbstractAction {
        AddTagAction() {
            super("add tag");
        }

        // MODIFIES: this
        // EFFECTS: adds user input tag to the cafe
        @Override
        public void actionPerformed(ActionEvent e) {
            String tag = (String) JOptionPane.showInputDialog(null,
                    "please enter a tag:", "add tag", JOptionPane.INFORMATION_MESSAGE,
                    icon, null, null);

            tagsList.add(tag);
            home.addTag(tag);
        }
    }

    // Represents an action to delete a tag from the cafe.
    private class DeleteTagAction extends AbstractAction {
        DeleteTagAction() {
            super("delete tag");
        }

        // MODIFIES: this
        // EFFECTS: deletes selected tag from the cafe
        @Override
        public void actionPerformed(ActionEvent e) {
            String tag = tagsList.getSelectedValue();
            tagsList.delete(tag);

            cafe.removeTag(tag);
        }
    }

}

