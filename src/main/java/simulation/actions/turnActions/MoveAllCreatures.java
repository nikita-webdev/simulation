package simulation.actions.turnActions;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;
import simulation.pathfinder.PathFinder;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MoveAllCreatures {
    private final Queue<Creature> creatures = new LinkedList<>();

    public void makeMoveAllCreatures(SimulationMap simulationMap) {
        PathFinder pathFinder = new PathFinder();

        collectAllCreatures(simulationMap);

        while (!creatures.isEmpty()) {
            Creature creature = creatures.poll();

            boolean isCreatureAlive = creature.getCell().findCellInMap(simulationMap.getEntities(), creature.getCell().getX(), creature.getCell().getY()) != null;

            if (isCreatureAlive) {
                for (int i = 0; i < creature.speed; i++) {
                    creature.makeMove(simulationMap, pathFinder.searchPath(simulationMap, creature));
                }
            }
        }
    }

    private void collectAllCreatures(SimulationMap simulationMap) {
        for (Map.Entry<Coordinate, Creature> entry : simulationMap.getAllCreatures().entrySet()) {
            Creature creature = entry.getValue();

            creatures.add(creature);
        }
    }
}
