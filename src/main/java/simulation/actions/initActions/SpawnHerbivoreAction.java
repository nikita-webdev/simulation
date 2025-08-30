package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.animals.Herbivore;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class SpawnHerbivoreAction implements Action {
    private final int initialHerbivoreAmount;

    public SpawnHerbivoreAction(int initialHerbivoreAmount) {
        this.initialHerbivoreAmount = initialHerbivoreAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialHerbivoreAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate cell = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(cell, new Herbivore(cell, "herbivore" + (i + 1)));
        }
    }
}
