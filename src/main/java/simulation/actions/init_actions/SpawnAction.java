package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.Entity;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public abstract class SpawnAction implements Action {
    private final int count;

    protected SpawnAction(int count) {
        this.count = count;
    }

    protected abstract Entity createEntity(SimulationMap simulationMap, int index);

    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < count; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();
            Entity entity = createEntity(simulationMap, i);

            simulationMap.addEntity(coordinate, entity);
        }
    }
}
