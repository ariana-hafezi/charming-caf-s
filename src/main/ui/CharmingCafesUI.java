package ui;

import model.CafeLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// CharmingCafesUI is a graphical ui for a cafe log application.
public class CharmingCafesUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final String COLOR = "#e6d1ba";
    private static final String FILE = "./data/charmingCafes.json";

    private CafeLog cafeLog;
    private JsonWriter writer;
    private JsonReader reader;

    private ImageIcon icon;
    private CafeList cafeList;
    private JTextField field;

    // EFFECTS: creates a new CharmingCafesUI
    public CharmingCafesUI() {
        cafeLog = new CafeLog();
        writer = new JsonWriter(FILE);
        reader = new JsonReader(FILE);

        icon = new ImageIcon("coffee cup.png");
        cafeList = new CafeList();
        createJTextField();

        setTitle("charming cafés");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode(COLOR));

        addMenu();
        add(field, BorderLayout.SOUTH);
        add(cafeList, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createJTextField() {
        field = new JTextField(20);
        field.getDocument().addDocumentListener(createDocumentListener());
    }

    private DocumentListener createDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cafeList.filterCafes(cafeLog.getCafes(), field.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cafeList.filterCafes(cafeLog.getCafes(), field.getText());
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

    // EFFECTS: runs the CharmingCafesUI
    public static void main(String[] args) {
        new CharmingCafesUI();
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

    // Represents a rank action to rank cafes in the log by average rating
    private class RankAction extends AbstractAction {

        // EFFECTS: creates a new LoadAction titled "load"
        RankAction() {
            super("rank cafes");
        }

        //TODO
        // EFFECTS: ranks cafes in the cafe log by average rating
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
}
