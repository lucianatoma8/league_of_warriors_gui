package org.example.entities.characters;

import org.example.gui.MessageUtils;

public class Rogue extends Character {

    public Rogue(String name, int experience, int level) {
        super(name, "Rogue", experience, level,
                10 + level,
                5 + level,
                15 + level * 2,
                false, false, true);
    }

    @Override
    protected void scaleAttributes() {
        setStrength(getStrength() + 1);
        setCharisma(getCharisma() + 1);
        setDexterity(getDexterity() + 3);
    }

    @Override
    public String getImmunityType() {
        return "Earth";
    }

    @Override
    public int calculateDamage() {
        return (int) (getDexterity() * 1.5 + Math.random() * getStrength());
    }

    @Override
    public void receiveDamage(int damage) {
        if (isPlayer()) {
            if (Math.random() < 0.5) {
                MessageUtils.showMessage("Rogue's dexterity dodged the attack!", "Dexterity");
//                System.out.println("Rogue's dexterity dodged the attack!");
                return;
            }
        }
        super.receiveDamage(damage);
    }
}
