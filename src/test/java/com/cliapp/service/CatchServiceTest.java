package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Catch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CatchServiceTest {

    @Test
    void getAvailableCatches_returnsListFromClient() {
        // Arrange
        RESTClient mockClient = mock(RESTClient.class);
        CatchService catchService = new CatchService(mockClient);

        Catch mockCatch = new Catch("cod", 2, 5, "here", true);  // populate fields if needed
        when(mockClient.getAvailableCatches()).thenReturn(List.of(mockCatch));

        // Act
        List<Catch> result = catchService.getAvailableCatches();

        // Assert
        assertEquals(1, result.size());
        assertSame(mockCatch, result.getFirst());
        verify(mockClient).getAvailableCatches();
    }

    @Test
    void getAvailableCatches_returnsEmptyList_whenClientThrowsException() {
        // Arrange
        RESTClient mockClient = mock(RESTClient.class);
        CatchService catchService = new CatchService(mockClient);

        when(mockClient.getAvailableCatches()).thenThrow(new RuntimeException("Server error"));

        // Act
        List<Catch> result = catchService.getAvailableCatches();

        // Assert
        assertTrue(result.isEmpty(), "Expected empty list when client throws");
        verify(mockClient).getAvailableCatches();
    }

    @Test
    void getCatchesBySpecies() {
    }

    @Test
    void getCatchesByFisher() {
    }

    @Test
    void getSpeciesAvailableAtLanding() {
    }
}