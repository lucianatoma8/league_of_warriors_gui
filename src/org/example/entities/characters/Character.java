package org.example.entities.characters;

import org.example.entities.Entity;
import org.example.entities.Spell;
import org.example.gui.MessageUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public abstract class Character extends Entity {
    private final String name;
    private final String profession;
    private int experience;
    private int level;

    private int strength;
    private int charisma;
    private int dexterity;

    private int killedEnemies = 0;

    private final List<Spell> abilities;

    public Character(String name, String profession, int experience, int level, int strength, int charisma, int dexterity,
                     boolean immuneToFire, boolean immuneToIce, boolean immuneToEarth) {
        super(100 + level * 10, 50 + level * 5, immuneToFire, immuneToIce, immuneToEarth, true);
        this.name = name;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;

        // scale attributes based on level
        scaleAttributes();
        this.abilities = generateRandomAbilities();
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void resetKilledEnemies() {
        killedEnemies = 0;
    }

    public void increaseKilledEnemies() {
        killedEnemies++;
    }

    private List<Spell> generateRandomAbilities() {
        Random random = new Random();
        int numberOfSpells = 3 + random.nextInt(4);
        List<Spell> spellList = new ArrayList<>();
        for (int i = 0; i < numberOfSpells; i++) {
            spellList.add(Spell.generateRandomSpell());
        }
        return spellList;
    }

    public List<Spell> getAbilities() {
        return new ArrayList<>(abilities);
    }

    public void useAbility(Spell spell, Entity target) {
        if (!abilities.contains(spell)) {
//            System.out.println("Ability not found in abilities list!");
            MessageUtils.showMessage("Ability not found in abilities list!", "Error");
            return;
        }

        if (getCurrentMana() < spell.getManaCost()) {
//            System.out.println("Not enough mana to use " + spell.getSpellType() + "!");
            MessageUtils.showMessage("Not enough mana to use " + spell.getSpellType() + "!", "Error");
            return;
        }

        // reduce mana and apply spell effects
        setCurrentMana(getCurrentMana() - spell.getManaCost());
        boolean isImmune = switch (spell.getSpellType()) {
            case "Fire" -> target.isImmuneToFire();
            case "Ice" -> target.isImmuneToIce();
            case "Earth" -> target.isImmuneToEarth();
            default -> false;
        };

        if (isImmune) {
//            System.out.println("Enemy is immune to " + spell.getSpellType() + "!");
            MessageUtils.showMessage("Enemy is immune to " + spell.getSpellType() + "!", "Immunity");
        } else {
            target.receiveDamage(spell.getDamage());
        }

        // remove used spell from the list
        abilities.remove(spell);
    }

    protected abstract void scaleAttributes();

    public abstract String getImmunityType();

    public abstract int calculateDamage();

    public void levelUp() {
        while (experience >= getLevelUpThresHold()) {
            experience -= getLevelUpThresHold();
            level++;
            scaleAttributes();

            setMaxHealth(100 + level * 10);
            setMaxMana(50 + level * 5);

            regenerateHealth(getMaxHealth());
            regenerateMana(getMaxMana());

            updateAbilities();

//            System.out.println("Congratulations! " + name + " leveled up to level " + level + "!");
            MessageUtils.showMessage("Congratulations! " + name + " leveled up to level " + level + "!", "Level Up");
        }
    }

    public void updateAbilities() {
        Random random = new Random();

        // at least 3 abilities
        while (abilities.size() < 3) {
            abilities.add(Spell.generateRandomSpell());
        }

        // remove extra abilities if more than 6
        while (abilities.size() > 6) {
            abilities.remove(random.nextInt(abilities.size()));
        }
    }

    public int getLevelUpThresHold() {
        return level * 50;
    }

    public void addExperience(int amount) {
        this.experience += amount;
        levelUp();
    }

    public Spell getAbilityByName(String name) {
        for (Spell ability : abilities) {
            if (ability.getSpellType().equals(name)) {
                return ability;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    protected void setStrength(int strength) {
        this.strength = strength;
    }

    public int getCharisma() {
        return charisma;
    }

    protected void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    protected void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", experience=" + experience +
                ", level=" + level +
                ", immuneToFire=" + isImmuneToFire() +
                ", immuneToIce=" + isImmuneToIce() +
                ", immuneToEarth=" + isImmuneToEarth() +
                ", strength=" + strength +
                ", charisma=" + charisma +
                ", dexterity=" + dexterity +
                ", health=" + getCurrentHealth() + "/" + getMaxHealth() +
                ", mana=" + getCurrentMana() + "/" + getMaxMana() +
                ", abilities=" + abilities +
                '}';
    }
}
