package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;

import java.util.Map;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate findCellInMap(Map<Coordinate, Entity> targetMap, int x, int y) {
        for (Coordinate cell : targetMap.keySet()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    public boolean isFood(SimulationMap simulationMap, Creature creature, Coordinate coordinate) {
        int[] checkFood = new int[] {coordinate.getX(), coordinate.getY()};


        boolean isFood = false;

        if (creature instanceof Herbivore) {
            isFood = simulationMap.isGrass(checkFood);
        } else if (creature instanceof Predator) {
            isFood = simulationMap.isHerbivore(checkFood);
        }

        return isFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
