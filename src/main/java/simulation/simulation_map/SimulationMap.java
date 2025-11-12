package simulation.simulation_map;

import simulation.config.logging.LoggerMessages;
import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.simulation_map.utils.SimulationMapUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationMap {
    private static final Logger logger = Logger.getLogger(SimulationMap.class.getName());

    private final SimulationMapBounds simulationMapBounds;
    private final Map<Coordinate, Entity> entities = new HashMap<>();

    private final List<MapChangeListener> listeners = new ArrayList<>();

    public SimulationMap(SimulationMapBounds simulationMapBounds) {
        this.simulationMapBounds = simulationMapBounds;
    }

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
        if (SimulationMapUtils.isMapFull(this, getMapBounds())) {
            logger.log(Level.INFO, LoggerMessages.MAP_FULL);
        } else {
            entities.put(coordinate, entity);
        }
    }

    public Map<Coordinate, Entity> getEntities() {
        return Collections.unmodifiableMap(entities);
    }

    public boolean isCoordinateOccupied(Coordinate targetCoordinate) {
        if (targetCoordinate == null) {
            throw new IllegalArgumentException("Coordinate must not be null");
        }

        if (!simulationMapBounds.isWithinMapBounds(targetCoordinate)) {
            throw new IllegalArgumentException("Coordinate out of bound");
        }

        return getEntities().containsKey(targetCoordinate);
    }

    public <T extends Entity> Map<Coordinate, T> getEntitiesOfType(Class<T> entitiesType) {
        Map<Coordinate, T> result = new HashMap<>();

        for (Map.Entry<Coordinate, Entity> entry : getEntities().entrySet()) {
            Entity entity = entry.getValue();
            Coordinate coordinate = entry.getKey();

            if (entitiesType.isInstance(entity)) {
                result.put(coordinate, entitiesType.cast(entity));
            }
        }

        return result;
    }

    public Optional<Entity> getEntityAt(Coordinate coordinate) {
        if (coordinate == null) return Optional.empty();
        return Optional.ofNullable(getEntities().get(coordinate));
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

    public SimulationMapBounds getMapBounds() {
        return simulationMapBounds;
    }
}
