package simulation.animals;

import simulation.Entity;
import simulation.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    private SimulationMap map = SimulationMap.getInstance();

    public List<int[]> pathToGoal;
    int numberOfStep = 0;

    public String groupName;
    int speed;
    int hp;
    int[] goalFoodCoordinates = {-1, -1};

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    public void makeMove(int[] goalNode) {
        boolean isNotLastStep = (numberOfStep < pathToGoal.size());

        if (isNotLastStep) {
            int[] nextStep = pathToGoal.get(numberOfStep);
            boolean isFoodFound = (goalNode[0] == nextStep[0] && goalNode[1] == nextStep[1]);

            if(!map.isCoordinatesOccupied(nextStep)) {
                makeStep(nextStep);
                coordinates[0] = positionX;
                coordinates[1] = positionY;

                if(numberOfStep < pathToGoal.size() - 1) {
                    numberOfStep++;
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
    }

    public void eat(int[] foodCoordinates) {
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

    public void clearNumberOfStep() {
        numberOfStep = 0;
    }
}
