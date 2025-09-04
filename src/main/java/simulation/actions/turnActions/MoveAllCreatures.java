package simulation.actions.turnActions;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;
import simulation.pathfinder.PathFinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoveAllCreatures {
    public void execute(SimulationMap simulationMap) {
        PathFinder pathFinder = new PathFinder();
        final List<Coordinate> coordinates = collectAllCreatures(simulationMap);

        for (Coordinate coordinate : coordinates) {
            Creature creature = simulationMap.getAllCreatures().get(coordinate);

            boolean isCreatureAlive = simulationMap.getAllCreatures().containsKey(coordinate);

            if (isCreatureAlive) {
                List<Coordinate> path = pathFinder.searchPath(simulationMap, creature, coordinate);

                creature.makeMove(simulationMap, coordinate, path);
            }
        }
    }

    private List<Coordinate> collectAllCreatures(SimulationMap simulationMap) {
        List<Coordinate> coordinates = new LinkedList<>();
        for (Map.Entry<Coordinate, Creature> entry : simulationMap.getAllCreatures().entrySet()) {
            Coordinate coordinate = entry.getKey();

            coordinates.add(coordinate);
        }

        return coordinates;
    }
}
