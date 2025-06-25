package com.cliapp.service;

import com.cliapp.model.Person;

import java.util.List;

public class PersonService {
    private final List<Person> mockUsers = List.of(
            new Person(1L, "Jane", "fisher", "FISHER"),
            new Person(2L, "Brad", "customer", "CUSTOMER"),
            new Person(3L, "Drew", "admin", "ADMIN")
    );

    public Person authenticate(String email, String password) {
        return mockUsers.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
