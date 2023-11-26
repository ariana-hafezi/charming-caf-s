package ui.graphical.tabs;

import model.Cafe;

import javax.swing.*;
import java.awt.*;

import static ui.graphical.CharmingCafesUI.COLOUR;

// Note: Code influenced by SmartHomeUI example: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// Represents a tab for a cafe in CharmingCafesUI.
public abstract class Tab extends JPanel {
    protected static final String FONT = "Sans Serif";
    protected static final int FONT_SIZE = 14;
    protected final Cafe cafe;

    // EFFECTS: creates a new tab with the given home
    public Tab(Cafe cafe) {
        this.cafe = cafe;
        setLayout(new BorderLayout());
        setBackground(Color.decode(COLOUR));
    }

    // EFFECTS: creates a JPanel with background colour
    protected JPanel createColourPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode(COLOUR));
        return panel;
    }
}

