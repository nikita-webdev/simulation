package simulation.entities.animals;

import simulation.map.Cell;

public class Herbivore extends Creature {
    // Стремятся найти ресурс (траву), может потратить свой ход на движение в сторону травы, либо на её поглощение.

    public Herbivore(Cell cell, String name) {
        super(cell, name);

        this.groupName = "herbivore";
//        hp = 2;
        icon = "\uD83D\uDC11";
    }

//    void makeMove() {
//
//    }
}
