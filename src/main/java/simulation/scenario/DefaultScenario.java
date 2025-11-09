package simulation.scenario;

import simulation.Simulation;
import simulation.actions.Action;
import simulation.actions.init_actions.*;
import simulation.actions.turn_actions.*;
import simulation.config.SpawnConfig;
import simulation.simulation_map.SimulationMap;

import java.util.List;

public class DefaultScenario implements SimulationScenario {
    @Override
    public Simulation create() {
        SimulationMap defaultMap = new SimulationMap();

        List<Action> initActions = List.of(
                new SpawnGrassAction(SpawnConfig.INITIAL_GRASS),
                new SpawnRockAction(SpawnConfig.INITIAL_ROCKS),
                new SpawnTreeAction(SpawnConfig.INITIAL_TREES),
                new SpawnPredatorAction(SpawnConfig.INITIAL_PREDATORS),
                new SpawnHerbivoreAction(SpawnConfig.INITIAL_HERBIVORES)
            );

        List<Action> turnActions = List.of(
                new RespawnGrassAction(),
                new RespawnHerbivoreAction()
            );

        return new Simulation(defaultMap, initActions, turnActions);
    }
}
