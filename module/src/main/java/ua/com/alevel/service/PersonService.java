package ua.com.alevel.service;

import ua.com.alevel.model.Customer;

import java.util.Random;
import java.util.UUID;

public final class PersonService {

    private static final int SZ = 5;
    private static final int ZERO = 0;
    private static final int MIN_LIMIT = 10;
    private static final int MAX_LIMIT = 30;
    private static final Random RANDOM = new Random();

    private PersonService() { }

    public static Customer createPerson() {
        String id = UUID.randomUUID().toString();
        String email = id.substring(ZERO, SZ) + "@mail.com";
        int age = RANDOM.nextInt(MIN_LIMIT, MAX_LIMIT);
        return new Customer(id, email, age);
    }
}
