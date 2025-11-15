package simulation.renderer.icon;

import simulation.entities.Entity;

public class AsciiIconProvider implements IconProvider {
    public String getIcon(Entity entity) {
        return switch (entity.getEntityType()) {
            case GRASS -> "g";
            case TREE -> "t";
            case ROCK -> "r";
            case HERBIVORE -> "H";
            case PREDATOR -> "P";
        };
    }

    @Override
    public String getEmptyCellIcon() {
        return ".";
    }
}
