package simulation.config.spawn;

import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.simulation_map.SimulationMap;
import simulation.simulation_map.utils.SimulationMapUtils;

import java.util.function.Predicate;

public final class RespawnThresholds {
    private RespawnThresholds() {
    }

    public static final Predicate<SimulationMap> RESPAWN_GRASS = new Predicate<>() {
        @Override
        public boolean test(SimulationMap simulationMap) {
            return SimulationMapUtils.countEntitiesOfType(simulationMap, Grass.class) < SpawnConfig.RESPAWN_GRASS_THRESHOLD;
        }
    };

    public static final Predicate<SimulationMap> RESPAWN_HERBIVORE = new Predicate<>() {
        @Override
        public boolean test(SimulationMap simulationMap) {
            return SimulationMapUtils.countEntitiesOfType(simulationMap, Herbivore.class) < SpawnConfig.RESPAWN_HERBIVORE_THRESHOLD;
        }
    };

}
