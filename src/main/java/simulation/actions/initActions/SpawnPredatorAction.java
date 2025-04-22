package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.animals.Predator;
import simulation.map.Cell;
import simulation.map.SimulationMap;

public class SpawnPredatorAction implements Action {
    private static final int initialGrassAmount = 1;

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialGrassAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Cell cell = new Cell(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Predator(cell,"predator" + (i + 1)));
        }
    }
}
