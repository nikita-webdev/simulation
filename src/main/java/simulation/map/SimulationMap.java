package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

import java.util.*;


public class SimulationMap {
    Renderer renderer = new Renderer();
    private final Map<Coordinate, Entity> entities = new HashMap<>();

    public static final int DELAY_MOVE = 500;
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
        int x = targetCoordinate.x();
        int y = targetCoordinate.y();

        return (x < SimulationMap.MAP_SIZE_X && x >= 0) && (y < SimulationMap.MAP_SIZE_Y && y >= 0);
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
