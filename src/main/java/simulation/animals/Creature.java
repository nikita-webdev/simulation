package simulation.animals;

import simulation.Entity;

public abstract class Creature extends Entity {
    int speed;
    int hp;

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    // Наверное, лучше получать значения через геттер и устанавливать через сеттер
    public void makeMove() {
        if (positionX < 39) {
            positionX += 1;
        }
    }
}
