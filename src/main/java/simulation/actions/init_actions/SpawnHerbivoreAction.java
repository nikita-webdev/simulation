package simulation.actions.init_actions;

import simulation.entities.Entity;
import simulation.entities.animals.Herbivore;
import simulation.simulation_map.SimulationMap;

public class SpawnHerbivoreAction extends SpawnAction{
    public SpawnHerbivoreAction(int amount) {
        super(amount);
    }

    @Override
    protected Entity createEntity(SimulationMap simulationMap, int index) {
        return new Herbivore("Herbivore" + (index + 1));
    }
}
