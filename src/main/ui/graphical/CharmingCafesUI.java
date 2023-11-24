package ui.graphical;

import model.Cafe;
import model.CafeLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graphical.lists.CafeList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Note: Code influenced by AlarmSystem example: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// CharmingCafesUI is a graphical ui for a cafe log application.
public class CharmingCafesUI extends JFrame {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    protected static final String FONT = "Sans Serif";
    protected static final int FONT_SIZE = 14;
    public static final String COLOUR = "#e6d1ba";
    private static final String FILE = "./data/charmingCafes.json";
    private static final ImageIcon icon = new ImageIcon("./data/coffee cup.png");

    private CafeLog cafeLog;
    private JsonWriter writer;
    private JsonReader reader;

    private JPanel mainPanel;
    private CafeList cafeList;
    private JPanel textFieldPanel;
    private JTextField textField;
    private JPanel buttonPanel;

    // EFFECTS: creates a new CharmingCafesUI
    public CharmingCafesUI() {
        cafeLog = new CafeLog();
        writer = new JsonWriter(FILE);
        reader = new JsonReader(FILE);

        cafeList = new CafeList();
        createTextFieldPanel();
        createButtonPanel();
        createMainPanel();

        setTitle("charming cafes");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode(COLOUR));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        addMenu();
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        JPanel west = new JPanel();
        west.setBackground(Color.decode(COLOUR));
        add(west, BorderLayout.WEST);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add given cafe to cafe log
    public void addCafe(Cafe cafe) {
        cafeLog.addCafe(cafe);
    }

