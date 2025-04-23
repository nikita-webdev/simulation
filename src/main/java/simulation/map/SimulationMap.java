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

    public int[] generateRandomCoordinates() {
        int[] xy = new int[2];
        Random random = new Random();

        do {
            xy[0] = random.nextInt((SimulationMap.MAP_SIZE_X));
            xy[1] = random.nextInt((SimulationMap.MAP_SIZE_Y));
        } while (isCoordinatesOccupied(xy));

        return xy;
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

    public Entity checkEntity(int[] currentPosition) {
        Cell targetCell = cell.findCellInMap(map, currentPosition[0], currentPosition[1]);

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Cell entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell == targetCell) {
                return entity;
            }
        }

        return null;
    }

    public boolean isGrass(int[] currentPosition) {
        return checkEntity(currentPosition) instanceof Grass;
    }

    public boolean isHerbivore(int[] currentPosition) {
        return checkEntity(currentPosition) instanceof Herbivore;
    }

    public boolean isTreeOrRock(int[] currentPosition) {
        return (checkEntity(currentPosition) instanceof Tree || checkEntity(currentPosition) instanceof Rock);
    }

    public boolean isCoordinatesOccupied(int[] targetCoordinates) {
        boolean isContain = false;

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
