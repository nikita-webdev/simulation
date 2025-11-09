package simulation.actions.init_actions;

import simulation.entities.Entity;
import simulation.entities.animals.Predator;
import simulation.simulation_map.SimulationMap;

public class SpawnPredatorAction extends SpawnAction {
    public SpawnPredatorAction(int count) {
        super(count);
    }

    @Override
    protected Entity createEntity(int index, SimulationMap simulationMap) {
        return new Predator("Predator" + (index + 1));
    }
}
