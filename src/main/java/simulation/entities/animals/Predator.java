package simulation.entities.animals;

import simulation.map.Cell;

import static simulation.config.Icons.PREDATOR_ICON;

public class Predator extends Creature {
    public Predator(Cell cell, String name) {
        super(cell, name);

//        hp = 2;
        icon = PREDATOR_ICON;
    }

//    void makeMove() {
//
//    }
}
