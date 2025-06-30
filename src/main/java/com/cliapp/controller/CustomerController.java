/**
 * Description: CLI controller for Customer users.
 *              Displays menu for viewing available catches, searching by filters, and making orders.
 */


package com.cliapp.controller;

import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.Order;
import com.cliapp.model.OrderItem;
import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.OrderService;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;

import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private final Scanner scanner;
    private final CatchService catchService;
    private final PersonService personService;
    private final OrderService orderService;

    public CustomerController(Scanner scanner, CatchService catchService, PersonService personService, OrderService orderService) {
        this.scanner = scanner;
        this.catchService = catchService;
        this.personService = personService;
        this.orderService = orderService;
    }


    public void run(Person customer) {
        while (true) {
            ConsoleUI.header("Customer Menu for " + customer.getUsername());
            ConsoleUI.option("1", "View Available Catches", true);
            ConsoleUI.option("2", "Search by Species", true);
            ConsoleUI.option("3", "Search by Port", true); // Q2
            ConsoleUI.option("4", "Search by Fisher", true);
            ConsoleUI.option("5", "Search by Species and Port", true);
            ConsoleUI.option("6", "Purchase Catch", false);
            ConsoleUI.option("7", "View My Purchases", true); // Q3
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewAvailableCatches();
                case "2" -> viewBySpecies();
                case "3" -> viewByLanding();
                case "4" -> viewByFisher();
                case "5" -> searchBySpeciesAndLanding();
                case "7" -> viewMyPurchases(customer);
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }

    private void viewAvailableCatches() {
        List<CatchViewDTO> catches = catchService.getAvailableCatches();
        if (catches.isEmpty()) {
            ConsoleUI.info("No catches available today. Try again later.");
            return;
        }

        ConsoleUI.header("All available catches: ");
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }

    private void viewBySpecies() {
        System.out.print("Enter species name: ");
        String species = scanner.nextLine();

        List<CatchViewDTO> catches = catchService.searchBySpeciesName(species);

        if (catches.isEmpty()) {
            ConsoleUI.info("No catches found for that species.");
            return;
        }

        ConsoleUI.header("Available Catches for Species: " + species);
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }

    private void viewByLanding() {
        System.out.print("Enter landing name: ");
        String landing = scanner.nextLine();

        List<CatchViewDTO> catches = catchService.searchByLandingName(landing);

        if (catches.isEmpty()) {
            ConsoleUI.info("No catches found for that landing.");
            return;
        }

        ConsoleUI.header("Available Catches for Landing: " + landing);
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }

    private void searchBySpeciesAndLanding() {
        System.out.print("Enter species name: ");
        String species = scanner.nextLine();

        System.out.print("Enter landing name: ");
        String landing = scanner.nextLine();

        List<CatchViewDTO> catches = catchService.searchCatches(species, landing);

        if (catches.isEmpty()) {
            ConsoleUI.info("No catches found.");
            return;
        }

        ConsoleUI.header("Available " + species + " Catches for Landing: " + landing);
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }


    private void viewByFisher() {
        System.out.print("Enter fisher's username: ");
        String fisher = scanner.nextLine();

        List<CatchViewDTO> catches = catchService.getCatchesByFisherName(fisher);

        if (catches.isEmpty()) {
            ConsoleUI.info("No catches found for that fisher.");
            return;
        }

        ConsoleUI.header("Available Catches by Fisher: " + fisher);
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }

    private void viewMyPurchases(Person customer) {
        List<Order> orders = orderService.getOrdersForCustomer(customer.getUsername());

        if (orders.isEmpty()) {
            ConsoleUI.info("You have no purchases.");
            return;
        }

        ConsoleUI.header("Your Purchases:");
        for (Order o : orders) {
            System.out.printf("Order #%d | Date: %s | Status: %s\n",
                    o.getId(), o.getOrderDateTime(), o.getOrderStatus());

            for (OrderItem item : o.getOrderItems()) {
                System.out.printf("  - Catch ID: %d | Quantity: %.2f\n",
                        item.getId(), item.getQuantity());
            }
        }
    }
    private void printCatch(CatchViewDTO c) {
        System.out.printf("Catch ID: %d | Species: %s | %.2f kg | $%.2f/kg | Fisher: %s\n",
                c.getId(), c.getSpeciesName(), c.getQuantityInKg(), c.getPricePerKg(), c.getFisherName());
    }


}
