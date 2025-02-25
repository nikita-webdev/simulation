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
    int[] goalEatCoordinates = {-1, -1};

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    public void makeMove(int[] goalNode) {
        int finishX = goalNode[0];
        int finishY = goalNode[1];

        if(positionX != finishX || positionY != finishY) {
            int[] nextStep = coordinates;

            if (Arrays.equals(getGoalEatCoordinates(), coordinates)) {
                nextStep = pathToGoal.get(0);
            } else if (getGoalEatCoordinates().length == 1) {
                nextStep = pathToGoal.get(0);
            } else if (pathToGoal.size() > numberOfStep){
                nextStep = pathToGoal.get(numberOfStep);
            } else {
                System.out.println("IndexOutOfBoundsException: " + "pathToGoal.size() " + pathToGoal.size() + ", numberOfStep " + numberOfStep );
            }

                if(!map.isCoordinatesOccupied(nextStep)){
                    positionX = nextStep[0];
                    positionY = nextStep[1];
                    System.out.println("Take a step on: " + Arrays.toString(nextStep));
                    if(numberOfStep < pathToGoal.size() - 1) {
                        numberOfStep++;
                    } else if (numberOfStep >= pathToGoal.size() - 1) {
                        numberOfStep = pathToGoal.size() - 1;
                    }
                } else if(goalNode[0] == nextStep[0] && goalNode[1] == nextStep[1]) {
                    eat(goalNode);
                    numberOfStep = 0;
                    setGoalEatCoordinates(coordinates);
                } else {
                    System.out.println((Arrays.equals(goalNode, nextStep)));
                    System.out.printf("%s, %s%n", Arrays.toString(goalNode), Arrays.toString(nextStep));
                    System.out.println("Should have taken a step, but it was not taken on: " + Arrays.toString(nextStep));
                }
        }
    }



    public void eat(int[] eatThis) {
        if (groupName.equals("herbivore")) {
            if(map.isGrass(eatThis)) {
                map.removeGrass(eatThis);
            }
        } else if (groupName.equals("predator")) {
            if(map.isHerbivore(eatThis)) {
                map.removeHerbivore(eatThis);
            }
        }
    }

    public int[] getGoalEatCoordinates() {
        return goalEatCoordinates;
    }

    public void setGoalEatCoordinates(int[] newEatCoordinates) {
        goalEatCoordinates[0] = newEatCoordinates[0];
        goalEatCoordinates[1] = newEatCoordinates[1];
    }
}
