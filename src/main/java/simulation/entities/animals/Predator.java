package simulation.entities.animals;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import static simulation.config.Icons.PREDATOR_ICON;

public class Predator extends Creature {
    public Predator(String name) {
        super(name);

        speed = 2;
//        hp = 2;
        icon = PREDATOR_ICON;
    }

//    void makeMove() {
//
//    }

    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        return simulationMap.isTreeOrRock(coordinate) || simulationMap.isGrass(coordinate);
    }
}
