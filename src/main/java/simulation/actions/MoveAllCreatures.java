package simulation.actions;

import simulation.Entity;
import simulation.SimulationMap;
import simulation.animals.Creature;
import simulation.animals.Herbivore;

import java.util.Map;
import java.util.Objects;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();


    public void makeMoveAllCreatures() {
        for (Map.Entry<String, Entity> entry : map.map.entrySet()) {
            if (entry.getValue() instanceof Creature) {
                Creature creature = (Creature) entry.getValue();

                if (entry.getValue() instanceof Herbivore herbivore) {
                    if(Objects.equals(entry.getValue().name, "herbivore1")) {
                        // Если координаты не содержат еду
                        if(!map.isGrass(herbivore.getGoalEatCoordinates())) {
                            herbivore.setGoalEatCoordinates(herbivore.searchPath());
                        }

                        herbivore.makeMove(herbivore.getGoalEatCoordinates());
                    }
                }
            }
        }
    }
}
