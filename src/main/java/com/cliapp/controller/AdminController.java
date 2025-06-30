/**
 * Description: CLI controller for Admin users.
 *              Includes options to view users, catch records, and perform admin tasks like deletes or analytics.
 */


package com.cliapp.controller;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.FisherProfile;
import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.FisherService;
import com.cliapp.service.OrderService;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminController {
    private final Scanner scanner;
    private final CatchService catchService;
    private final PersonService personService;
    //private final OrderService orderService;
    //private final FisherService fisherService;


    public AdminController(Scanner scanner, CatchService catchService, PersonService personService) {
        this.scanner = scanner;
        this.catchService = catchService;
        this.personService = personService;
    }
//
//    public AdminController(Scanner scanner, CatchService catchService, PersonService personService, FisherService fisherService, OrderService orderService) {
//        this.scanner = scanner;
//        this.catchService = catchService;
//        this.personService = personService;
//        this.fisherService = fisherService;
//        this.orderService = orderService;
//    }

    public void run(Person admin) {
        while (true) {
            ConsoleUI.header("Admin Menu for " + admin.getUsername());
            ConsoleUI.option("1", "View All Fishers", true);
            ConsoleUI.option("2", "View All Buyers", true);
            ConsoleUI.option("3", "View All Catches", false);
            ConsoleUI.option("4", "Delete Catch", false);
            ConsoleUI.option("5", "Remove User", false);
            ConsoleUI.option("6", "Q1: Count Catches by Species", true);
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewAllFishers();
                case "2" -> viewAllCustomers();
                case "6" -> getCatchCountBySpecies(scanner);
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }

    private void viewAllFishers() {
        List<FisherProfile> fishers = personService.getAllFishers();
        ConsoleUI.header("All Registered Fishers");
        for (FisherProfile f : fishers) {
            System.out.printf("Fisher ID: %d | License: %s | Name: %s | Landing: %s\n",
                    f.getId(), f.getFishingLicenseNumber(), f.getUserName(), f.getDefaultLanding());
        }
    }

    private void viewAllCustomers() {
        try {
            List<Person> customers = personService.getAllByRoleType("customer");
            ConsoleUI.header("All Registered Buyers");
            for (Person c : customers) {
                System.out.printf("Buyer ID: %d | Name: %s\n", c.getId(), c.getUsername());
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch or parse customers: " + e.getMessage());
            e.printStackTrace(); // Optional: for full trace
        }
    }


    private void getCatchCountBySpecies(Scanner scanner) {

        System.out.print("Enter species name: ");
        String name = scanner.nextLine();
        if (name.isBlank()) {
            ConsoleUI.error("Species name cannot be blank.");
            return;
        }
        Map<String, Long> countBySpecies = catchService.getCatchCountBySpecies(name);

        ConsoleUI.header("Catch Counts by Species");
        countBySpecies.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.printf("Port: %-20s | Catches: %d\n", entry.getKey(), entry.getValue())
                );
    }


}
