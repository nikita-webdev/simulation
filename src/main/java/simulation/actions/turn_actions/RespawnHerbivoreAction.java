package simulation.actions.turn_actions;

import simulation.actions.init_actions.SpawnAction;
import simulation.entities.Entity;
import simulation.entities.animals.Herbivore;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class RespawnHerbivoreAction extends SpawnAction {
    private static final int RESPAWN_COUNT = 5;

    public RespawnHerbivoreAction() {
        super(RESPAWN_COUNT);
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        int herbivoresCount = simulationMap.getCountOfHerbivores();

        for (int i = 0; i < RESPAWN_COUNT; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();
            Entity entity = createEntity(herbivoresCount + i, simulationMap);

            simulationMap.addEntity(coordinate, entity);

            herbivoresCount = simulationMap.getCountOfHerbivores();
        }
    }

    @Override
    protected Entity createEntity(int index, SimulationMap simulationMap) {
        return new Herbivore("Herbivore" + (index + 1));
    }
}
