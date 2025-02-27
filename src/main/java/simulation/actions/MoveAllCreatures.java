package simulation.actions;

import simulation.Entity;
import simulation.Renderer;
import simulation.SimulationMap;
import simulation.animals.Creature;
import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.utils.SearchPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();
    SearchPath searchPath = new SearchPath();
    Renderer renderer = new Renderer();

    public void makeMoveAllCreatures() throws InterruptedException {

        for (Map.Entry<String, Predator> entry : map.getAllPredators().entrySet()) {
            Predator predator = entry.getValue();
            // Если координаты не содержат еду
            if(!map.isHerbivore(predator.getGoalEatCoordinates()) && !map.getAllHerbivoresCoordinatesForRemove().isEmpty()) {
                predator.setGoalEatCoordinates(searchPath.searchPath(predator));
            }

            predator.makeMove(predator.getGoalEatCoordinates());

            renderer.renderMap();
            Thread.sleep(1000);
        }

        map.clearAllHerbivoresCoordinatesForRemove();

        for (Map.Entry<String, Herbivore> entry : map.getAllHerbivores().entrySet()) {
            Herbivore herbivore = entry.getValue();
            // Если координаты не содержат еду
            if(!map.isGrass(herbivore.getGoalEatCoordinates()) && !map.getAllGrassesCoordinatesForRemove().isEmpty()) {
                herbivore.setGoalEatCoordinates(searchPath.searchPath(herbivore));
            }

            herbivore.makeMove(herbivore.getGoalEatCoordinates());
            map.setAllHerbivoresCoordinatesForRemove(herbivore);
            renderer.renderMap();
            Thread.sleep(1000);
        }
    }
}
