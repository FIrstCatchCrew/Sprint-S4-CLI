package com.cliapp.controller;

import com.cliapp.service.CatchService;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class GuestController {
    private final Scanner scanner;
    private final CatchService catchService;

    public GuestController(Scanner scanner, CatchService catchService) {
        this.scanner = scanner;
        this.catchService = catchService;
    }

    public void run() {
        while (true) {
            ConsoleUI.header("Guest Menu");
            ConsoleUI.option("1", "View Available Catches", false);
            ConsoleUI.option("2", "Search Catches by Species", false);
            ConsoleUI.option("0", "Back to Main Menu", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewAvailableCatches();
                case "2" -> searchCatchesBySpecies();
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Invalid choice. Try again.");
            }
        }
    }

    private void viewAvailableCatches() {
        catchService.getAvailableCatches().forEach(fishCatch -> {
            System.out.println("- " + fishCatch);
        });
    }

    private void searchCatchesBySpecies() {
        System.out.print("Enter species name: ");
        String species = scanner.nextLine();
        catchService.getCatchesBySpecies(species).forEach(fishCatch -> {
            System.out.println("- " + fishCatch);
        });
    }
}
