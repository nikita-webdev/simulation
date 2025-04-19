package simulation.actions;

import simulation.entities.Entity;
import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();
    private final Queue<Creature> creatures = new LinkedList<>();

    public void makeMoveAllCreatures() throws InterruptedException {
        for (Map.Entry<Cell, Creature> entry : map.getAllCreatures().entrySet()) {
            Creature creature = entry.getValue();

            creatures.add(creature);
        }

        while (!creatures.isEmpty()) {
            Creature creature = creatures.poll();

            creature.makeMove(creature.getGoalFoodCoordinates());
        }
    }
}
