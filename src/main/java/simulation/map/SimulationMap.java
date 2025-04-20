package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

import java.util.*;

public class SimulationMap {
    public Map<Cell, Entity> map = new HashMap<>();
    private static final SimulationMap instance = new SimulationMap();
    private Cell cell = new Cell();

    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_Y = 15;

    private SimulationMap() {

    }

    public static SimulationMap getInstance() {
        return instance;
    }

    public void addEntity(Cell cell, Entity entity) {
        map.put(cell, entity);
    }

    public int getCountOfGrasses() {
        int countOfGrasses = 0;

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Entity entity = entry.getValue();

            if (entity instanceof Grass) {
                countOfGrasses++;
            }
        }

        return countOfGrasses;
    }

    public int getCountOfHerbivores() {
        int countOfHerbivores = 0;

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Entity entity = entry.getValue();

            if (entity instanceof Herbivore) {
                countOfHerbivores++;
            }
        }

        return countOfHerbivores;
    }

    public boolean isGrass(int[] thisPosition) {
        Cell targetCell = cell.findCellInMap(map, thisPosition[0], thisPosition[1]);

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Cell entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell == targetCell) {
                if (entity instanceof Grass) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isHerbivore(int[] thisPosition) {
        Cell targetCell = cell.findCellInMap(map, thisPosition[0], thisPosition[1]);

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Cell entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell == targetCell) {
                if (entity instanceof Herbivore) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isTreeOrRock(int[] thisPosition) {
        Cell targetCell = cell.findCellInMap(map, thisPosition[0], thisPosition[1]);

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Cell entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell == targetCell) {
                if (entity instanceof Tree || entity instanceof Rock) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCoordinatesOccupied(int[] targetCoordinates) {
        boolean isContain = false;

//        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
//            int[] existingCoordinates = new int[2];
//            existingCoordinates[0] = entry.getValue().positionX;
//            existingCoordinates[1] = entry.getValue().positionY;
//
//            isContain = Arrays.equals(targetCoordinates, existingCoordinates);
//
//            if (isContain) {
//                break;
//            }
//        }

        if (cell.findCellInMap(map, targetCoordinates[0], targetCoordinates[1]) != null) {
            isContain = true;
        }

        return isContain;
    }

    public Map<Cell, Creature> getAllCreatures() {
        Map<Cell, Creature> creatures = new HashMap<>();

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Entity entity = entry.getValue();
            Cell cell = entry.getKey();

            if (entity instanceof Creature creature) {
                creatures.put(cell, creature);
            }
        }

        return creatures;
    }

    public void removeCell(int x, int y) {
        Cell targetCell = cell.findCellInMap(map, x, y);

        if (targetCell != null) {
            map.remove(targetCell);
        } else {
            System.out.println("Клетка не найдена.");
        }
    }
}
