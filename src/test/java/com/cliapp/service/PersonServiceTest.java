package com.cliapp.service;
import com.cliapp.client.RESTClient;
import com.cliapp.model.FisherProfile;
import com.cliapp.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    private RESTClient mockClient;
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        mockClient = mock(RESTClient.class);
        personService = new PersonService(mockClient);
    }

    @Test
    public void testGetAllFishersSuccess() {
        List<FisherProfile> fishers = List.of(
                new FisherProfile("fisher1", "LIC123", 1L),
                new FisherProfile("fisher2", "LIC456", 2L)
        );
        when(mockClient.getAllFishers()).thenReturn(fishers);

        List<FisherProfile> result = personService.getAllFishers();

        assertEquals(2, result.size());
        assertEquals("fisher1", result.get(0).getUserName());
    }

    @Test
    public void testGetAllFishersFailure() {
        when(mockClient.getAllFishers()).thenThrow(new RuntimeException("API down"));

        List<FisherProfile> result = personService.getAllFishers();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllByRoleTypeSuccess() {
        List<Person> buyers = List.of(
                new Person(1L, "buyer1", "buyer1@mail.com", "CUSTOMER"),
                new Person(2L, "buyer2", "buyer2@mail.com", "CUSTOMER")
        );
        when(mockClient.getAllByRoleType("CUSTOMER")).thenReturn(buyers);

        List<Person> result = personService.getAllByRoleType("CUSTOMER");

        assertEquals(2, result.size());
        assertEquals("buyer1", result.get(0).getUsername());
    }

    @Test
    public void testGetAllByRoleTypeFailure() {
        when(mockClient.getAllByRoleType("FISHER")).thenThrow(new RuntimeException("DB error"));

        List<Person> result = personService.getAllByRoleType("FISHER");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUserByIdSuccess() {
        Person user = new Person(10L, "admin", "admin@mail.com", "ADMIN");
        when(mockClient.getUserById(10L)).thenReturn(user);

        Person result = personService.getUserById(10L);

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    public void testGetUserByIdFailure() {
        when(mockClient.getUserById(999L)).thenThrow(new RuntimeException("Not found"));

        Person result = personService.getUserById(999L);

        assertNull(result);
    }

    @Test
    public void testGetUserByUsernameSuccess() {
        Person user = new Person(20L, "fishy", "fishy@mail.com", "FISHER");
        when(mockClient.getUserByUsername("fishy")).thenReturn(user);

        Person result = personService.getUserByUsername("fishy");

        assertNotNull(result);
        assertEquals("fishy", result.getUsername());
    }

    @Test
    public void testGetUserByUsernameFailure() {
        when(mockClient.getUserByUsername("ghost")).thenThrow(new RuntimeException("Gone"));

        Person result = personService.getUserByUsername("ghost");

        assertNull(result);
    }
}
