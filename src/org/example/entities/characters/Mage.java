package org.example.entities.characters;

import org.example.gui.MessageUtils;

import java.security.MessageDigest;

public class Mage extends Character {

    public Mage(String name, int experience, int level) {
        super(name, "Mage", experience, level,
                5 + level,
                15 + level * 2,
                10 + level,
                false, true, false);
    }

    @Override
    protected void scaleAttributes() {
        setStrength(getStrength() + 1);
        setCharisma(getCharisma() + 3);
        setDexterity(getDexterity() + 1);
    }

    @Override
    public String getImmunityType() {
        return "Ice";
    }

    @Override
    public int calculateDamage() {
        return (int) (getCharisma() * 1.5 + Math.random() * getDexterity());
    }

    @Override
    public void receiveDamage(int damage) {
        if (isPlayer()) {
            if (Math.random() < 0.5) {
                damage /= 2;
                MessageUtils.showMessage("Mage's charisma reduced damage by 50%!", "Charisma");
//                System.out.println("Mage's charisma reduced damage by 50%!");
            }
        }
        super.receiveDamage(damage);
    }
}
