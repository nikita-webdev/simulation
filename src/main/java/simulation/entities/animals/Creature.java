package simulation.entities.animals;

import simulation.Game;
import simulation.Simulation;
import simulation.entities.Entity;
import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.utils.SearchPath;

import java.util.*;

public abstract class Creature extends Entity {
    Game game = new Game();
    SearchPath searchPath = new SearchPath();

    private Cell cell;

    public int[] coordinates = new int[2];

    public List<int[]> pathToFood;
    private int currentStepNumber = 0;

    public int speed = 1;
    int hp;
    int[] foodCoordinates = {-1, -1};

    public Creature(Cell cell, String name) {
        super(name);
        this.cell = cell;
        this.coordinates[0] = cell.getX();
        this.coordinates[1] = cell.getY();
    }

    public void makeMove(SimulationMap simulationMap, int[] foodTargetNode) throws InterruptedException {
        if(shouldUpdateFoodCoordinates(simulationMap, this)) {
            updateFoodCoordinates(simulationMap, this);
        }

        boolean isNotLastStep = getCurrentStepNumber() < pathToFood.size();

        if (isNotLastStep) {
            int[] nextStepCoordinates = pathToFood.get(getCurrentStepNumber());
            boolean isFoodAtNextStep = (foodTargetNode[0] == nextStepCoordinates[0] && foodTargetNode[1] == nextStepCoordinates[1]);

            if(isFoodAtNextStep) {
                eat(simulationMap, foodTargetNode);
                clearNumberOfStep();
                stopMovement();
            } else if (!simulationMap.isCoordinatesOccupied(nextStepCoordinates)) {
                makeStep(nextStepCoordinates);

                if(getCurrentStepNumber() < pathToFood.size() - 1) {
                    increaseCurrentStepNumber();
                }
            } else {
                System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(nextStepCoordinates));
                updateFoodCoordinates(simulationMap, this);
            }
        } else {
//            eat(foodTargetNode);
//            numberOfStep = 0;
//            stopMovement();
        }

        game.updateMap(simulationMap);
    }

    private void eat(SimulationMap simulationMap, int[] foodCoordinates) {
        if (this instanceof Herbivore) {
            if(simulationMap.isGrass(foodCoordinates)) {
                simulationMap.removeCell(foodCoordinates[0], foodCoordinates[1]);
            }
        } else if (this instanceof Predator) {
            if(simulationMap.isHerbivore(foodCoordinates)) {
                simulationMap.removeCell(foodCoordinates[0], foodCoordinates[1]);
            }
        }
    }

    public int[] getFoodCoordinates() {
        return foodCoordinates;
    }

    public void setFoodCoordinates(int[] newFoodCoordinates) {
        foodCoordinates[0] = newFoodCoordinates[0];
        foodCoordinates[1] = newFoodCoordinates[1];
    }

    private void stopMovement() {
        setFoodCoordinates(coordinates);
    }

    private void makeStep(int[] stepCoordinates) {
        cell.setCoordinates(stepCoordinates[0], stepCoordinates[1]);
        coordinates = cell.getCoordinates();
    }

    public int getCurrentStepNumber() {
        return currentStepNumber;
    }

    private void increaseCurrentStepNumber() {
        currentStepNumber++;
    }

    private void clearNumberOfStep() {
        currentStepNumber = 0;
    }

    private boolean shouldUpdateFoodCoordinates(SimulationMap simulationMap, Creature creature) {
        // If isFoodCoordinatesInvalid is true, the food coordinates of creature should be updated
        boolean isFoodCoordinatesInvalid = false;

        if (creature instanceof Predator) {
            isFoodCoordinatesInvalid = !simulationMap.isHerbivore(creature.getFoodCoordinates());
        } else if (creature instanceof Herbivore) {
            isFoodCoordinatesInvalid = !simulationMap.isGrass(creature.getFoodCoordinates());
        }

        return isFoodCoordinatesInvalid;
    }

    private void updateFoodCoordinates(SimulationMap simulationMap, Creature creature) {
            pathToFood = searchPath.searchPath(simulationMap, creature);

            creature.setFoodCoordinates(pathToFood.get(pathToFood.size() - 1));
            clearNumberOfStep();
    }
}
