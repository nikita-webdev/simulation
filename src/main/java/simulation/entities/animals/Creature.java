package simulation.entities.animals;

import simulation.Game;
import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    Game game = new Game();

    private Coordinate coordinate;

    public int speed = 1;
    int hp;

    public Creature(Coordinate coordinate, String name) {
        super(name);
        this.coordinate = coordinate;
    }

    public void makeMove(SimulationMap simulationMap, List<Coordinate> path) {
        Coordinate nextStep = new Coordinate(path.get(0).getX(), path.get(0).getY());

        if(coordinate.isFood(simulationMap, this, nextStep)) {
            eat(simulationMap, nextStep);
        } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
            makeStep(simulationMap, nextStep);

//            path.remove(0);
        } else {
            System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(new int[] {nextStep.getX(), nextStep.getY()}));
        }

        game.updateMap(simulationMap);
    }

    private void eat(SimulationMap simulationMap, Coordinate food) {
        if (coordinate.isFood(simulationMap, this, food)) {
            simulationMap.removeEntity(food);
        }
    }

    private void makeStep(SimulationMap simulationMap, Coordinate stepCoordinates) {
        updateCreatureCoordinates(simulationMap, stepCoordinates);
    }

    private void updateCreatureCoordinates(SimulationMap simulationMap, Coordinate newCoordinates) {
        Coordinate oldCell = getCoordinate();
        Entity oldEntity = this;

        if (oldCell != null) {
            simulationMap.removeEntity(oldCell);

            oldCell.setCoordinates(newCoordinates.getX(), newCoordinates.getY());
            simulationMap.addEntity(oldCell, oldEntity);
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
