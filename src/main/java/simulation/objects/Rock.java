package simulation.objects;

import simulation.Entity;

public class Rock extends Entity {
    public Rock(String name, int positionX, int positionY) {
        super(name, positionY, positionX);

        icon = " \uD83C\uDFD4\uFE0F";
    }
}
