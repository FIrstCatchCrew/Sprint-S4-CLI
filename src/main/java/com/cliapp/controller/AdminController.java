package com.cliapp.controller;

import com.cliapp.model.Person;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class AdminController {
    private final Scanner scanner;

    public AdminController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run(Person admin) {
        while (true) {
            ConsoleUI.header("Admin Menu for " + admin.getUsername());
            ConsoleUI.option("1", "View All Fishers", false);
            ConsoleUI.option("2", "View All Buyers", false);
            ConsoleUI.option("3", "View All Catches", false);
            ConsoleUI.option("4", "Delete Catch", false);
            ConsoleUI.option("5", "Remove User", false);
            ConsoleUI.option("6", "Q1: Count Catches by Port", true);
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "6" -> ConsoleUI.success("Q1 logic here...");
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }
}
