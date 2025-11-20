package simulation.entities.animals;

import simulation.coordinate.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.List;

public interface Mover {
    void move(Creature creature, SimulationMap simulationMap, Coordinate from, List<Coordinate> path);
}
