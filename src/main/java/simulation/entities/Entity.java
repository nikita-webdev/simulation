package simulation.entities;

import simulation.renderer.EntityType;

public abstract class Entity {
    private final EntityType entityType;
    private final String name;

    public Entity(EntityType entityType, String name) {
        this.entityType = entityType;
        this.name = name;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return name;
    }
}
