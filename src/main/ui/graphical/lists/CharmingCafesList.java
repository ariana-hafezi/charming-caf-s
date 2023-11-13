package ui.graphical.lists;

import model.Cafe;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import static ui.graphical.CharmingCafesUI.COLOUR;

// Represents a JPanel for a list in the charming cafes application.
public abstract class CharmingCafesList extends JPanel implements ListSelectionListener {
    protected static final String FONT = "Sans Serif";
    protected static final int FONT_SIZE = 14;
    protected JList list;
    protected DefaultListModel listModel;

    // EFFECTS: creates a new list
    public CharmingCafesList(String label) {
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

        JLabel title = new JLabel();
        title.setText("\t" + label);
        title.setFont(font);

        add(listScrollPane, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        setBackground(Color.decode(COLOUR));
    }

    // MODIFIES: this
    // EFFECTS: deletes element's name from the list
    public void delete(String name) {
        listModel.removeElement(name);
    }

    // MODIFIES: this
    // EFFECTS: adds element to the list if it isn't already in the list
    public void add(String name) {
        if (!listModel.contains(name)) {
            listModel.addElement(name);
        }
    }

    // EFFECTS: returns the value selected in the list
    public String getSelectedValue() {
        return (String) list.getSelectedValue();
    }

    // required by ListSelectionListener
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
