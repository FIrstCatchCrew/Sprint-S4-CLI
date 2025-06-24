package com.cliapp;

import com.cliapp.controller.AdminController;
import com.cliapp.controller.CustomerController;
import com.cliapp.controller.FisherController;
import com.cliapp.model.Person;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonService personService = new PersonService();

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
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
                case 1 -> handleLogin();
                case 2 -> System.out.println("Goodbye!");
                default -> ConsoleUI.error("Invalid option.");
            }
        } while (choice != 2);
    }

    private void handleLogin() {
        Person user = loginUser();
        if (user == null) {
            ConsoleUI.error("Login failed.");
            return;
        }

        switch (user.getRole()) {
            case "ADMIN" -> new AdminController(scanner).run(user);
            case "FISHER" -> new FisherController(scanner).run(user);
            case "CUSTOMER" -> new CustomerController(scanner).run(user);
            default -> ConsoleUI.error("Unknown role.");
        }
    }

    private Person loginUser() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        return personService.authenticate(email, password);
    }
}
