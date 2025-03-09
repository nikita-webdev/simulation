package simulation.actions;

import simulation.SimulationMap;
import simulation.animals.Creature;

import java.util.Map;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();

    public void makeMoveAllCreatures() {
        for (Map.Entry<String, Creature> entry : map.getAllCreatures().entrySet()) {
            Creature creature = entry.getValue();

            creature.makeMove(creature.getGoalFoodCoordinates());
        }
    }
}
