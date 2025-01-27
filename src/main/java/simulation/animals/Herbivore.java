package simulation.animals;

public class Herbivore extends Creature {
    // Стремятся найти ресурс (траву), может потратить свой ход на движение в сторону травы, либо на её поглощение.
    String name = "H";

    public Herbivore(String name, int speed, int hp, int positionX, int positionY) {
        super(name, speed, hp, positionX, positionY);
        this.name = name;
    }
}
