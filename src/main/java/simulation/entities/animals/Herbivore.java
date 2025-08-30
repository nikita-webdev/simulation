package simulation.entities.animals;

import simulation.map.Coordinate;

import static simulation.config.Icons.HERBIVORE_ICON;

public class Herbivore extends Creature {
    public Herbivore(Coordinate cell, String name) {
        super(cell, name);

        hp = 1;
        icon = HERBIVORE_ICON;
    }

//    void makeMove() {
//
//    }
}
