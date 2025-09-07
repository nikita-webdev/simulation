package simulation.actions.turnActions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class RespawnGrassAction implements Action {
    private static final int initialGrassAmount = 10;

    @Override
    public void execute(SimulationMap simulationMap) {
        int grassCount = simulationMap.getCountOfGrass();

        for (int i = 0; i < initialGrassAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Grass("Grass" + (grassCount + 1)));
            grassCount++;
        }
    }
}
