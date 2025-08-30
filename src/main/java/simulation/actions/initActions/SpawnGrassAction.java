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
            // Random coordinates are checked for a match in the map. If there is no match, the object is added to these coordinates.
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate cell = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Grass("grass" + (i + 1)));
        }
    }
}
