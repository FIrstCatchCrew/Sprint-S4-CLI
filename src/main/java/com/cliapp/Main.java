package com.cliapp;

import com.cliapp.client.RESTClient;
import com.cliapp.controller.AdminController;
import com.cliapp.controller.CustomerController;
import com.cliapp.controller.FisherController;
import com.cliapp.model.Person;
import com.cliapp.service.*;
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
        FisherService fisherService = new FisherService(restClient);
        OrderService orderService = new OrderService(restClient);
        AuthService authService = new AuthService(restClient);

        new Main().start(catchService, personService, orderService, fisherService, authService);

    }

    public void start(CatchService catchService, PersonService personService, OrderService orderService, FisherService fisherService, AuthService authService) {
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
                case 1 -> handleLogin(catchService, personService, authService, fisherService, orderService);
                case 2 -> System.out.println("Goodbye!");
                default -> ConsoleUI.error("Invalid option.");
            }
        } while (choice != 2);
    }


    private void handleLogin(CatchService catchService, PersonService personService, AuthService authService, FisherService fisherService, OrderService orderService) {
        Person user = loginUser(authService);
        if (user == null) {
            ConsoleUI.error("Login failed.");
            return;
        }

        switch (user.getRole()) {
            case "ADMIN" -> new AdminController(scanner, catchService, personService).run(user);
            case "FISHER" -> new FisherController(scanner, catchService, fisherService).run(user);
            case "CUSTOMER" -> new CustomerController(scanner, catchService, personService, orderService).run(user);
            default -> ConsoleUI.error("Unknown role.");
        }
    }

    private Person loginUser(AuthService authService) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        return authService.authenticate(email, password);
    }
}
