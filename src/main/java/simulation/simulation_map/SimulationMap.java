package simulation.simulation_map;

import simulation.config.SimulationConfig;
import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.entities.objects.Grass;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static simulation.config.logging.LoggerMessages.MAP_FULL;

public class SimulationMap {
    private static final Logger logger = Logger.getLogger(SimulationMap.class.getName());

    private final Map<Coordinate, Entity> entities = new HashMap<>();

    private final List<MapChangeListener> listeners = new ArrayList<>();

    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for(MapChangeListener listener : listeners) {
            listener.onMapChange(this);
        }
    }

    public void moveEntity(Coordinate from, Coordinate to) {
        if (from != null) {
            Entity entity = entities.remove(from);
            addEntity(to, entity);
        }

        notifyListeners();
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        if (isMapFull()) {
            logger.log(Level.INFO, MAP_FULL);
        } else {
            entities.put(coordinate, entity);
        }
    }

    public int countEntitiesOfType(Class<? extends Entity> type) {
        int count = 0;
        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();
            if (type.isInstance(entity)) {
                count++;
            }
        }

        return count;
    }

    public boolean isMapFull() {
        int maxEntities = SimulationConfig.MAP_WIDTH * SimulationConfig.MAP_HEIGHT;

        return countEntitiesOfType(Entity.class) == maxEntities;
    }

    public Map<Coordinate, Entity> getEntities() {
        return Collections.unmodifiableMap(entities);
    }

    public Optional <Coordinate> generateRandomFreeCoordinate() {
        if (isMapFull()) {
            return Optional.empty();
        }

        Random random = new Random();
        int row;
        int column;

        do {
            row = random.nextInt((SimulationConfig.MAP_WIDTH));
            column = random.nextInt((SimulationConfig.MAP_HEIGHT));
        } while (isCoordinatesOccupied(new Coordinate(row, column)));

        return Optional.of(new Coordinate(row, column));
    }

    public boolean isGrass(Coordinate currentPosition) {
        return getEntities().get(currentPosition) instanceof Grass;
    }

    public boolean isHerbivore(Coordinate currentPosition) {
        return getEntities().get(currentPosition) instanceof Herbivore;
    }

    public boolean isCoordinatesOccupied(Coordinate targetCoordinate) {
        validateCoordinate(targetCoordinate);

        if (!isCoordinateWithinMapBounds(targetCoordinate)) {
            throw new IllegalArgumentException("Coordinate out of bound");
        }

        return getEntities().containsKey(targetCoordinate);
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

    public Optional<Entity> getEntityAt(Coordinate coordinate) {
        return Optional.ofNullable(coordinate).map(c -> getEntities().get(coordinate));
    }

    public Optional<Creature> getCreatureAt(Coordinate coordinate) {
        Entity entity = getEntities().get(coordinate);

        if (entity instanceof Creature creature) {
            return Optional.of(creature);
        } else {
            return Optional.empty();
        }
    }

    public void removeEntity(Coordinate coordinate) {
        entities.remove(coordinate);

        notifyListeners();
    }

    public boolean isCoordinateWithinMapBounds(Coordinate targetCoordinate) {
        validateCoordinate(targetCoordinate);

        int row = targetCoordinate.row();
        int column = targetCoordinate.column();

        return (row < SimulationConfig.MAP_WIDTH && row >= 0) && (column < SimulationConfig.MAP_HEIGHT && column >= 0);
    }

    public void validateCoordinate(Coordinate targetCoordinate) {
        if (targetCoordinate == null) {
            throw new IllegalArgumentException("Coordinate must not be null");
        }
    }
}
