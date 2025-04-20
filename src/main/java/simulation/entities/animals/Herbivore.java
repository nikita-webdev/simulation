package simulation.entities.animals;

import simulation.map.Cell;

import static simulation.config.Icons.HERBIVORE_ICON;

public class Herbivore extends Creature {
    public Herbivore(Cell cell, String name) {
        super(cell, name);

//        hp = 2;
        icon = HERBIVORE_ICON;
    }

//    void makeMove() {
//
//    }
}
