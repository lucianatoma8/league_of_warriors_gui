package org.example.entities;

public interface Element <T extends Entity> {
    void accept(Visitor<T> visitor);
}
