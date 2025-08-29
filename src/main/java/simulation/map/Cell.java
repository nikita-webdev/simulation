package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;

import java.util.Arrays;
import java.util.Map;

public class Cell {
    private int[] coordinates = new int[2];

    public Cell(int x, int y) {
        coordinates = new int[]{x, y};
    }

    public Cell() {

    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getX() {
        return coordinates[0];
    }

    public int getY() {
        return coordinates[1];
    }

    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public Cell findCellInMap(Map<Cell, Entity> targetMap, int x, int y) {
        for (Cell cell : targetMap.keySet()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    public boolean isFood(SimulationMap simulationMap, Creature creature, int[] node) {
        boolean isFood = false;

        if (creature instanceof Herbivore) {
            isFood = simulationMap.isGrass(node);
        } else if (creature instanceof Predator) {
            isFood = simulationMap.isHerbivore(node);
        }

        return isFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;
        return Arrays.equals(coordinates, cell.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }
}
