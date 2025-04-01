package org.example.entities;

public class Ice extends Spell {
    public Ice(int damage, int manaCost) {
        super("Ice", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
    }
}
