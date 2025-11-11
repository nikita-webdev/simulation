package simulation.actions.turn_actions;

import simulation.actions.init_actions.SpawnAction;
import simulation.entities.Entity;
import simulation.simulation_map.SimulationMap;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class RespawnAction extends SpawnAction {
    private final Predicate<SimulationMap> thresholdCondition;

    public RespawnAction(Supplier<Entity> entitySupplier, int amount, Predicate<SimulationMap> thresholdCondition) {
        super(entitySupplier, amount);
        this.thresholdCondition = thresholdCondition;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        if (!thresholdCondition.test(simulationMap)) {
            return;
        }

        spawnEntity(simulationMap);
    }
}
