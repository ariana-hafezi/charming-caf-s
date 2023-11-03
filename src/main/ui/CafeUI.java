package ui;

import model.Cafe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

// CafeUI is a graphical UI for a given cafe in the charming cafes application.
public class CafeUI extends JFrame {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    private final CharmingCafesUI home;

    // EFFECTS: constructs a new CafeUI with the given cafe and charming cafes home page
    public CafeUI(Cafe cafe, CharmingCafesUI home) {
        this.home = home;

        setTitle("charming cafes - " + cafe.getName());
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        JButton backButton = new JButton(new BackAction());
        add(backButton);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Represents a back action to go back to charming cafes home page.
    private class BackAction extends AbstractAction {
        BackAction() {
            super("back");
        }

        // EFFECTS: goes back to the charming cafes home page
        @Override
        public void actionPerformed(ActionEvent e) {
            home.setVisible(true);
            processWindowEvent(new WindowEvent(CafeUI.this, WindowEvent.WINDOW_CLOSED));
        }
    }
}
