package org.example.entities;

public class Earth extends Spell {
    public Earth(int damage, int manaCost) {
        super("Earth", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
    }
}
