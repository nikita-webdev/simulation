package simulation.actions.turn_actions;

import simulation.actions.init_actions.SpawnAction;
import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class RespawnGrassAction extends SpawnAction {
    private static final int RESPAWN_COUNT = 10;

    public RespawnGrassAction() {
        super(RESPAWN_COUNT);
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        int grassCount = simulationMap.getCountOfGrass();

        for (int i = 0; i < RESPAWN_COUNT; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();
            Entity entity = createEntity(grassCount + i, simulationMap);

            simulationMap.addEntity(coordinate, entity);

            grassCount = simulationMap.getCountOfGrass();
        }
    }

    @Override
    protected Entity createEntity(int index, SimulationMap simulationMap) {
        return new Grass("Grass" + (index + 1));
    }
}
