package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class SpawnGrassAction implements Action {
    private final int initialGrassAmount;

    public SpawnGrassAction(int initialGrassAmount) {
        this.initialGrassAmount = initialGrassAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialGrassAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate coordinate = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(coordinate, new Grass("grass" + (i + 1)));
        }
    }
}
