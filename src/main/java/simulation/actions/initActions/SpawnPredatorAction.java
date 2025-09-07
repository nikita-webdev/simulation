package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.animals.Predator;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class SpawnPredatorAction implements Action {
    private final int initialPredatorAmount;

    public SpawnPredatorAction(int initialPredatorAmount) {
        this.initialPredatorAmount = initialPredatorAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialPredatorAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Predator("Predator" + (i + 1)));
        }
    }
}
