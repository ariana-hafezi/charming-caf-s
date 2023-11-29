package ui.graphical.tabs;

import model.Cafe;

import javax.swing.*;
import java.awt.*;

import static ui.graphical.CharmingCafesUI.COLOUR;

// Represents the tab for a cafe.
public class CafeTab extends Tab {
    private static final ImageIcon cafeIcon = new ImageIcon("./data/cafe.png");

    // EFFECTS: creates a new cafe tab with the given home
    public CafeTab(Cafe cafe) {
        super(cafe);

        createLabels();
        add(new JLabel(cafeIcon), BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates the and adds the labels for this tab
    private void createLabels() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        Font font = new Font(FONT, Font.PLAIN, FONT_SIZE);

        JLabel label = new JLabel();
        label.setText("welcome to " + cafe.getName() + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);

        JLabel locationLabel = new JLabel();
        locationLabel.setText("location: " + cafe.getLocation());
        locationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        locationLabel.setFont(font);

        JPanel north = createColourPanel();
        panel.add(north);
        panel.add(label);
        panel.add(locationLabel);

        panel.setBackground(Color.decode(COLOUR));

        add(panel, BorderLayout.NORTH);
    }
}
