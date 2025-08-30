package simulation.actions.turnActions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class RespawnGrassAction implements Action {
    private static final int initialGrassAmount = 10;

    @Override
    public void execute(SimulationMap simulationMap) {
        // Quantity counter for assigning a number
        int grassesCount = simulationMap.getCountOfGrasses();

        for (int i = 0; i < initialGrassAmount; i++) {
            // Random coordinates are checked for a match in the map. If there is no match, the object is added to these coordinates.
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate cell = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Grass("grass" + (grassesCount + 1)));
            grassesCount++;
        }
    }
}
