package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.animals.Predator;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

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
