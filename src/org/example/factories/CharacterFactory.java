package org.example.factories;

import org.example.entities.characters.Character;
import org.example.entities.characters.Mage;
import org.example.entities.characters.Rogue;
import org.example.entities.characters.Warrior;

public class CharacterFactory {
    public static Character createCharacter(String profession, String name, int experience, int level) {
        return switch (profession) {
            case "Warrior" -> new Warrior(name, experience, level);
            case "Mage" -> new Mage(name, experience, level);
            case "Rogue" -> new Rogue(name, experience, level);
            default -> throw new IllegalArgumentException("Unknown character profession: " + profession);
        };
    }
}
