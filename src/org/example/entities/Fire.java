package org.example.entities;

public class Fire extends Spell {
    public Fire(int damage, int manaCost) {
        super("Fire", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
    }
}
