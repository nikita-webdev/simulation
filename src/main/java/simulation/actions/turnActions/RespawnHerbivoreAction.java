package simulation.actions.turnActions;

import simulation.actions.Action;
import simulation.entities.animals.Herbivore;
import simulation.map.Cell;
import simulation.map.SimulationMap;

public class RespawnHerbivoreAction implements Action {
    private static final int initialHerbivoreAmount = 10;

    @Override
    public void execute(SimulationMap simulationMap) {
        int herbivoresCounter = simulationMap.getCountOfHerbivores();

        for (int i = 0; i < initialHerbivoreAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Cell cell = new Cell(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Herbivore(cell, "herbivore" + (herbivoresCounter + 1)));
            herbivoresCounter = simulationMap.getCountOfHerbivores();
        }
    }
}
