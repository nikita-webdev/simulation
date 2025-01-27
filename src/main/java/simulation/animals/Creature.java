package simulation.animals;

import simulation.Entity;

public abstract class Creature extends Entity {
    int speed;
    int hp;

    public Creature(String name, int speed, int hp, int positionX, int positionY) {
        super(name, positionX, positionY);
        this.speed = speed;
        this.hp = hp;
    }

    void makeMove() {

    }
}
