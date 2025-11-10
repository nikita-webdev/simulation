package simulation.config.spawn;

import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.simulation_map.SimulationMap;

import java.util.function.Predicate;

public class RespawnThresholds {
    public static final Predicate<SimulationMap> RESPAWN_GRASS = map ->
            map.countEntitiesOfType(Grass.class) < SpawnConfig.RESPAWN_GRASS_THRESHOLD;

    public static final Predicate<SimulationMap> RESPAWN_HERBIVORE = map ->
            map.countEntitiesOfType(Herbivore.class) < SpawnConfig.RESPAWN_HERBIVORE_THRESHOLD;
}
