package simulation.actions.turn_actions;

import simulation.actions.Action;
import simulation.entities.Entity;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class RespawnAction implements Action {
    private final Supplier<Entity> entitySupplier;
    private final int amount;
    private final Predicate<SimulationMap> thresholdCondition;

    public RespawnAction(Supplier<Entity> entitySupplier, int amount, Predicate<SimulationMap> thresholdCondition) {
        this.entitySupplier = entitySupplier;
        this.amount = amount;
        this.thresholdCondition = thresholdCondition;
    }

    public void execute(SimulationMap simulationMap) {
        if (!thresholdCondition.test(simulationMap)) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            if (coordinate == null) {
                break;
            }

            Entity entity = entitySupplier.get();
            simulationMap.addEntity(coordinate, entity);
        }
    }
}
