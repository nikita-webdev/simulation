package simulation.entities.objects;

import simulation.entities.Entity;

import static simulation.config.Icons.ROCK_ICON;

public class Rock extends Entity {
    public Rock(String name, int positionX, int positionY) {
        super(name, positionX, positionY);

        icon = ROCK_ICON;
        groupName = "rock";
    }
}
