package org.example.entities;

import org.example.gui.MessageUtils;

import java.util.Random;

public abstract class Spell implements Visitor<Entity> {
    private final String spellType;
    private final int damage;
    private final int manaCost;

    public Spell(String spellType, int damage, int manaCost) {
        if (damage < 0 || manaCost < 0) {
            throw new IllegalArgumentException("Damage and mana cost cannot be negative");
        }
        this.spellType = spellType;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public String getSpellType() {
        return spellType;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public void visit(Entity entity) {
        boolean isImmune = switch (spellType) {
            case "Fire" -> entity.isImmuneToFire();
            case "Ice" -> entity.isImmuneToIce();
            case "Earth" -> entity.isImmuneToEarth();
            default -> false;
        };

        if (isImmune) {
//            System.out.println("You are immune to " + spellType+ "!");
            MessageUtils.showMessage("You are immune to " + spellType + "!", "Immunity");
        } else {
            entity.receiveDamage(damage);
        }
    }

    public static Spell generateRandomSpell() {
        Random random = new Random();
        String[] spellTypes = {"Fire", "Ice", "Earth"};
        String type = spellTypes[random.nextInt(spellTypes.length)];
        int damage = 10 + random.nextInt(21); // random damage between 10 and 30
        int manaCost = 5 + random.nextInt(16); // random mana cost between 5 and 20

        return switch (type) {
            case "Fire" -> new Fire(damage, manaCost);
            case "Ice" -> new Ice(damage, manaCost);
            default -> new Earth(damage, manaCost);
        };
    }

    @Override
    public String toString() {
        return "Spell{" +
                "spell type='" + spellType + '\'' +
                "damage=" + damage +
                ", mana cost=" + manaCost +
                '}';
    }
}
