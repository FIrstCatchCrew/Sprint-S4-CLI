package com.cliapp.controller;

import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;

import java.util.Scanner;

public class CustomerController {
    private final Scanner scanner;
    private final CatchService catchService;
    private final PersonService personService;

    public CustomerController(Scanner scanner, CatchService catchService, PersonService personService) {
        this.scanner = scanner;
        this.catchService = catchService;
        this.personService = personService;
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
}
