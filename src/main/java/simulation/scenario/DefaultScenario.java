package simulation.scenario;

import simulation.Simulation;
import simulation.actions.Action;
import simulation.actions.init_actions.*;
import simulation.actions.turn_actions.MoveAllCreatures;
import simulation.actions.turn_actions.RespawnAction;
import simulation.config.SimulationConfig;
import simulation.config.spawn.RespawnThresholds;
import simulation.config.spawn.SpawnConfig;
import simulation.entities.objects.*;
import simulation.entities.animals.*;
import simulation.simulation_map.SimulationMapBounds;
import simulation.simulation_map.SimulationMap;

import java.util.List;

public class DefaultScenario implements SimulationScenario {
    @Override
    public Simulation create() {
        SimulationMapBounds simulationMapBounds = new SimulationMapBounds(SimulationConfig.MAP_WIDTH, SimulationConfig.MAP_HEIGHT);
        SimulationMap defaultMap = new SimulationMap(simulationMapBounds);

        List<Action> initActions = List.of(
                new SpawnAction(() -> new Grass("Grass"), SpawnConfig.INITIAL_GRASS),
                new SpawnAction(() -> new Rock("Rock"), SpawnConfig.INITIAL_ROCKS),
                new SpawnAction(() -> new Tree("Tree"), SpawnConfig.INITIAL_TREES),
                new SpawnAction(() -> new Predator("Predator"), SpawnConfig.INITIAL_PREDATORS),
                new SpawnAction(() -> new Herbivore("Herbivore"), SpawnConfig.INITIAL_HERBIVORES)
        );

        List<Action> turnActions = List.of(
                new MoveAllCreatures(),
                new RespawnAction(() -> new Grass("Grass"), 10, RespawnThresholds.RESPAWN_GRASS),
                new RespawnAction(() -> new Herbivore("Herbivore"), 5, RespawnThresholds.RESPAWN_HERBIVORE)
            );

        return new Simulation(defaultMap, initActions, turnActions);
    }
}
