package simulation.actions.init_actions;

import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.simulation_map.SimulationMap;

public class SpawnGrassAction extends SpawnAction {
    public SpawnGrassAction(int count) {
        super(count);
    }

    @Override
    protected Entity createEntity(int index, SimulationMap simulationMap) {
        return new Grass("Grass" + (index + 1));
    }
}
