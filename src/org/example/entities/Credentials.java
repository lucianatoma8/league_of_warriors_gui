package org.example.entities;

public class Credentials {
    private final String email;
    private final String password;

    public Credentials(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public boolean validate(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "email='" + email + '\'' +
                ", password='**********'" +
                '}';
    }
}