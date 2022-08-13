package ua.com.alevel.model;

import lombok.Getter;

@Getter
public class Customer {

    private final String id;
    private String email;
    private final int age;

    public Customer(String id, String email, int age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
