package simulation.actions.turnActions;

import simulation.actions.Action;
import simulation.entities.objects.Grass;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class RespawnGrassAction implements Action {
    private static final int initialGrassAmount = 10;

    @Override
    public void execute(SimulationMap simulationMap) {
        int grassesCount = simulationMap.getCountOfGrasses();

        for (int i = 0; i < initialGrassAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate coordinate = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(coordinate, new Grass("grass" + (grassesCount + 1)));
            grassesCount++;
        }
    }
}
