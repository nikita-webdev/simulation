package simulation.actions.turn_actions;

import simulation.actions.Action;
import simulation.coordinate.Coordinate;
import simulation.simulation_map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoveAllCreatures implements Action {
    @Override
    public void execute(SimulationMap simulationMap) {
        final List<Coordinate> coordinates = collectAllCreatures(simulationMap);

        for (Coordinate coordinate : coordinates) {
            Creature creature = simulationMap.getEntitiesOfType(Creature.class).get(coordinate);
            boolean isCreatureAlive = simulationMap.getEntitiesOfType(Creature.class).containsKey(coordinate);

            if (isCreatureAlive && creature != null) {
                List<Coordinate> path = creature.calculatePath(simulationMap, coordinate);

                creature.makeMove(simulationMap, coordinate, path);
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
}
