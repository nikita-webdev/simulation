package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.objects.Rock;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class SpawnRockAction implements Action {
    private final int initialRockAmount;

    public SpawnRockAction(int initialRockAmount) {
        this.initialRockAmount = initialRockAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialRockAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Rock("Rock" + (i + 1)));
        }
    }
}
