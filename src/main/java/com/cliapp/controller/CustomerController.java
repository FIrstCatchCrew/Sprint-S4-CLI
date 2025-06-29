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
            ConsoleUI.option("1", "View Available Catches", false);
            ConsoleUI.option("2", "Search by Species", true);
            ConsoleUI.option("3", "Search by Port", false);
            ConsoleUI.option("4", "Search by Fisher", true); // Q2
            ConsoleUI.option("5", "Purchase Catch", false);
            ConsoleUI.option("6", "View My Purchases", true); // Q3
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "4" -> ConsoleUI.success("Q2: Search species by fisher...");
                case "6" -> ConsoleUI.success("Q3: View orders...");
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }

    private void searchBySpecies() {
        System.out.print("Enter species name: ");
        String species = scanner.nextLine();

        List<CatchViewDTO> catches = catchService.getCatchesBySpecies(species);

        if (catches.isEmpty()) {
            ConsoleUI.info("No catches found for that species.");
            return;
        }

        ConsoleUI.header("Available Catches for Species: " + species);
        for (CatchViewDTO c : catches) {
            printCatch(c);
        }
    }

    private void searchByFisher() {
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
                    o.getOrderId(), o.getOrderDateTime(), o.getOrderStatus());

            for (OrderItem item : o.getOrderItems()) {
                System.out.printf("  - Catch ID: %d | Quantity: %d\n",
                        item.getCatchId(), item.getQuantity());
            }
        }
    }
    private void printCatch(CatchViewDTO c) {
        System.out.printf("Catch ID: %d | Species: %s | %.2f kg | $%.2f/kg | Fisher: %s\n",
                c.getId(), c.getSpecies(), c.getQuantityInKg(), c.getPricePerKg(), c.getFisherName());
    }


}
