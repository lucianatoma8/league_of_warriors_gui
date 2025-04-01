package org.example.entities;

import org.example.gui.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.Iterator;

public class Enemy extends Entity {
    private final int normalAttackDamage;
    private final List<Spell> abilities;

    public Enemy(int maxHealth, int maxMana) {
        super(maxHealth, maxMana,
                randomBoolean(), // Fire immunity
                randomBoolean(), // Ice immunity
                randomBoolean(), // Earth immunity
                false);

        // random normal attack damage
        this.normalAttackDamage = 10 + new Random().nextInt(6);

        // 3-6 random abilities
        this.abilities = generateRandomSpells(3 + new Random().nextInt(4));
        for (Spell ability : abilities) {
            addAbility(ability);
        }
    }

    private static boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    // random list of spells
    private List<Spell> generateRandomSpells(int count) {
        List<Spell> spells = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String spellType = switch (random.nextInt(3)) {
                case 0 -> "Fire";
                case 1 -> "Ice";
                default -> "Earth";
            };

            int damage = 10 + random.nextInt(21); // damage between 10 and 30
            int manaCost = 10 + random.nextInt(11); // mana cost between 10 and 20

            spells.add(new Spell(spellType, damage, manaCost) {});
        }

        return spells;
    }

    public int performAttack(Entity target) {
        Collections.shuffle(abilities);

        Iterator<Spell> iterator = abilities.iterator();
        while (iterator.hasNext()) {
            Spell ability = iterator.next();

            if (getCurrentMana() >= ability.getManaCost()) {
                MessageUtils.showMessage("Enemy used " + ability.getSpellType() + "!", "Enemy Ability");

                useAbility(ability, target);
                iterator.remove();
                return ability.getDamage();
            }
        }

        // if no abilities can be used, perform a normal attack
        int damage = getDamage();
        target.receiveDamage(damage);
        return damage;
    }

    @Override
    public void receiveDamage(int damage) {
        // 50% chance to evade the damage
        if (new Random().nextBoolean()) {
//            System.out.println("Enemy evaded the attack!");
            MessageUtils.showMessage("Enemy evaded the attack!", "Evade");
        } else {
            super.receiveDamage(damage);
        }
    }

    @Override
    public int getDamage() {
        // 50% chance to deal double damage
        if (new Random().nextBoolean()) {
//            System.out.println("Enemy dealt double damage!");
            MessageUtils.showMessage("Enemy dealt double damage!", "Critical Hit");
            return normalAttackDamage * 2;
        } else {
            return normalAttackDamage;
        }
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "normalAttackDamage=" + normalAttackDamage +
                ", currentHealth=" + getCurrentHealth() +
                ", maxHealth=" + getMaxHealth() +
                ", currentMana=" + getCurrentMana() +
                ", maxMana=" + getMaxMana() +
                ", immuneToFire=" + isImmuneToFire() +
                ", immuneToIce=" + isImmuneToIce() +
                ", immuneToEarth=" + isImmuneToEarth() +
                ", abilities=" + getAbilities() +
                '}';
    }
}
