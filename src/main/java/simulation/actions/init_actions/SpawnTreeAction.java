package simulation.actions.init_actions;

import simulation.entities.Entity;
import simulation.entities.objects.Tree;
import simulation.simulation_map.SimulationMap;

public class SpawnTreeAction extends SpawnAction {
    public SpawnTreeAction(int count) {
        super(count);
    }

    @Override
    protected Entity createEntity(SimulationMap simulationMap, int index) {
        return new Tree("Tree" + (index + 1));
    }


}
