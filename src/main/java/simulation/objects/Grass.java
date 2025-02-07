package simulation.objects;

import simulation.Entity;

public class Grass extends Entity {
    public Grass(String name, int positionX, int positionY) {
        super(name, positionX, positionY);

        icon = "\uD83C\uDF3E";
    }
}
