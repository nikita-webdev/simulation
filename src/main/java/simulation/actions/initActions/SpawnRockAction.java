package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.objects.Rock;
import simulation.map.Cell;
import simulation.map.SimulationMap;

public class SpawnRockAction implements Action {
    private static final int initialRockAmount = 10;

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialRockAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Cell cell = new Cell(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Rock("rock" + (i + 1)));
        }
    }
}
