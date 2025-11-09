package simulation.actions.turn_actions;

import simulation.actions.init_actions.SpawnAction;
import simulation.config.SpawnConfig;
import simulation.entities.Entity;
import simulation.entities.animals.Herbivore;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class RespawnHerbivoreAction extends SpawnAction {
    public RespawnHerbivoreAction() {
        super(SpawnConfig.RESPAWN_HERBIVORE);
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        int herbivoresCount = simulationMap.getCountOfHerbivores();

        for (int i = 0; i < SpawnConfig.RESPAWN_HERBIVORE; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();
            Entity entity = createEntity(simulationMap, herbivoresCount + i);

            simulationMap.addEntity(coordinate, entity);

            herbivoresCount = simulationMap.getCountOfHerbivores();
        }
    }

    @Override
    protected Entity createEntity( SimulationMap simulationMap, int index) {
        return new Herbivore("Herbivore" + (index + 1));
    }
}
