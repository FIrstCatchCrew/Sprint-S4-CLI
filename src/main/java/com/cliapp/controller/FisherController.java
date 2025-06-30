/**
 * Description: CLI controller for Fisher users.
 *              Allows fishers to manage their catches and view sales data.
 */


package com.cliapp.controller;

import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.Person;
import com.cliapp.service.CatchService;
import com.cliapp.service.FisherService;
import com.cliapp.util.ConsoleUI;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FisherController {
    private final Scanner scanner;
    private final CatchService catchService;
    private final FisherService fisherService;


    public FisherController(Scanner scanner, CatchService catchService, FisherService fisherService) {
        this.scanner = scanner;
        this.catchService = catchService;
        this.fisherService = fisherService;
    }



    public void run(Person fisher) {
        while (true) {
            ConsoleUI.header("Fisher Menu for " + fisher.getUsername());

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
                case "5" -> displaySalesToday(fisher);
                case "0" -> {
                    return;
                }
                default -> ConsoleUI.error("Not yet implemented.");
            }
        }
    }

    private void viewMyCatches(long id) {
        List<CatchViewDTO> catchViewDTOS = catchService.getCatchesByFisherId(id);
        ConsoleUI.header("Your Catches");
        if (catchViewDTOS.isEmpty()) {
            ConsoleUI.info("No catches found.");
            return;
        }

        for (CatchViewDTO c : catchViewDTOS) {
            System.out.printf("ID: %d | Species: %s | Qty: %.2fkg | Price: $%.2f | Available: %s\n",
                    c.getId(),
                    c.getSpeciesName(),
                    c.getQuantityInKg(),
                    c.getPricePerKg(),
                    c.isAvailable() ? "Yes" : "No"
            );
        }
    }

    private void displaySalesToday(Person fisher) {
            List<CatchViewDTO> soldCatches = fisherService.getTodaysSalesByFisherId(fisher.getId());

            if (soldCatches.isEmpty()) {
                ConsoleUI.info("You have no sales today.");
                return;
            }

            LocalDate today = LocalDate.now();
            BigDecimal total = BigDecimal.ZERO;

            System.out.println("\n=== Sales for Today (" + today + ") ===");

            for (CatchViewDTO c : soldCatches) {
                BigDecimal lineTotal = c.getPricePerKg().multiply(c.getQuantityInKg());

                total = total.add(lineTotal);

                System.out.printf("Sold: %s | %.2f kg | $%.2f/kg | Line: $%.2f\n",
                        c.getSpeciesName(),
                        c.getQuantityInKg(),
                        c.getPricePerKg(),
                        lineTotal
                );
            }

            int count = soldCatches.size();
            BigDecimal avg = (count > 0)
                    ? total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            System.out.printf("\nSummary: %d sales | Total: $%.2f | Avg per sale: $%.2f\n", count, total, avg);
        }

}
