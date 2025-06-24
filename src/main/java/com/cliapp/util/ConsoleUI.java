package com.cliapp.util;

/**
 * ConsoleUI is a simple utility class designed to standardize and enhance
 * command-line interface (CLI) output for text-based Java applications.
 *
 * <p>
 * It provides methods to print styled messages such as headers, success messages,
 * errors, and general information using ANSI escape codes for color and formatting.
 * </p>
 *
 * <h2>Why Use This?</h2>
 * <ul>
 *     <li>Improves readability and consistency of CLI output</li>
 *     <li>Makes it easier to distinguish between sections (e.g., errors vs. headers)</li>
 *     <li>Centralizes formatting logic, making global style updates easy</li>
 * </ul>
 *
 * <h2>How to Use</h2>
 * Simply call the desired static method:
 * <pre>
 *     ConsoleUI.header("Login Menu");
 *     ConsoleUI.success("Login successful");
 *     ConsoleUI.error("Invalid password");
 *     ConsoleUI.info("Loading...");
 * </pre>
 *
 * <p>
 * Works on most Unix-based terminals (Mac, Linux, modern Windows terminals).
 * For portability, consider adding a `useColor` flag or terminal capability detection.
 * </p>
 *
 * @author YourName
 * @version 1.0
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