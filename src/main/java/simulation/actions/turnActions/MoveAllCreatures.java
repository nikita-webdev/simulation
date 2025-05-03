package simulation.actions.turnActions;

import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MoveAllCreatures {
    private final Queue<Creature> creatures = new LinkedList<>();

    public void makeMoveAllCreatures(SimulationMap simulationMap) throws InterruptedException {
        collectAllCreatures(simulationMap);

        while (!creatures.isEmpty()) {
            Creature creature = creatures.poll();

            for (int i = 0; i < creature.speed; i++) {
                creature.makeMove(simulationMap, creature.getFoodCoordinates());
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
