package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.Entity;
import simulation.coordinate.Coordinate;
import simulation.simulation_map.SimulationMap;
import simulation.simulation_map.utils.SimulationMapUtils;

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
            Optional <Coordinate> randomCoordinate = SimulationMapUtils.generateRandomFreeCoordinate(simulationMap, simulationMap.getMapBounds());

            if (randomCoordinate.isPresent()) {
                Coordinate freeCoordinate = randomCoordinate.get();
                Entity entity = entitySupplier.get();
                simulationMap.addEntity(freeCoordinate, entity);
            }
        }
    }
}
