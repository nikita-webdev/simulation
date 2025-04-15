package simulation.actions;

import simulation.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.Map;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();

    public void makeMoveAllCreatures() throws InterruptedException {
        for (Map.Entry<String, Creature> entry : map.getAllCreatures().entrySet()) {
            Creature creature = entry.getValue();

            creature.makeMove(creature.getGoalFoodCoordinates());
        }
    }
}
