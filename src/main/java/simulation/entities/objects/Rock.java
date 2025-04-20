package simulation.entities.objects;

import simulation.entities.Entity;

import static simulation.config.Icons.ROCK_ICON;

public class Rock extends Entity {
    public Rock(String name) {
        super(name);

        icon = ROCK_ICON;
    }
}
