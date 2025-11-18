package simulation.renderer.icon;

import simulation.entities.Entity;

public final class AsciiIconProvider implements IconProvider {
    @Override
    public String getIcon(Entity entity) {
        return switch (entity.getEntityType()) {
            case GRASS -> "g ";
            case ROCK -> "r ";
            case TREE -> "t ";
            case HERBIVORE -> "H ";
            case PREDATOR -> "P ";
        };
    }

    @Override
    public String getEmptyCellIcon() {
        return ". ";
    }
}
