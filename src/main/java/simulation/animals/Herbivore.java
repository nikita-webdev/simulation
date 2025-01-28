package simulation.animals;

public class Herbivore extends Creature {
    // Стремятся найти ресурс (траву), может потратить свой ход на движение в сторону травы, либо на её поглощение.

    public Herbivore(String name, int positionX, int positionY) {
        super(name, positionY, positionX);
//
//        speed = 1;
//        hp = 2;
        icon = " \uD83D\uDC11";
    }
}
