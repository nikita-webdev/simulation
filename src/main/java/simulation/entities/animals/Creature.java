package simulation.entities.animals;

import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    public int speed = 1;
    int hp;

    public Creature(String name) {
        super(name);
    }

    public void makeMove(SimulationMap simulationMap, Coordinate from, List<Coordinate> path) {
        Coordinate nextStep = path.get(0);

        if(simulationMap.isFood(simulationMap, this, nextStep)) {
            eat(simulationMap, nextStep);
        } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
            moveCreature(simulationMap, from, nextStep);

        } else {
            System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(new int[] {nextStep.getX(), nextStep.getY()}));
        }
    }

    private void eat(SimulationMap simulationMap, Coordinate food) {
        if (simulationMap.isFood(simulationMap, this, food)) {
            simulationMap.removeEntity(food);
        }
    }

    private void moveCreature(SimulationMap simulationMap, Coordinate from, Coordinate to) {
        Entity entity = this;

        if (from != null) {
            simulationMap.removeEntity(from);

            simulationMap.addEntity(to, entity);
        }
    }

    public abstract boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate);
}
