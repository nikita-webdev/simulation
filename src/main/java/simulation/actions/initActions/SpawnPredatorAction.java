package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.animals.Predator;
import simulation.map.Cell;
import simulation.map.SimulationMap;

public class SpawnPredatorAction implements Action {
    private final int initialPredatorAmount;

    public SpawnPredatorAction(int initialPredatorAmount) {
        this.initialPredatorAmount = initialPredatorAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialPredatorAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Cell cell = new Cell(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Predator(cell,"predator" + (i + 1)));
        }
    }
}
