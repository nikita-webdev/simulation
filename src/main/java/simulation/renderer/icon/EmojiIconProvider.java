package simulation.renderer.icon;

import simulation.entities.Entity;

public class EmojiIconProvider implements IconProvider {
    @Override
    public String getIcon(Entity entity) {
        return switch (entity.getEntityType()) {
            case GRASS -> "\uD83C\uDF3E";
            case ROCK -> "\uD83C\uDFD4️";
            case TREE -> "\uD83C\uDF33";
            case HERBIVORE -> "\uD83D\uDC11";
            case PREDATOR -> "\uD83D\uDC06";
        };
    }

    @Override
    public String getEmptyCellIcon() {
        return "⬛";
    }
}
