package com.cliapp.controller;

import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.PersonService;
import com.cliapp.util.ConsoleUI;

import java.util.List;
import java.util.Scanner;

public class FisherController {
    private final Scanner scanner;
    private final CatchService catchService;
    private final PersonService personService;

    public FisherController(Scanner scanner, CatchService catchService, PersonService personService) {
        this.scanner = scanner;
        this.catchService = catchService;
        this.personService = personService;
    }



    public void run(Person fisher) {
        while (true) {
            ConsoleUI.header("Fisher Menu for " + fisher.getUsername());
            System.out.print(fisher.getId()+ fisher.getUsername()+ fisher.getEmail()+ fisher.getRole());


            ConsoleUI.option("1", "Add New Catch", false);
            ConsoleUI.option("2", "View My Catches", true);
            ConsoleUI.option("3", "Update Catch Info", false);
            ConsoleUI.option("4", "Delete a Catch", false);
            ConsoleUI.option("5", "Q4: My Sales Today", true);
            ConsoleUI.option("0", "Logout", true);

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "2" -> viewMyCatches(fisher.getId());
                case "5" -> ConsoleUI.success("Q4: Total sales value today...");
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }

    private void viewMyCatches(long id) {
        List<CatchViewDTO> catchViewDTOS = catchService.getFisherCatchesById(id);
        ConsoleUI.header("Your Catches");
        if (catchViewDTOS.isEmpty()) {
            ConsoleUI.info("No catches found.");
            return;
        }

        for (CatchViewDTO c : catchViewDTOS) {
            System.out.printf("ID: %d | Species: %s | Qty: %.2fkg | Price: $%.2f | Available: %s\n",
                    c.getId(),
                    c.getSpecies(),
                    c.getQuantityInKg(),
                    c.getPricePerKg(),
                    c.isAvailable() ? "Yes" : "No"
            );
        }
    }

}
