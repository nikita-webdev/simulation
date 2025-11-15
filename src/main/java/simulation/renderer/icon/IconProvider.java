package simulation.renderer.icon;

import simulation.entities.Entity;

public interface IconProvider {
    String getIcon(Entity entity);
    String getEmptyCellIcon();
}
