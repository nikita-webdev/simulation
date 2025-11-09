package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class SpawnGrassAction implements Action {
    private final int initialGrassAmount;

    public SpawnGrassAction(int initialGrassAmount) {
        this.initialGrassAmount = initialGrassAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialGrassAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Grass("Grass" + (i + 1)));
        }
    }
}
