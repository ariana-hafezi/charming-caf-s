package ui;

import model.Cafe;
import model.CafeLog;
import model.MenuItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


// Note: displayMenu() and processCommand() modeled after the TellerApp example.
// loadCafeLog() and saveCafeLog() modelled after loadWorkRoom() and saveWorkRoom() in JsonSerializationDemo.
// CharmingCafes is a cafe log application.
public class CharmingCafes {

    private static final String INVALID_COMMAND_STATEMENT = "\nsorry, please enter a valid command! ('-'*)";

    private static final String CAFE_COMMAND = "c";
    private static final String LOG_COMMAND = "l";
    private static final String DELETE_COMMAND = "d";
    private static final String RATING_COMMAND = "r";
    private static final String SAVE_COMMAND = "s";
    private static final String QUIT_COMMAND = "q";

    private static final String LOCATION_COMMAND = "l";
    private static final String ITEMS_COMMAND = "i";
    private static final String ADD_COMMAND = "a";
    private static final String TAGS_COMMAND = "t";
    private static final String BACK_COMMAND = "b";
    private static final String PRICE_COMMAND = "p";
    private static final String MAIN_MENU_COMMAND = "m";

    private static final String FILE = "./data/cafeLog.json";
    private CafeLog cafeLog;
    private Scanner input;
    private JsonWriter writer;
    private JsonReader reader;

    // Runs the charming cafes application.
    public CharmingCafes() throws FileNotFoundException {
        runCharmingCafes();
    }

    // MODIFIES: this
    // EFFECTS: processes user input to allow the cafe app to run
    private void runCharmingCafes() {
        boolean runApp = true;
        String command;

        initialize();

        while (runApp) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals(QUIT_COMMAND)) {
                runApp = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\ngoodbye! (^-^*)");
    }

    // MODIFIES: this
    // EFFECTS: initializes the cafe log and scanner
    private void initialize() {
        cafeLog = new CafeLog();
        input = new Scanner(System.in);
        writer = new JsonWriter(FILE);
        reader = new JsonReader(FILE);
    }

    // EFFECTS: prints out the options for input commands
    private void displayMenu() {
        System.out.println("\nplease enter one of the following commands:");
        System.out.println("\tview cafes -> " + CAFE_COMMAND);
        System.out.println("\tlog a cafe -> " + LOG_COMMAND);
        System.out.println("\tdelete a cafe -> " + DELETE_COMMAND);
        System.out.println("\trank cafes -> " + RATING_COMMAND);
        System.out.println("\tsearch cafes by tag -> " + TAGS_COMMAND);
        System.out.println("\tsave and load menu -> " + SAVE_COMMAND);
        System.out.println("\tquit -> " + QUIT_COMMAND);
    }

    // MODIFIES: this
    // EFFECTS: processes the input command
    private void processCommand(String command) {
        switch (command) {
            case CAFE_COMMAND:
                printLog();
                break;
            case LOG_COMMAND:
                logCafe();
                break;
            case DELETE_COMMAND:
                deleteCafe();
                break;
            case RATING_COMMAND:
                rankCafes();
                break;
            case TAGS_COMMAND:
                displayCafesWithTag();
                break;
            case SAVE_COMMAND:
                displaySaveMenu();
                break;
            case BACK_COMMAND:
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
        }
    }

