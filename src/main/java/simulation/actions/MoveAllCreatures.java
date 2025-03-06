package simulation.actions;

import simulation.Renderer;
import simulation.SimulationMap;
import simulation.animals.Creature;
import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.utils.SearchPath;

import java.util.Map;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();
    SearchPath searchPath = new SearchPath();
    Renderer renderer = new Renderer();

    public void makeMoveAllCreatures() {
        for (Map.Entry<String, Predator> entry : map.getAllPredators().entrySet()) {
            Predator predator = entry.getValue();

            if(checkFoodOnTheMap(predator)) {
                predator.setGoalFoodCoordinates(searchPath.searchPath(predator));
                predator.clearNumberOfStep();
            }

            predator.makeMove(predator.getGoalFoodCoordinates());
        }

        map.clearAllHerbivoresCoordinatesForRemove(); // NullPointerException

        for (Map.Entry<String, Herbivore> entry : map.getAllHerbivores().entrySet()) {
            Herbivore herbivore = entry.getValue();

            if(checkFoodOnTheMap(herbivore)) {
                herbivore.setGoalFoodCoordinates(searchPath.searchPath(herbivore));
                herbivore.clearNumberOfStep();
            }

            herbivore.makeMove(herbivore.getGoalFoodCoordinates());
            map.setAllHerbivoresCoordinatesForRemove(herbivore);
        }
    }

    private boolean checkFoodOnTheMap(Creature creature) {
        boolean isFood = false;

        if (creature.groupName.equals("predator")) {
            isFood = (!map.isHerbivore(creature.getGoalFoodCoordinates()) && !map.getAllHerbivoresCoordinatesForRemove().isEmpty());
        }

        if (creature.groupName.equals("herbivore")) {
            isFood = (!map.isGrass(creature.getGoalFoodCoordinates()) && !map.getAllGrassesCoordinatesForRemove().isEmpty());
        }

        return isFood;
    }
}
