package simulation.actions.turnActions;

import simulation.actions.Action;
import simulation.entities.animals.Herbivore;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class RespawnHerbivoreAction implements Action {
    private static final int initialHerbivoreAmount = 5;

    @Override
    public void execute(SimulationMap simulationMap) {
        int herbivoresCounter = simulationMap.getCountOfHerbivores();

        for (int i = 0; i < initialHerbivoreAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Herbivore("herbivore" + (herbivoresCounter + 1)));
            herbivoresCounter = simulationMap.getCountOfHerbivores();
        }
    }
}
