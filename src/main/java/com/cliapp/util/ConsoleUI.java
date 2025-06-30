package com.cliapp.util;
/**
 * Description: CLI utility class for printing styled text to the terminal.
 *              Helps create a clean UI for headers, prompts, errors, and options.
 */

public class ConsoleUI {

    // ANSI escape codes for color formatting
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    public static final String BOLD = "\u001B[1m";

    /**
     * Prints a stylized header message in cyan.
     *
     * @param message the header text
     */
    public static void header(String message) {
        System.out.println(BOLD + CYAN + "\n=== " + message + " ===" + RESET);
    }

    /**
     * Prints a success message in green.
     *
     * @param message the success message
     */
    public static void success(String message) {
        System.out.println(GREEN + "[SUCCESS] " + message + RESET);
    }

    /**
     * Prints an error message in red.
     *
     * @param message the error message
     */
    public static void error(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    /**
     * Prints an informational message in yellow.
     *
     * @param message the info message
     */
    public static void info(String message) {
        System.out.println(YELLOW + "[INFO] " + message + RESET);
    }

    /**
     * Prints a raw message with no styling. Useful when toggling color off.
     *
     * @param message the plain message
     */
    public static void raw(String message) {
        System.out.println(message);
    }

    public static void option(String num, String label, boolean active) {
        String color = active ? GREEN : RED;
        System.out.println(color + "[" + num + "] " + label + RESET);

    }
    public static void divider() {
        System.out.println(CYAN + "------------------------" + RESET);
    }
}