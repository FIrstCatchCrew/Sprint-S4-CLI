package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.FisherProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FisherServiceTest {

    private RESTClient mockClient;
    private FisherService fisherService;

    @BeforeEach
    void setUp() {
        mockClient = mock(RESTClient.class);
        fisherService = new FisherService(mockClient);
    }

    @Test
    void testGetFisherByIdSuccess() {
        FisherProfile fisher = new FisherProfile("fisher1", "ABC123", 42L);
        when(mockClient.getFisherById(42L)).thenReturn(fisher);

        FisherProfile result = fisherService.getFisherById(42L);

        assertNotNull(result);
        assertEquals("fisher1", result.getUserName());
        assertEquals("ABC123", result.getFishingLicenseNumber());
    }

    @Test
    void testGetFisherByIdFailure() {
        when(mockClient.getFisherById(999L)).thenThrow(new RuntimeException("Fisher not found"));

        FisherProfile result = fisherService.getFisherById(999L);

        assertNull(result);
    }

    @Test
    void testGetTodaysSalesByFisherId() {
        List<CatchViewDTO> mockData = List.of(
                new CatchViewDTO(3L, "Trout", new BigDecimal("8.0"), new BigDecimal("20.00"), false, "fisher1", "Port C") {{
                    setTimeStamp(LocalDateTime.now());
                }},
                new CatchViewDTO(2L, "Cod", new BigDecimal("5.0"), new BigDecimal("15.00"), false, "fisher1", "Port B") {{
                    setTimeStamp(LocalDateTime.now().minusDays(1));
                }}
        );

        when(mockClient.getSoldCatchesByFisherId(42L)).thenReturn(mockData);

        List<CatchViewDTO> todaySales = fisherService.getTodaysSalesByFisherId(42L);

        assertEquals(1, todaySales.size());
        assertEquals("Trout", todaySales.get(0).getSpeciesName());
    }

    @Test
    void testGetSoldCatchesByPort() {
        List<CatchViewDTO> mockData = List.of(
                new CatchViewDTO(1L, "Halibut", new BigDecimal("10.5"), new BigDecimal("25.00"), true, "fisher1", "Port A"),
                new CatchViewDTO(2L, "Cod", new BigDecimal("5.0"), new BigDecimal("15.00"), false, "fisher1", "Port B"),
                new CatchViewDTO(3L, "Trout", new BigDecimal("8.0"), new BigDecimal("20.00"), false, "fisher1", "")
        );

        when(mockClient.getSoldCatchesByFisherId(42L)).thenReturn(mockData);

        Map<String, List<CatchViewDTO>> grouped = fisherService.getSoldCatchesByPort(42L);

        assertTrue(grouped.containsKey("Port A"));
        assertTrue(grouped.containsKey("Port B"));
        assertTrue(grouped.containsKey("Unknown Port"));
    }
}
