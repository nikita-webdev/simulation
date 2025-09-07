package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SimulationMap {
    private static final Logger logger = Logger.getLogger(SimulationMap.class.getName());
    private final Map<Coordinate, Entity> entities = new HashMap<>();
    Renderer renderer = new Renderer();

    public static final int DELAY_MOVE = 500;
    public static final int MAP_SIZE_ROW = 20;
    public static final int MAP_SIZE_COLUMN = 15;
    public static final String MESSAGE_MAP_FULL = "Unable to add new object. Maximum number of objects on the map reached.";

    public void addEntity(Coordinate coordinate, Entity entity) {
        if (isMapFull()) {
            logger.log(Level.INFO, MESSAGE_MAP_FULL);
        } else {
            entities.put(coordinate, entity);
        }
    }

    public boolean isMapFull() {
        int maxEntities = MAP_SIZE_ROW * MAP_SIZE_COLUMN;

        return getCountOfEntity() == maxEntities;
    }

    public Map<Coordinate, Entity> getEntities() {
        return Collections.unmodifiableMap(entities);
    }

    public Coordinate generateRandomFreeCoordinate() {
        Random random = new Random();
        int row;
        int column;

        do {
            row = random.nextInt((SimulationMap.MAP_SIZE_ROW));
            column = random.nextInt((SimulationMap.MAP_SIZE_COLUMN));
        } while (isCoordinatesOccupied(new Coordinate(row, column)));

        return new Coordinate(row, column);
    }

    public int getCountOfEntity() {
        return getEntities().size();
    }

    public int getCountOfGrass() {
        int countOfGrass = 0;

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();

            if (entity instanceof Grass) {
                countOfGrass++;
            }
        }

        return countOfGrass;
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

    public boolean isGrass(Coordinate currentPosition) {
        return getEntities().get(currentPosition) instanceof Grass;
    }

    public boolean isHerbivore(Coordinate currentPosition) {
        return getEntities().get(currentPosition) instanceof Herbivore;
    }

    public boolean isTreeOrRock(Coordinate currentPosition) {
        return (getEntities().get(currentPosition) instanceof Tree || getEntities().get(currentPosition) instanceof Rock);
    }

    public boolean isCoordinatesOccupied(Coordinate targetCoordinates) {
        return getEntities().containsKey(targetCoordinates);
    }

    public boolean isFood(Creature creature, Coordinate coordinate) {
        boolean isFood = false;

        if (creature instanceof Herbivore) {
            isFood = isGrass(coordinate);
        } else if (creature instanceof Predator) {
            isFood = isHerbivore(coordinate);
        }

        return isFood;
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

    public boolean isCoordinateWithinMapBounds(Coordinate targetCoordinate) {
        int row = targetCoordinate.row();
        int column = targetCoordinate.column();

        return (row < SimulationMap.MAP_SIZE_ROW && row >= 0) && (column < SimulationMap.MAP_SIZE_COLUMN && column >= 0);
    }

    public void updateMap() {
        renderer.renderMap(this);

        try {
            Thread.sleep(DELAY_MOVE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
