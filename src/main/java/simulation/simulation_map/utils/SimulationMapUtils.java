package simulation.simulation_map.utils;

import simulation.config.SimulationConfig;
import simulation.entities.Entity;
import simulation.coordinate.Coordinate;
import simulation.simulation_map.SimulationMap;
import simulation.simulation_map.SimulationMapBounds;

import java.util.Optional;
import java.util.Random;

public final class SimulationMapUtils {
    private SimulationMapUtils() {
    }

    public static <T extends Entity> int countEntitiesOfType(SimulationMap simulationMap, Class<T> entityType) {
        int count = 0;
        for (Entity entity : simulationMap.getEntities().values()) {
            if (entityType.isInstance(entity)) {
                count++;
            }
        }

        return count;
    }

    public static boolean isMapFull(SimulationMap simulationMap, SimulationMapBounds simulationMapBounds) {
        int maxEntities = simulationMapBounds.getWidth() * simulationMapBounds.getHeight();

        return SimulationMapUtils.countEntitiesOfType(simulationMap, Entity.class) == maxEntities;
    }

    public static Optional<Coordinate> generateRandomFreeCoordinate(SimulationMap simulationMap, SimulationMapBounds simulationMapBounds) {
        if (SimulationMapUtils.isMapFull(simulationMap, simulationMapBounds)) {
            return Optional.empty();
        }

        Random random = new Random();
        int row;
        int column;

        do {
            row = random.nextInt((SimulationConfig.MAP_WIDTH));
            column = random.nextInt((SimulationConfig.MAP_HEIGHT));
        } while (!simulationMapBounds.isWithinMapBounds(new Coordinate(row, column)));

        return Optional.of(new Coordinate(row, column));
    }
}
