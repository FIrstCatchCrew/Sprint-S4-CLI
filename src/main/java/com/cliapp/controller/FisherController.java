package com.cliapp.controller;

import com.cliapp.model.Person;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class FisherController {
    private final Scanner scanner;

    public FisherController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run(Person fisher) {
        while (true) {
            ConsoleUI.header("Fisher Menu for " + fisher.getUsername());
            ConsoleUI.option("1", "Add New Catch", false);
            ConsoleUI.option("2", "View My Catches", false);
            ConsoleUI.option("3", "Update Catch Info", false);
            ConsoleUI.option("4", "Delete a Catch", false);
            ConsoleUI.option("5", "Q4: My Sales Today", true);
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "5" -> ConsoleUI.success("Q4: Total sales value today...");
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }
}
