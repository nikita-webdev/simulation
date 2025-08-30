package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

import java.util.*;

public class SimulationMap {
    private final Map<Coordinate, Entity> entities = new HashMap<>();
    private final Coordinate cell = new Coordinate();

    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_Y = 15;

    public void addEntity(Coordinate cell, Entity entity) {
        int x = cell.getX();
        int y = cell.getY();

        entities.put(cell, entity);
    }

    public Map<Coordinate, Entity> getEntities() {
        return Collections.unmodifiableMap(entities);
    }

    public int[] generateRandomCoordinates() {
        int[] xy = new int[2];
        Random random = new Random();

        do {
            xy[0] = random.nextInt((SimulationMap.MAP_SIZE_X));
            xy[1] = random.nextInt((SimulationMap.MAP_SIZE_Y));
        } while (isCoordinatesOccupied(new Coordinate(xy[0], xy[1])));

        return xy;
    }

    public int getCountOfGrasses() {
        int countOfGrasses = 0;

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();

            if (entity instanceof Grass) {
                countOfGrasses++;
            }
        }

        return countOfGrasses;
    }

    public int getCountOfHerbivores() {
        int countOfHerbivores = 0;

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();

            if (entity instanceof Herbivore) {
                countOfHerbivores++;
            }
        }

        return countOfHerbivores;
    }

    public Entity checkEntity(int[] currentPosition) {
        Coordinate targetCell = cell.findCellInMap(getEntities(), currentPosition[0], currentPosition[1]);

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Coordinate entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell.equals(targetCell)) {
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

    public boolean isCoordinatesOccupied(Coordinate targetCoordinates) {
        boolean isContain = false;

        if (cell.findCellInMap(getEntities(), targetCoordinates.getX(), targetCoordinates.getY()) != null) {
            isContain = true;
        }

        return isContain;
    }

    public Map<Coordinate, Creature> getAllCreatures() {
        Map<Coordinate, Creature> creatures = new HashMap<>();

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();
            Coordinate cell = entry.getKey();

            if (entity instanceof Creature creature) {
                creatures.put(cell, creature);
            }
        }

        return creatures;
    }

    public void removeCell(Coordinate cellForRemove) {
        entities.remove(cellForRemove);
    }

    public boolean isCoordinatesWithinMapBounds(int[] targetCoordinates) {
        int x = targetCoordinates[0];
        int y = targetCoordinates[1];

        return (x < SimulationMap.MAP_SIZE_X && x >= 0) && (y < SimulationMap.MAP_SIZE_Y && y >= 0);
    }
}
