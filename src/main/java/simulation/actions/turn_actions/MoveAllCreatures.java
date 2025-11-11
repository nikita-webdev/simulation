package simulation.actions.turn_actions;

import simulation.actions.Action;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;
import simulation.entities.animals.Creature;
import simulation.pathfinder.PathFinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoveAllCreatures implements Action {
    private boolean isMoveAllowed = true;

    @Override
    public void execute(SimulationMap simulationMap) {
        PathFinder pathFinder = new PathFinder();
        final List<Coordinate> coordinates = collectAllCreatures(simulationMap);

        for (Coordinate coordinate : coordinates) {
            if (isMoveAllowed) {
                Creature creature = simulationMap.getAllCreatures().get(coordinate);

                boolean isCreatureAlive = simulationMap.getAllCreatures().containsKey(coordinate);

                if (isCreatureAlive) {
                    List<Coordinate> path = pathFinder.searchPath(simulationMap, creature, coordinate);

                    creature.makeMove(simulationMap, coordinate, path);
                }
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

    public void setMoveAllowed(boolean state) {
        isMoveAllowed = state;
    }
}
