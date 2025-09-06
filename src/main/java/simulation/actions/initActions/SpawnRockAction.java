package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.objects.Rock;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class SpawnRockAction implements Action {
    private final int initialRockAmount;

    public SpawnRockAction(int initialRockAmount) {
        this.initialRockAmount = initialRockAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialRockAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Rock("rock" + (i + 1)));
        }
    }
}
