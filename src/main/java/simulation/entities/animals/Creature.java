package simulation.entities.animals;

import simulation.entities.Entity;
import simulation.Game;
import simulation.SimulationMap;
import simulation.utils.SearchPath;

import java.util.*;

public abstract class Creature extends Entity {
    private SimulationMap map = SimulationMap.getInstance();
    SearchPath searchPath = new SearchPath();

    Game game = new Game();

    public List<int[]> pathToGoal;
    private int numberOfStep = 0;

    public String groupName;
    int speed = 1;
    int hp;
    int[] goalFoodCoordinates = {-1, -1};

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    public void makeMove(int[] goalNode) throws InterruptedException {
        updateFoodCoordinates(this);

        boolean isNotLastStep = (getNumberOfStep() < pathToGoal.size());

        if (isNotLastStep) {
            int[] nextStep = pathToGoal.get(getNumberOfStep());
            boolean isFoodFound = (goalNode[0] == nextStep[0] && goalNode[1] == nextStep[1]);

            if(!map.isCoordinatesOccupied(nextStep)) {
                makeStep(nextStep);
                coordinates[0] = positionX;
                coordinates[1] = positionY;

                if(getNumberOfStep() < pathToGoal.size() - 1) {
                    increaseNumberOfStep();
                }
            } else if(isFoodFound) {
                eat(goalNode);
                clearNumberOfStep();
                stopMovement();
            } else {
                System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(nextStep));
            }
        } else {
//            eat(goalNode);
//            numberOfStep = 0;
//            stopMovement();
        }

        game.updateMap();
    }

    private void eat(int[] foodCoordinates) {
        if (groupName.equals("herbivore")) {
            if(map.isGrass(foodCoordinates)) {
                map.removeGrass(foodCoordinates);
            }
        } else if (groupName.equals("predator")) {
            if(map.isHerbivore(foodCoordinates)) {
                map.removeHerbivore(foodCoordinates);
            }
        }
    }

    public int[] getGoalFoodCoordinates() {
        return goalFoodCoordinates;
    }

    public void setGoalFoodCoordinates(int[] newEatCoordinates) {
        goalFoodCoordinates[0] = newEatCoordinates[0];
        goalFoodCoordinates[1] = newEatCoordinates[1];
    }

    private void stopMovement() {
        setGoalFoodCoordinates(coordinates);
    }

    private void makeStep(int[] coordinates) {
        positionX = coordinates[0];
        positionY = coordinates[1];
    }

    public int getNumberOfStep() {
        return numberOfStep;
    }

    private void increaseNumberOfStep() {
        numberOfStep++;
    }

    private void clearNumberOfStep() {
        numberOfStep = 0;
    }

    private boolean shouldUpdateFoodCoordinates(Creature creature) {
        boolean foodCoordinatesIncorrect = false;

        if (creature.groupName.equals("predator")) {
            boolean currentFoodIsCreature = map.isHerbivore(creature.getGoalFoodCoordinates());
            boolean hasHerbivores = !map.getAllHerbivoresCoordinatesForRemove().isEmpty();

            foodCoordinatesIncorrect = (!currentFoodIsCreature && hasHerbivores);
        } else if (creature.groupName.equals("herbivore")) {
            boolean currentFoodIsGrass = map.isGrass(creature.getGoalFoodCoordinates());
            boolean hasGrass = !map.getAllGrassesCoordinatesForRemove().isEmpty();

            foodCoordinatesIncorrect = (!currentFoodIsGrass && hasGrass);
        }

        return foodCoordinatesIncorrect;
    }

    private void updateFoodCoordinates(Creature creature) {
        if(shouldUpdateFoodCoordinates(creature)) {
            creature.setGoalFoodCoordinates(searchPath.searchPath(creature));
            clearNumberOfStep();
        }
    }
}
