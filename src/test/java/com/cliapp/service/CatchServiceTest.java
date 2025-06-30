package com.cliapp.service;
import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CatchServiceTest {

    private RESTClient client;
    private CatchService service;

    private final CatchViewDTO sampleCatch = new CatchViewDTO(
            1, "Halibut",
            BigDecimal.valueOf(10), BigDecimal.valueOf(25), true, "fisher1",
             "st. John's");

    @BeforeEach
    public void setup() {
        client = mock(RESTClient.class);
        service = new CatchService(client);
    }

    @Test
    public void testGetAll_success() {
        when(client.getList(eq("/catch"), any())).thenReturn(List.of(sampleCatch));

        List<CatchViewDTO> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("Halibut", result.get(0).getSpeciesName());
    }

    @Test
    public void testGetAll_failure() {
        when(client.getList(eq("/catch"), any())).thenThrow(new RuntimeException("fail"));

        List<CatchViewDTO> result = service.getAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAvailableCatches_success() {
        when(client.getAvailableCatches()).thenReturn(List.of(sampleCatch));

        List<CatchViewDTO> result = service.getAvailableCatches();
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchBySpeciesName_success() {
        when(client.searchCatches(eq("Halibut"), isNull())).thenReturn(List.of(sampleCatch));

        List<CatchViewDTO> result = service.searchBySpeciesName("Halibut");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchByLandingName_failure() {
        when(client.searchCatches(isNull(), eq("St. John’s"))).thenThrow(new RuntimeException("No connection"));

        List<CatchViewDTO> result = service.searchByLandingName("St. John’s");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCatchesByFisherId_success() {
        when(client.getCatchesByFisherId(42L)).thenReturn(List.of(sampleCatch));

        List<CatchViewDTO> result = service.getCatchesByFisherId(42L);
        assertEquals("Halibut", result.get(0).getSpeciesName());
    }

    @Test
    public void testGetCatchCountBySpecies() {
        when(client.searchCatches(eq("Halibut"), isNull())).thenReturn(List.of(sampleCatch, sampleCatch));

        Map<String, Long> count = service.getCatchCountBySpecies("Halibut");
        assertEquals(1, count.size());
        assertEquals(2, count.get("Halibut"));
    }
}