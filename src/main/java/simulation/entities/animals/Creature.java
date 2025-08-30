package simulation.entities.animals;

import simulation.Game;
import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    Game game = new Game();

    private Coordinate cell;

    public int speed = 1;
    int hp;

    public Creature(Coordinate coordinate, String name) {
        super(name);
        this.cell = coordinate;
    }

    public void makeMove(SimulationMap simulationMap, List<Coordinate> path) {
        Coordinate nextStep = path.get(0);

        if(cell.isFood(simulationMap, this, nextStep)) {
            eat(simulationMap, nextStep);
        } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
            makeStep(simulationMap, nextStep);

            path.remove(0);
        } else {
            System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(new int[] {nextStep.getX(), nextStep.getY()}));
        }

        game.updateMap(simulationMap);
    }

    private void eat(SimulationMap simulationMap, Coordinate food) {
        if (cell.isFood(simulationMap, this, food)) {
            simulationMap.removeCell(food);
        }
    }

    private void makeStep(SimulationMap simulationMap, Coordinate stepCoordinates) {
        updateCreatureCoordinates(simulationMap, stepCoordinates);
    }

    private void updateCreatureCoordinates(SimulationMap simulationMap, Coordinate newCoordinates) {
        Coordinate oldCell = getCell();
        Entity oldEntity = this;

        if (oldCell != null) {
            simulationMap.removeCell(oldCell);

            oldCell.setCoordinates(newCoordinates.getX(), newCoordinates.getY());
            simulationMap.addEntity(oldCell, oldEntity);
        }
    }

    public Coordinate getCell() {
        return cell;
    }
}
