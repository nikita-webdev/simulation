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
    private final Coordinate coordinate = new Coordinate();

    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_Y = 15;

    public void addEntity(Coordinate coordinate, Entity entity) {
        entities.put(coordinate, entity);
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

    public Entity checkEntityByCoordinate(Coordinate coordinate) {
        Coordinate targetCoordinate = coordinate.findCellInMap(getEntities(), coordinate);

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Coordinate entryCell = entry.getKey();
            Entity entity = entry.getValue();

            if (entryCell.equals(targetCoordinate)) {
                return entity;
            }
        }

//        return getEntities().get(coordinate);

        return null;
    }

//    public Entity getEntity(Coordinate coordinate) {
//        return getEntities().get(coordinate);
//    }
//
//    public boolean isGrass(Coordinate currentPosition) {
//        return getEntity(currentPosition) instanceof Grass;
//    }

    public boolean isGrass(Coordinate currentPosition) {
        return checkEntityByCoordinate(currentPosition) instanceof Grass;
    }

    public boolean isHerbivore(Coordinate currentPosition) {
        return checkEntityByCoordinate(currentPosition) instanceof Herbivore;
    }

    public boolean isTreeOrRock(Coordinate currentPosition) {
        return (checkEntityByCoordinate(currentPosition) instanceof Tree || checkEntityByCoordinate(currentPosition) instanceof Rock);
    }

    public boolean isCoordinatesOccupied(Coordinate targetCoordinates) {
        boolean isContain = false;

        if (targetCoordinates.findCellInMap(getEntities(), targetCoordinates.getX(), targetCoordinates.getY()) != null) {
            isContain = true;
        }

        return isContain;
    }

    public Map<Coordinate, Creature> getAllCreatures() {
        Map<Coordinate, Creature> creatures = new HashMap<>();

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();
            Coordinate entityCoordinate = entry.getKey();

            if (entity instanceof Creature creature) {
                creatures.put(entityCoordinate, creature);
            }
        }

        return creatures;
    }

    public void removeEntity(Coordinate coordinate) {
        entities.remove(coordinate);
    }

    public boolean isCoordinatesWithinMapBounds(Coordinate targetCoordinate) {
        int x = targetCoordinate.getX();
        int y = targetCoordinate.getY();

        return (x < SimulationMap.MAP_SIZE_X && x >= 0) && (y < SimulationMap.MAP_SIZE_Y && y >= 0);
    }
}
