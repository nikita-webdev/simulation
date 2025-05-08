package simulation.actions.turnActions;

import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MoveAllCreatures {
    private final Queue<Creature> creatures = new LinkedList<>();
    Cell cell = new Cell();

    public void makeMoveAllCreatures(SimulationMap simulationMap) throws InterruptedException {
        collectAllCreatures(simulationMap);

        while (!creatures.isEmpty()) {
            Creature creature = creatures.poll();

            boolean isCreatureAlive = cell.findCellInMap(simulationMap.getEntities(), creature.coordinates[0], creature.coordinates[1]) != null;

            if (isCreatureAlive) {
                for (int i = 0; i < creature.speed; i++) {
                    creature.makeMove(simulationMap, creature.getFoodCoordinates());
                }
            }
        }
    }

    private void collectAllCreatures(SimulationMap simulationMap) {
        for (Map.Entry<Cell, Creature> entry : simulationMap.getAllCreatures().entrySet()) {
            Creature creature = entry.getValue();

            creatures.add(creature);
        }
    }
}
