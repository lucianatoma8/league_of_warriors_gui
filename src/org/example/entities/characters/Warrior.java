package org.example.entities.characters;

import org.example.gui.MessageUtils;

public class Warrior extends Character {

    public Warrior(String name, int experience, int level) {
        super(name, "Warrior", experience, level,
                15 + level * 2,
                5 + level,
                10 + level,
                true, false, false);
    }

    @Override
    protected void scaleAttributes() {
        setStrength(getStrength() + 3);
        setCharisma(getCharisma() + 1);
        setDexterity(getDexterity() + 1);
    }

    @Override
    public String getImmunityType() {
        return "Fire";
    }

    @Override
    public int calculateDamage() {
        return (int) (getStrength() * 1.5 + Math.random() * getDexterity());
    }

    @Override
    public void receiveDamage(int damage) {
        if (isPlayer()) {
            if (Math.random() < 0.5) {
                damage *= 2;
                MessageUtils.showMessage("Warrior's strength caused double damage taken!", "Strength");
//                System.out.println("Warrior's strength caused double damage taken!");
            }
        }

        super.receiveDamage(damage);
    }
}
