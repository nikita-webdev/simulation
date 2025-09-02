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

            Coordinate coordinate = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(coordinate, new Herbivore("herbivore" + (i + 1)));
        }
    }
}
