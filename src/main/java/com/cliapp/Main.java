package com.cliapp;

import com.cliapp.client.RESTClient;
import com.cliapp.controller.AdminController;
import com.cliapp.controller.CustomerController;
import com.cliapp.controller.FisherController;
import com.cliapp.model.Order;
import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.OrderService;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Init REST client
        RESTClient restClient = new RESTClient();
        restClient.setServerURL("http://localhost:8080/api");

        // Init services
        CatchService catchService = new CatchService(restClient);
        PersonService personService = new PersonService(restClient);
        OrderService orderService = new OrderService(restClient);

        new Main().start(catchService, personService, orderService);

    }

    public void start(CatchService catchService, PersonService personService, OrderService orderService) {
        ConsoleUI.header("=== FIRST CATCH CLI ===");

        int choice;
        do {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            switch (choice) {
                case 1 -> handleLogin(catchService, personService);
                case 2 -> System.out.println("Goodbye!");
                default -> ConsoleUI.error("Invalid option.");
            }
        } while (choice != 2);
    }


    private void handleLogin(CatchService catchService, PersonService personService) {
        Person user = loginUser(personService);
        if (user == null) {
            ConsoleUI.error("Login failed.");
            return;
        }

        switch (user.getRole()) {
            case "ADMIN" -> new AdminController(scanner, catchService, personService,orderService).run(user);
            case "FISHER" -> new FisherController(scanner, catchService, personService, orderService).run(user);
            case "CUSTOMER" -> new CustomerController(scanner, catchService, personService, orderService).run(user);
            default -> ConsoleUI.error("Unknown role.");
        }
    }

    private Person loginUser(PersonService personService) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        return personService.authenticate(email, password);
    }
}
