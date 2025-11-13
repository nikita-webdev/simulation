package simulation.actions.turn_actions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
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
                Creature creature = simulationMap.getEntitiesOfType(Creature.class).get(coordinate);

                boolean isCreatureAlive = simulationMap.getEntitiesOfType(Creature.class).containsKey(coordinate);

                if (isCreatureAlive) {
                    List<Coordinate> path = pathFinder.searchPath(simulationMap, coordinate, Grass.class);

                    creature.makeMove(simulationMap, coordinate, path);
                }
            }
        }
    }

    private List<Coordinate> collectAllCreatures(SimulationMap simulationMap) {
        List<Coordinate> coordinates = new LinkedList<>();
        for (Map.Entry<Coordinate, Creature> entry : simulationMap.getEntitiesOfType(Creature.class).entrySet()) {
            Coordinate coordinate = entry.getKey();

            coordinates.add(coordinate);
        }

        return coordinates;
    }

    public void setMoveAllowed(boolean state) {
        isMoveAllowed = state;
    }
}
