package simulation.scenario;

import simulation.Simulation;
import simulation.actions.Action;
import simulation.actions.init_actions.*;
import simulation.actions.turn_actions.MoveAllCreatures;
import simulation.actions.turn_actions.RespawnAction;
import simulation.config.SimulationConfig;
import simulation.config.entities.AnimalStats;
import simulation.config.spawn.RespawnThresholds;
import simulation.config.spawn.SpawnConfig;
import simulation.entities.objects.*;
import simulation.entities.animals.*;
import simulation.renderer.ConsoleRenderer;
import simulation.renderer.EntityType;
import simulation.renderer.Renderer;
import simulation.renderer.icon.EmojiIconProvider;
import simulation.simulation_map.SimulationMapBounds;
import simulation.simulation_map.SimulationMap;

import java.util.List;

public class DefaultScenario implements SimulationScenario {
    @Override
    public Simulation create() {
        SimulationMapBounds simulationMapBounds = new SimulationMapBounds(SimulationConfig.MAP_WIDTH, SimulationConfig.MAP_HEIGHT);
        SimulationMap defaultMap = new SimulationMap(simulationMapBounds);
        Renderer renderer = new ConsoleRenderer(new EmojiIconProvider());

        List<Action> initActions = List.of(
                new SpawnAction(() -> new Grass(EntityType.GRASS, "Grass"), SpawnConfig.INITIAL_GRASS),
                new SpawnAction(() -> new Rock(EntityType.ROCK, "Rock"), SpawnConfig.INITIAL_ROCKS),
                new SpawnAction(() -> new Tree(EntityType.TREE, "Tree"), SpawnConfig.INITIAL_TREES),
                new SpawnAction(() -> new Predator(EntityType.PREDATOR, "Predator", AnimalStats.DEFAULT_PREDATOR_ATTACK_POWER), SpawnConfig.INITIAL_PREDATORS),
                new SpawnAction(() -> new Herbivore(EntityType.HERBIVORE, "Herbivore"), SpawnConfig.INITIAL_HERBIVORES)
        );

        List<Action> turnActions = List.of(
                new MoveAllCreatures(),
                new RespawnAction(() -> new Grass(EntityType.GRASS, "Grass"), 10, RespawnThresholds.RESPAWN_GRASS),
                new RespawnAction(() -> new Herbivore(EntityType.HERBIVORE, "Herbivore"), 5, RespawnThresholds.RESPAWN_HERBIVORE)
            );

        return new Simulation(defaultMap, renderer, initActions, turnActions);
    }
}
