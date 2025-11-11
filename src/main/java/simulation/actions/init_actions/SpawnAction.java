package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.Entity;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.Optional;
import java.util.function.Supplier;

public class SpawnAction implements Action {
    private final Supplier<Entity> entitySupplier;
    private final int amount;

    public SpawnAction(Supplier<Entity> entitySupplier, int amount) {
        this.entitySupplier = entitySupplier;
        this.amount = amount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        spawnEntity(simulationMap);
    }

    protected void spawnEntity(SimulationMap simulationMap) {
        for (int i = 0; i < amount; i++) {
            Optional <Coordinate> randomCoordinate = simulationMap.generateRandomFreeCoordinate();

            if (randomCoordinate.isPresent()) {
                Coordinate freeCoordinate = randomCoordinate.get();
                Entity entity = entitySupplier.get();
                simulationMap.addEntity(freeCoordinate, entity);
            }
        }
    }
}
