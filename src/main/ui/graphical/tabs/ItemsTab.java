package ui.graphical.tabs;

import exceptions.PriceException;
import exceptions.RatingException;
import model.Cafe;
import model.MenuItem;
import ui.graphical.lists.ItemsList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static ui.graphical.CharmingCafesUI.COLOUR;

// Represents the tab for items for a cafe.
public class ItemsTab extends Tab {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".00");
    private static final ImageIcon icon = new ImageIcon("./data/cake.png");
    private ItemsList itemsList;
    private JPanel buttonPanel;

    // EFFECTS: creates a new items tab with the given home
    public ItemsTab(Cafe cafe) {
        super(cafe);

        itemsList = new ItemsList();
        itemsList.loadItems(cafe.getItems());
        createButtonPanel();

        add(itemsList, BorderLayout.CENTER);
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
        buttonPanel.setLayout(new GridLayout(5, 2));
        buttonPanel.add(new JButton(new ItemsTab.AddItemAction()));
        buttonPanel.add(new JButton(new ItemsTab.DeleteItemAction()));
        buttonPanel.add(new JButton(new ItemsTab.ViewItemAction()));
        buttonPanel.add(new JButton(new ItemsTab.EditItemAction()));
        buttonPanel.add(new JLabel(icon));
        buttonPanel.setBackground(Color.decode(COLOUR));
    }

    // Represents an action to add an item to the cafe.
    private class AddItemAction extends AbstractAction {
        AddItemAction() {
            super("add item");
        }

        // MODIFIES: this
        // EFFECTS: adds item with user input name, price, and rating to the cafe
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = (String) JOptionPane.showInputDialog(null,
                    "please enter the item's name:", "add item", JOptionPane.INFORMATION_MESSAGE,
                    icon, null, null);
            if (name != null && !name.equals("")) {
                String stringRating = (String) JOptionPane.showInputDialog(null,
                        "please enter a rating from 1 to 5 stars:", "add item",
                        JOptionPane.INFORMATION_MESSAGE, icon, null, null);
                if (stringRating != null && !stringRating.equals("")) {
                    String stringPrice = (String) JOptionPane.showInputDialog(null,
                            "please enter the item's price:", "add item", JOptionPane.INFORMATION_MESSAGE,
                            icon, null, null);
                    if (stringPrice != null && !stringPrice.equals("")) {
                        double price = Double.parseDouble(stringPrice);
                        int rating = Integer.parseInt(stringRating);
                        addItem(name, rating, price);
                    }
                }
            }
        }

        private void addItem(String name, int rating, double price) {
            try {
                MenuItem item = new MenuItem(name, rating, price);
                itemsList.add(item.getName());
                cafe.addItem(item);
            } catch (RatingException ratingException) {
                JOptionPane.showMessageDialog(null, "invalid rating", "add item error",
                        JOptionPane.ERROR_MESSAGE, icon);
            } catch (PriceException priceException) {
                JOptionPane.showMessageDialog(null, "invalid price", "add item error",
                        JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    // Represents an action to delete an item from the cafe.
    private class DeleteItemAction extends AbstractAction {
        DeleteItemAction() {
            super("delete item");
        }

        // MODIFIES: this
        // EFFECTS: deletes selected item from the cafe
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = itemsList.getSelectedValue();
            itemsList.delete(name);

            MenuItem item = new MenuItem(name, 1, 1);
            cafe.removeItem(item);
        }
    }

    // Represents an action to view selected item.
    private class ViewItemAction extends AbstractAction {
        ViewItemAction() {
            super("view item");
        }

        // MODIFIES: this
        // EFFECTS: if there is an item selected, displays its information
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = itemsList.getSelectedValue();
            if (name != null) {
                MenuItem item = cafe.getItem(name);
                double price = parseDouble(DECIMAL_FORMAT.format(item.getPrice()));

                String message = "'" + item.getName() + "'" + " costs $" + price + " and is rated "
                        + item.getRating() + " stars";

                JOptionPane.showMessageDialog(null, message, "item information",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            }
        }
    }

    // Represents an action to edit selected item.
    private class EditItemAction extends AbstractAction {
        EditItemAction() {
            super("edit item");
        }

        // MODIFIES: this
        // EFFECTS: if there is an item selected, allows user to edit its information
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = itemsList.getSelectedValue();
            MenuItem item = cafe.getItem(name);
            String stringRating = (String) JOptionPane.showInputDialog(null,
                    "please enter new rating for the item:", "edit item", JOptionPane.INFORMATION_MESSAGE,
                    icon, null, null);
            if (stringRating != (null) && !stringRating.equals("")) {
                int rating = Integer.parseInt(stringRating);
                newRating(item, rating);
            }

            String stringPrice = (String) JOptionPane.showInputDialog(null,
                    "please enter new price for the item:", "edit item", JOptionPane.INFORMATION_MESSAGE,
                    icon, null, null);
            if (stringPrice != (null) && !stringPrice.equals("")) {
                double price = Double.parseDouble(stringPrice);
                newPrice(item, price);
            }
        }

        // MODIFIES: this
        // EFFECTS: changes item price
        private void newPrice(MenuItem item, double price) {
            try {
                item.setPrice(price);
            } catch (PriceException e) {
                JOptionPane.showMessageDialog(null, "invalid price", "edit item error",
                        JOptionPane.ERROR_MESSAGE, icon);
            }
        }

        // MODIFIES: this
        // EFFECTS: changes item rating
        private void newRating(MenuItem item, int rating) {
            try {
                item.setRating(rating);
            } catch (RatingException e) {
                JOptionPane.showMessageDialog(null, "invalid rating", "edit item error",
                        JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }
}