    // REQUIRES: the cafe have a unique name (not already in the log)
    // MODIFIES: this
    // EFFECTS: adds a cafe to the log
    private void logCafe() {
        System.out.println("\nplease enter the cafe's unique name:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("\nplease enter the cafe's location:");
        String location = input.nextLine();
        Cafe cafe = new Cafe(name, location);
        cafeLog.addCafe(cafe);
        System.out.println("\n" + cafe.getName() + " has been added to the log! yippee! (^-^*)");
    }

    // MODIFIES: this
    // EFFECTS: deletes the cafe with the given name from the log
    private void deleteCafe() {
        System.out.println("\nplease enter the name of the cafe you'd like to delete:");
        input.nextLine();
        String name = input.nextLine();
        List<Cafe> cafes = cafeLog.getCafes();
        Cafe cafeToDelete = null;
        for (Cafe cafe : cafes) {
            if (name.equals(cafe.getName())) {
                cafeToDelete = cafe;
                break;
            }
        }
        if (cafeToDelete == null) {
            System.out.println("\nsorry, the cafe wasn't in the log! (;-;)");
        } else {
            cafeLog.removeCafe(cafeToDelete);
            System.out.println("\n" + name + " was deleted");
        }
    }

    // EFFECTS: print out the ranked list of cafes in the log
    private void rankCafes() {
        List<Cafe> cafes = cafeLog.rankCafes();
        int rank = 1;

        if (cafes.isEmpty()) {
            System.out.println("\nsorry, there are no cafes to rank! (;-;)");
        } else {
            System.out.println("\nhere's the ranking:");
            for (Cafe cafe : cafes) {
                String name = cafe.getName();
                double rating = cafe.calculateAverageRating();
                System.out.print("\t" + rank + ". " + name + ": " + rating + " stars\n");
                rank++;
            }
        }
    }

    // EFFECTS: print out the cafes in the log with the input tag
    private void displayCafesWithTag() {
        System.out.println("\nplease enter the tag you'd like to search for:");
        input.nextLine();
        String tag = input.nextLine();

        List<Cafe> cafes = cafeLog.cafesByTag(tag);

        if (cafes.isEmpty()) {
            System.out.println("\nsorry, there are no cafes with the tag " + tag + "! (;-;)");
        } else {
            System.out.println("\nhere are the cafes with the tag: '" + tag + "'.");
            for (Cafe cafe : cafes) {
                System.out.println("\t-> " + cafe.getName());
            }
        }
    }


    // EFFECTS: displays the menu for saving or loading a cafe log
    private void displaySaveMenu() {
        System.out.println("\nplease enter one of the following commands:");
        System.out.println("\tload cafe log -> " + LOG_COMMAND);
        System.out.println("\tsave -> " + SAVE_COMMAND);
        System.out.println("\tback -> " + BACK_COMMAND);

        String command = input.next();
        command = command.toLowerCase();
        processSaveCommand(command);
    }

    // MODIFIES: this
    // EFFECTS: processes save or load command
    private void processSaveCommand(String command) {
        switch (command) {
            case LOG_COMMAND:
                loadCafeLog();
                break;
            case SAVE_COMMAND:
                saveCafeLog();
                break;
            case BACK_COMMAND:
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
                break;
        }
    }

    // EFFECTS: prints out the cafes in the log
    private void printLog() {
        List<Cafe> cafes = cafeLog.getCafes();

        if (cafes.isEmpty()) {
            System.out.println("\nsorry, there are no cafes! (;-;)");
        } else {
            System.out.println("\nhere are the cafes:");
            for (Cafe cafe : cafes) {
                System.out.println("\t-> " + cafe.getName());
            }
            displayLogMenu();
        }
    }

    // REQUIRES: the user input be the back command or a cafe's name in the log
    // EFFECTS: displays the menu for the cafe log
    private void displayLogMenu() {
        System.out.println("\nto view a cafe, please enter its name, to go back, enter '" + BACK_COMMAND + "':");
        Cafe cafe = null;
        input.nextLine();
        String command = input.nextLine();
        if (command.equals(BACK_COMMAND)) {
            processCommand(BACK_COMMAND);
        } else {
            for (Cafe c : cafeLog.getCafes()) {
                String name = c.getName();
                if (name.equals(command)) {
                    cafe = c;
                    displayCafe(cafe);
                    break;
                }
            }
            if (cafe == null) {
                System.out.println("\nsorry, there is no cafe with that name! (;-;)");
            }
        }
    }

    // EFFECTS: displays information and menu for given cafe
    private void displayCafe(Cafe cafe) {
        String name = cafe.getName();

        System.out.println("\nplease enter one of the following commands for " + name + ":");
        System.out.println("\tlocation -> " + LOCATION_COMMAND);
        System.out.println("\titems menu -> " + ITEMS_COMMAND);
        System.out.println("\ttags menu -> " + TAGS_COMMAND);
        System.out.println("\tmain menu -> " + MAIN_MENU_COMMAND);

        String command = input.next();
        command = command.toLowerCase();
        processCafeCommand(cafe, command);
    }

    // MODIFIES: cafe
    // EFFECTS: processes user input for a cafe
    private void processCafeCommand(Cafe cafe, String command) {
        switch (command) {
            case LOCATION_COMMAND:
                System.out.println("\n" + cafe.getName() + " is located in " + cafe.getLocation());
                displayCafe(cafe);
                break;
            case ITEMS_COMMAND:
                displayItemsMenu(cafe);
                break;
            case TAGS_COMMAND:
                displayTagsMenu(cafe);
                break;
            case MAIN_MENU_COMMAND:
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
                displayCafe(cafe);
        }
    }

    // EFFECTS: displays tags menu for given cafe
    private void displayTagsMenu(Cafe cafe) {
        System.out.println("\nfor " + cafe.getName() + "'s tags, please enter one of the following commands:");
        System.out.println("\tview tags -> " + TAGS_COMMAND);
        System.out.println("\tadd tag -> " + ADD_COMMAND);
        System.out.println("\tdelete tag -> " + DELETE_COMMAND);
        System.out.println("\tgo back -> " + BACK_COMMAND);
        System.out.println("\tmain menu -> " + MAIN_MENU_COMMAND);

        String command = input.next();
        command = command.toLowerCase();
        processTagCommand(cafe, command);
    }

    // MODIFIES: cafe
    // EFFECTS: processes user input for a tag at given cafe
    private void processTagCommand(Cafe cafe, String command) {
        switch (command) {
            case TAGS_COMMAND:
                printTags(cafe);
                displayTagsMenu(cafe);
                break;
            case ADD_COMMAND:
                addTagToCafe(cafe);
                displayTagsMenu(cafe);
                break;
            case DELETE_COMMAND:
                deleteTagFromCafe(cafe);
                displayTagsMenu(cafe);
                break;
            case BACK_COMMAND:
                displayCafe(cafe);
                break;
            case MAIN_MENU_COMMAND:
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
                displayTagsMenu(cafe);
        }
    }

    // EFFECTS: display the tags for the given cafe
    private void printTags(Cafe cafe) {
        Set<String> tags = cafe.getTags();
        String cafeName = cafe.getName();

        if (tags.isEmpty()) {
            System.out.println("\nsorry, there are no tags for " + cafeName + "! (;-;)");
        } else {
            System.out.println("\nhere are the tags for " + cafeName + ":");
            for (String tag : tags) {
                System.out.println("\t-> " + tag);
            }
        }
    }

    // MODIFIES: cafe
    // EFFECTS: add tag to the cafe
    private void addTagToCafe(Cafe cafe) {
        System.out.println("\nplease enter the unique tag:");
        input.nextLine();
        String tag = input.nextLine();
        String cafeName = cafe.getName();

        Set<String> tags = cafe.getTags();
        if (tags.contains(tag)) {
            System.out.println("\nsorry, the tag '" + tag + "' has already been added to " + cafeName + "! (;-;)");
        } else {
            cafe.addTag(tag);
            System.out.println("\nthe tag '" + tag + "' has been added to " + cafeName + "! yippee! (^-^*)");
        }
    }

    // MODIFIES: cafe
    // EFFECTS: deletes the input tag from the given cafe's tags
    private void deleteTagFromCafe(Cafe cafe) {
        System.out.println("\nplease enter the tag you'd like to delete:");
        input.nextLine();
        String tag = input.nextLine();
        Set<String> tags = cafe.getTags();
        if (tags.contains(tag)) {
            cafe.removeTag(tag);
            System.out.println("\nthe tag '" + tag + "' was removed");
        } else {
            System.out.println("\nsorry, " + cafe.getName() + " doesn't have that tag! (;-;)");
        }
    }

    // EFFECTS: displays items menu for given cafe
    private void displayItemsMenu(Cafe cafe) {
        System.out.println("\nfor " + cafe.getName() + "'s items, please enter one of the following commands:");
        System.out.println("\tview items -> " + ITEMS_COMMAND);
        System.out.println("\tadd item -> " + ADD_COMMAND);
        System.out.println("\tdelete item -> " + DELETE_COMMAND);
        System.out.println("\tgo back -> " + BACK_COMMAND);
        System.out.println("\tmain menu -> " + MAIN_MENU_COMMAND);

        String command = input.next();
        command = command.toLowerCase();
        processItemCommand(cafe, command);
    }

    // MODIFIES: cafe
    // EFFECTS: processes user input for an item at given cafe
    private void processItemCommand(Cafe cafe, String command) {
        switch (command) {
            case ITEMS_COMMAND:
                printItems(cafe);
                displayItemsMenu(cafe);
                break;
            case ADD_COMMAND:
                addItemToCafe(cafe);
                displayItemsMenu(cafe);
                break;
            case DELETE_COMMAND:
                deleteItemFromCafe(cafe);
                displayItemsMenu(cafe);
                break;
            case BACK_COMMAND:
                displayCafe(cafe);
                break;
            case MAIN_MENU_COMMAND:
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
                displayItemsMenu(cafe);
        }
    }

    // REQUIRES: input rating be [1,5], input price >= 0, input name not belong to an item
    // previously logged at the cafe
    // MODIFIES: cafe
    // EFFECTS: add item to the cafe
    private void addItemToCafe(Cafe cafe) {
        System.out.println("\nplease enter the unique name of the item you wish to log:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("\nplease enter a rating between 1 and 5 stars:");
        int rating = input.nextInt();
        System.out.println("\nplease enter the price in cents:");
        int price = input.nextInt();
        MenuItem item = new MenuItem(name, rating, price);
        cafe.addItem(item);
        System.out.println("\n" + name + " has been logged at " + cafe.getName() + "! yippee! (^-^*)");
    }

    // MODIFIES: cafe
    // EFFECTS: deletes the item with the input name from the given cafe
    private void deleteItemFromCafe(Cafe cafe) {
        System.out.println("\nplease enter the name of the item you'd like to delete:");
        input.nextLine();
        String name = input.nextLine();
        List<MenuItem> items = cafe.getItems();
        MenuItem itemToDelete = null;
        for (MenuItem item : items) {
            if (name.equals(item.getName())) {
                itemToDelete = item;
                break;
            }
        }
        if (itemToDelete == null) {
            System.out.println("\nsorry, the item '" + name + "' wasn't in " + cafe.getName() + "'s items! (;-;)");
        } else {
            cafe.removeItem(itemToDelete);
            System.out.println("\n" + name + " was deleted");
        }
    }

    // EFFECTS: display items at cafe
    private void printItems(Cafe cafe) {
        List<MenuItem> items = cafe.getItems();
        String cafeName = cafe.getName();

        if (items.isEmpty()) {
            System.out.println("\nsorry, there are no items for " + cafeName + "! (;-;)");
        } else {
            System.out.println("\nhere are the items at " + cafeName + ":");
            for (MenuItem item : items) {
                System.out.println("\t-> " + item.getName());
            }
            displayEditItemMenu(cafe);
        }
    }

    /// REQUIRES: the user input be the back command or a item's name at the given cafe
    // EFFECTS: displays the menu for editing an item at given cafe
    private void displayEditItemMenu(Cafe cafe) {
        System.out.println("\nto view an item, please enter its name, to go back, enter '" + BACK_COMMAND + "':");
        input.nextLine();
        String command = input.nextLine();
        MenuItem item = null;
        if (command.equals(BACK_COMMAND)) {
            processItemCommand(cafe, BACK_COMMAND);
        } else {
            for (MenuItem i : cafe.getItems()) {
                String name = i.getName();
                if (name.equals(command)) {
                    item = i;
                    displayItem(item, cafe);
                    break;
                }
            }
            if (item == null) {
                System.out.println("\nsorry, there is no item with that name at " + cafe.getName() + "! (;-;)");
            }
        }
    }

    // EFFECTS: displays item's information and options for modifying it
    private void displayItem(MenuItem item, Cafe cafe) {
        String name = item.getName();
        System.out.println("\nplease enter one of the following commands for " + name + ":");
        System.out.println("\titem information -> " + ITEMS_COMMAND);
        System.out.println("\tedit rating -> " + RATING_COMMAND);
        System.out.println("\tedit price -> " + PRICE_COMMAND);
        System.out.println("\tgo back -> " + BACK_COMMAND);

        String command = input.next();
        command = command.toLowerCase();
        processSpecificItemCommand(item, command, cafe);
    }

    // MODIFIES: item
    // EFFECTS: processes user input for the given item
    private void processSpecificItemCommand(MenuItem item, String command, Cafe cafe) {
        switch (command) {
            case ITEMS_COMMAND:
                printItemInformation(item);
                displayItem(item, cafe);
                break;
            case RATING_COMMAND:
                changeRating(item);
                displayItem(item, cafe);
                break;
            case PRICE_COMMAND:
                changePrice(item);
                displayItem(item, cafe);
                break;
            case BACK_COMMAND:
                displayItemsMenu(cafe);
                break;
            default:
                System.out.println(INVALID_COMMAND_STATEMENT);
                displayItem(item, cafe);
        }
    }

    // EFFECTS: prints the name, rating, and price of the given item
    private void printItemInformation(MenuItem item) {
        String name = item.getName();
        int rating = item.getRating();
        int price = item.getPrice();
        if (price == 0) {
            System.out.println("\n" + name + " is rated " + rating + " stars and is free!");
        } else {
            System.out.println("\n" + name + " is rated " + rating + " stars and costs " + price + " cents");
        }
    }

    // REQUIRES: input rating be an integer in [1,5]
    // MODIFIES: item
    // EFFECTS: changes the rating of the given item
    private void changeRating(MenuItem item) {
        String name = item.getName();
        System.out.println("\nplease enter the new rating for " + name + ":");
        int rating = input.nextInt();
        item.setRating(rating);
        System.out.println("\n" + name + "'s rating has been changed!");
    }

    // REQUIRES: input price be an integer >= 0
    // MODIFIES: item
    // EFFECTS: changes the price of the given item
    private void changePrice(MenuItem item) {
        String name = item.getName();
        System.out.println("\nplease enter the new price for " + name + ":");
        int price = input.nextInt();
        item.setPrice(price);
        System.out.println("\n" + name + "'s price has been changed!");
    }

    // EFFECTS: saves the cafe log to file
    private void saveCafeLog() {
        try {
            writer.open();
            writer.write(cafeLog);
            writer.close();
            System.out.println("saved the cafe log to " + FILE + " yippee! (^-^*)");
        } catch (FileNotFoundException e) {
            System.out.println("sorry, unable to write to the file: " + FILE + " (;-;)");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads cafe log from file
    private void loadCafeLog() {
        try {
            cafeLog = reader.read();
            System.out.println("loaded cafe log from " + FILE + " yippee! (^-^*)");
        } catch (IOException e) {
            System.out.println("sorry, unable to read from the file: " + FILE + " (;-;)");
        }
    }
}
