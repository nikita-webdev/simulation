package simulation.animals;

import simulation.Entity;

public abstract class Creature extends Entity {
    int speed;
    int hp;

    public Creature(String name, int positionX, int positionY) {
        super(name, positionY, positionX);
    }

//    void makeMove() {
//
//    }
}
