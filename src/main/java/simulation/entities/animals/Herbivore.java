package simulation.entities.animals;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import static simulation.config.Icons.HERBIVORE_ICON;

public class Herbivore extends Creature {
    public Herbivore(String name) {
        super(name);

        this.setHp(100);
        this.setSpeed(1);

        icon = HERBIVORE_ICON;
    }

    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        return simulationMap.isTreeOrRock(coordinate);
    }
}
