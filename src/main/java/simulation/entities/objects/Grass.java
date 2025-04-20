package simulation.entities.objects;

import simulation.entities.Entity;

import static simulation.config.Icons.GRASS_ICON;

public class Grass extends Entity {
    public Grass(String name) {
        super(name);

        icon = GRASS_ICON;
    }
}
