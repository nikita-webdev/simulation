package simulation.entities.animals;

import simulation.Game;
import simulation.entities.Entity;
import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.pathfinder.PathFinder;

import java.util.*;

public abstract class Creature extends Entity {
    Game game = new Game();
    PathFinder pathFinder = new PathFinder();

    private Cell cell;

    public List<int[]> path = new LinkedList<>();

    public int speed = 1;
    int hp;
    int[] foodCoordinates = {-1, -1};

    public Creature(Cell cell, String name) {
        super(name);
        this.cell = cell;
    }

    public void makeMove(SimulationMap simulationMap, int[] foodTargetNode) {
        if(shouldUpdateFoodCoordinates(simulationMap, this)) {
            updateFoodCoordinates(simulationMap, this);
        }

        int[] nextStep = path.get(0);

        if(cell.isFood(simulationMap, this, nextStep)) {
            eat(simulationMap, foodTargetNode);
        } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
            makeStep(simulationMap, nextStep);

            path.remove(0);
        } else {
            System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(nextStep));
            updateFoodCoordinates(simulationMap, this);
        }

        game.updateMap(simulationMap);
    }

    private void eat(SimulationMap simulationMap, int[] foodCoordinates) {
        if (cell.isFood(simulationMap, this, foodCoordinates)) {
            simulationMap.removeCellByCoordinates(foodCoordinates[0], foodCoordinates[1]);
        }
    }

    public int[] getFoodCoordinates() {
        return foodCoordinates;
    }

    public void setFoodCoordinates(int[] newFoodCoordinates) {
        foodCoordinates[0] = newFoodCoordinates[0];
        foodCoordinates[1] = newFoodCoordinates[1];
    }

    private void makeStep(SimulationMap simulationMap, int[] stepCoordinates) {
        updateCreatureCoordinates(simulationMap, stepCoordinates);
    }

    private boolean shouldUpdateFoodCoordinates(SimulationMap simulationMap, Creature creature) {
        // If isFoodCoordinatesInvalid is true, the food coordinates of creature should be updated
        boolean isFoodCoordinatesInvalid = false;

        if (path == null || path.isEmpty() || path.get(0) == null) {
            isFoodCoordinatesInvalid = true;
        } else if (creature instanceof Predator) {
            isFoodCoordinatesInvalid = !cell.isFood(simulationMap, this, path.get(0));
        } else if (creature instanceof Herbivore) {
            isFoodCoordinatesInvalid = !cell.isFood(simulationMap, this, path.get(0));
        }

        return isFoodCoordinatesInvalid;
    }

    private void updateFoodCoordinates(SimulationMap simulationMap, Creature creature) {
        path = pathFinder.searchPath(simulationMap, creature, cell);

        creature.setFoodCoordinates(path.get(path.size() - 1));
    }

    private void updateCreatureCoordinates(SimulationMap simulationMap, int[] newCoordinates) {
        Cell oldCell = getCell();
        Entity oldEntity = this;

        if (oldCell != null) {
            simulationMap.removeCell(oldCell);

            oldCell.setCoordinates(newCoordinates[0], newCoordinates[1]);
            simulationMap.addEntity(oldCell, oldEntity);
        }
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isCreatureAlive(SimulationMap simulationMap, Creature creature) {
        return cell.findCellInMap(simulationMap.getEntities(), cell.getX(), cell.getY()) != null;
    }
}