    // MODIFIES: this
    // EFFECTS: creates main panel for charming cafes
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode(COLOUR));
        mainPanel.add(cafeList, BorderLayout.CENTER);
        mainPanel.add(textFieldPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates panel with JTextField
    private void createTextFieldPanel() {
        textField = new JTextField(15);
        textField.getDocument().addDocumentListener(createDocumentListener());
        textField.setPreferredSize(new Dimension(500, 10));

        textFieldPanel = new JPanel();
        textFieldPanel.setBackground(Color.decode(COLOUR));
        textFieldPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel();
        Font font = new Font(FONT, Font.PLAIN, FONT_SIZE);
        label.setText("\tenter a tag:");
        label.setFont(font);

        textFieldPanel.add(label, 0);
        textFieldPanel.add(textField, 1);
        textFieldPanel.add(new JLabel(), 2);
    }

    // EFFECTS: creates DocumentListener for text field, to filter cafes by tag
    private DocumentListener createDocumentListener() {
        return new DocumentListener() {
            // EFFECTS: filters cafes if there is an insert into the text field
            @Override
            public void insertUpdate(DocumentEvent e) {
                cafeList.filterCafes(cafeLog.getCafes(), textField.getText());
            }

            // EFFECTS: filters cafes if there is a removal from the text field
            @Override
            public void removeUpdate(DocumentEvent e) {
                cafeList.filterCafes(cafeLog.getCafes(), textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
    }

    // EFFECTS: adds the menu bar to the CharmingCafesUI
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("file");
        addMenuItem(fileMenu, new SaveAction());
        addMenuItem(fileMenu, new LoadAction());

        JMenu rankMenu = new JMenu("rank");
        addMenuItem(rankMenu, new RankAction());

        menuBar.add(fileMenu);
        menuBar.add(rankMenu);

        setJMenuBar(menuBar);
    }

    // EFFECTS: adds an item with the given handler to the menu bar
    private void addMenuItem(JMenu menu, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        menu.add(menuItem);
    }

    // Represents a save action to save the cafe log.
    private class SaveAction extends AbstractAction {

        // EFFECTS: creates a new SaveAction titled "save"
        SaveAction() {
            super("save");
        }

        // EFFECTS: saves the state of the application to file
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                writer.open();
                writer.write(cafeLog);
                writer.close();
                String message = "saved! yippee! (^-^*)";
                JOptionPane.showMessageDialog(null, message, "save",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "couldn't save", "save error",
                        JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    // Represents a load action to load the cafe log.
    private class LoadAction extends AbstractAction {

        // EFFECTS: creates a new LoadAction titled "load"
        LoadAction() {
            super("load");
        }

        // MODIFIES: this
        // EFFECTS: loads the saved state of the application from file
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                cafeLog = reader.read();
                cafeList.loadCafes(cafeLog.getCafes());
                JOptionPane.showMessageDialog(null, "loaded! (^-^*)", "load",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "sorry, unable to load! (;-;)",
                        "load error", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        }
    }

    // Represents a rank action to rank cafes in the log by average rating.
    private class RankAction extends AbstractAction {

        // EFFECTS: creates a new RankAction titled "rank cafes"
        RankAction() {
            super("rank cafes");
        }

        // EFFECTS: ranks cafes in the cafe log by average rating
        @Override
        public void actionPerformed(ActionEvent event) {
            List<Cafe> cafes = cafeLog.rankCafes();
            StringBuilder message;
            int rank = 1;

            if (cafes.isEmpty()) {
                message = new StringBuilder("sorry, there are no cafes (;-;)");
            } else {
                message = new StringBuilder("ranking: \n");
                for (Cafe cafe : cafes) {
                    String name = cafe.getName();
                    double rating = cafe.calculateAverageRating();
                    message.append(rank).append(". ").append(name).append(": ").append(rating).append(" stars\n");
                    rank++;
                }
            }

            ImageIcon starIcon = new ImageIcon("star.png");

            JOptionPane.showMessageDialog(null, message.toString(), "ranked cafes",
                    JOptionPane.INFORMATION_MESSAGE, starIcon);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a button panel
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,2));
        buttonPanel.add(new JButton(new AddCafeAction()));
        buttonPanel.add(new JButton(new DeleteCafeAction()));
        buttonPanel.add(new JButton((new OpenCafeAction())));
        buttonPanel.add(new JLabel(icon));
        buttonPanel.setBackground(Color.decode(COLOUR));
    }

    // Represents an action to add a cafe to the log.
    private class AddCafeAction extends AbstractAction {
        AddCafeAction() {
            super("add cafe");
        }

        // MODIFIES: this
        // EFFECTS: adds cafe with user input name and location to the log
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = (String) JOptionPane.showInputDialog(null,
                    "please enter the cafe's name:", "add cafe", JOptionPane.INFORMATION_MESSAGE,
                    icon, null, null);
            if (name != null) {
                String location = (String) JOptionPane.showInputDialog(null,
                        "please enter the cafe's location:", "add cafe", JOptionPane.INFORMATION_MESSAGE,
                        icon, null, null);
                if (location != null) {
                    Cafe cafe = new Cafe(name, location);
                    cafeList.add(cafe.getName());
                    addCafe(cafe);
                }
            }
        }
    }

    // Represents an action to delete a cafe from the log.
    private class DeleteCafeAction extends AbstractAction {
        DeleteCafeAction() {
            super("delete cafe");
        }

        // MODIFIES: this
        // EFFECTS: deletes selected cafe from the log
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = cafeList.getSelectedValue();
            cafeList.delete(name);

            Cafe cafe = new Cafe(name, "");
            cafeLog.removeCafe(cafe);
        }
    }

    // Represents an action to open the selected cafe in the log.
    private class OpenCafeAction extends AbstractAction {
        OpenCafeAction() {
            super("open cafe");
        }

        // MODIFIES: this
        // EFFECTS: if there is a cafe selected, opens a CafeUI JFrame for that cafe, and sets this to not visible
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = cafeList.getSelectedValue();
            if (name != null) {
                Cafe cafe = cafeLog.getCafe(name);
                new CafeUI(cafe, CharmingCafesUI.this);
                CharmingCafesUI.this.setVisible(false);
            }
        }
    }

    // EFFECTS: runs the CharmingCafesUI
    public static void main(String[] args) {
        new CharmingCafesUI();
    }
}