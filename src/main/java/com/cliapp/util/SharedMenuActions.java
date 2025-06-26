package com.cliapp.util;

import com.cliapp.model.Catch;
import com.cliapp.service.CatchService;

import java.util.List;
import java.util.Scanner;


/**
 * SharedMenuActions is responsible for handling and displaying shared information
 * related to fishing catches. It leverages the CatchService to fetch
 * necessary data and facilitates user interaction through console input and output.
 *
 * This class provides methods for:
 * - Listing all available catches.
 * - Filtering and displaying catches by species.
 *
 * Constructor Dependency:
 * - Requires an instance of CatchService to interact with backend or data client.
 */
public class SharedMenuActions {
    private final CatchService catchService;

    public SharedMenuActions(CatchService catchService) {
        this.catchService = catchService;
    }

    public void viewAvailableCatches() {
        List<Catch> catches = catchService.getAvailableCatches();
        catches.forEach(System.out::println);  // Eventually format nicer
    }

    public void viewCatchesBySpecies(Scanner scanner) {
        System.out.print("Enter species name: ");
        String species = scanner.nextLine();
        List<Catch> catches = catchService.getCatchesBySpecies(species);
        catches.forEach(System.out::println);
    }
}
