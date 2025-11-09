package simulation.actions.init_actions;

import simulation.entities.Entity;
import simulation.entities.objects.Rock;
import simulation.simulation_map.SimulationMap;

public class SpawnRockAction extends SpawnAction {
    public SpawnRockAction(int count) {
        super(count);
    }

    @Override
    protected Entity createEntity(int index, SimulationMap simulationMap) {
        return new Rock("Rock" + (index + 1));
    }
}
