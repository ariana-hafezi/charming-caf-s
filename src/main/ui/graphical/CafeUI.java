package ui.graphical;

import model.Cafe;
import model.EventLog;
import ui.graphical.tabs.CafeTab;
import ui.graphical.tabs.ItemsTab;
import ui.graphical.tabs.TagsTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static ui.graphical.CharmingCafesUI.COLOUR;

// CafeUI is a graphical UI for a given cafe in the charming cafes application.
public class CafeUI extends JFrame {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    private static final int CAFE_INDEX = 0;
    private static final int ITEMS_INDEX = 1;
    private static final int TAGS_INDEX = 2;

    private final CharmingCafesUI home;
    private final Cafe cafe;
    private JTabbedPane tabs;

    // EFFECTS: constructs a new CafeUI with the given cafe and charming cafes home page
    public CafeUI(Cafe cafe, CharmingCafesUI home) {
        this.home = home;
        this.cafe = cafe;
        addWindowListener(new CafeWindowAction());

        setTitle("charming cafes - " + this.cafe.getName());
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.decode(COLOUR));

        addMenu();

        createTabbedPane();
        add(tabs);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a menu
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu mainMenu = new JMenu("main menu");
        JMenuItem menuItem = new JMenuItem(new MainMenuAction());
        mainMenu.add(menuItem);

        menuBar.add(mainMenu);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: creates tabbed pane
    private void createTabbedPane() {
        tabs = new JTabbedPane();
        JPanel cafeTab = new CafeTab(cafe);
        JPanel itemsTab = new ItemsTab(cafe);
        JPanel tagsTab = new TagsTab(cafe);

        tabs.add(cafeTab, CAFE_INDEX);
        tabs.setTitleAt(CAFE_INDEX, "cafe");
        tabs.add(itemsTab, ITEMS_INDEX);
        tabs.setTitleAt(ITEMS_INDEX, "items");
        tabs.add(tagsTab, TAGS_INDEX);
        tabs.setTitleAt(TAGS_INDEX, "tags");

        tabs.setTabPlacement(JTabbedPane.LEFT);
        tabs.setBackground(Color.pink);
    }

    // Represents a back action to go back to charming cafes home page.
    private class MainMenuAction extends AbstractAction {

        // EFFECTS: creates a new MainMenuAction titled "main menu"
        MainMenuAction() {
            super("main menu");
        }

        // EFFECTS: goes back to the charming cafes home page
        @Override
        public void actionPerformed(ActionEvent e) {
            home.setVisible(true);
            CafeUI.this.setVisible(false);
        }
    }

    // Represents an action for when the window is closed.
    private class CafeWindowAction extends WindowAdapter {

        // EFFECTS: creates a new CafeWindowAction
        CafeWindowAction() {
            super();
        }

        // EFFECTS: prints event log when application is closed
        @Override
        public void windowClosing(WindowEvent e) {
            home.printEventLog(EventLog.getInstance());
        }
    }

    // getter
    public Cafe getCafe() {
        return this.cafe;
    }
}
