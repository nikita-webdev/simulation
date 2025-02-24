package simulation.actions;

import simulation.Entity;
import simulation.SimulationMap;
import simulation.animals.Creature;
import simulation.animals.Herbivore;
import simulation.utils.SearchPath;

import java.util.Map;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();
    SearchPath searchPath = new SearchPath();

    public void makeMoveAllCreatures() {
        for (Map.Entry<String, Entity> entry : map.map.entrySet()) {
            if (entry.getValue() instanceof Creature) {
                Creature creature = (Creature) entry.getValue();
            }
        }

        for (Map.Entry<String, Herbivore> entry : map.getAllHerbivores().entrySet()) {
            Herbivore herbivore = entry.getValue();
            if(!map.isGrass(herbivore.getGoalEatCoordinates()) && !map.getAllGrassesCoordinatesForRemove().isEmpty()) {
                herbivore.setGoalEatCoordinates(searchPath.searchPath(herbivore));
            }
            herbivore.makeMove(herbivore.getGoalEatCoordinates());
        }
    }
}
