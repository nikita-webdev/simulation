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
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Herbivore("Herbivore" + (i + 1)));
        }
    }
}
