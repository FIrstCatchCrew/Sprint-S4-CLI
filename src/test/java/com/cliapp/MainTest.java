package com.cliapp;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void testMainOpensWithHeader() {
        // Arrange: simulate user entering "3" to exit
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Main app = new Main();
            app.start();

            String output = outContent.toString();

            // Assert: check header printed
            assertTrue(output.contains("FIRST CATCH"));

        } finally {
            // Clean up streams
            System.setOut(originalOut);
        }
    }
}
