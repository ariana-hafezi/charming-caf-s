package ui.console;

import java.io.FileNotFoundException;

// Runs Charming Cafes application.
public class Main {
    public static void main(String[] args) {
        System.out.println("\nwelcome to charming cafes! (^-^*)/");

        try {
            new CharmingCafes();
        } catch (FileNotFoundException e) {
            System.out.println("unable to start charming cafes, file not found! (;-;)");
        }
    }
}
