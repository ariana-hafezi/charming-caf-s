package ui;

import model.Cafe;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.Set;

// Represents a JPanel for the list of cafes in a log.
public class CafeList extends JPanel implements ListSelectionListener {
    private static final String FONT = "Sans Serif";
    private static final int FONT_SIZE = 14;
    private JList list;
    private DefaultListModel listModel;

    // EFFECTS: creates a new cafe list
    public CafeList() {
        super(new BorderLayout());

        listModel = new DefaultListModel<>();
        Font font = new Font(FONT, Font.PLAIN, FONT_SIZE);

        list = new JList(listModel);
        list.setFont(font);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(-1);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);

        add(listScrollPane, BorderLayout.CENTER);
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

    // MODIFIES: this
    // EFFECTS: adds cafe's name to the list if it isn't already in the list
    public void addCafe(Cafe cafe) {
        String name = cafe.getName();
        if (!listModel.contains(name)) {
            listModel.addElement(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes cafe's name from the list
    public void deleteCafe(String name) {
        listModel.removeElement(name);
    }

    // EFFECTS: returns the value selected in the list
    public String getSelectedValue() {
        return (String) list.getSelectedValue();
    }

    // EFFECTS: required by ListSelectionListener
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
