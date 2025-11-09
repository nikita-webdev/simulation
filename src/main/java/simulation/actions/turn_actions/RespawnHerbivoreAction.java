package simulation.actions.turn_actions;

import simulation.actions.Action;
import simulation.entities.animals.Herbivore;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class RespawnHerbivoreAction implements Action {
    private static final int initialHerbivoreAmount = 5;

    @Override
    public void execute(SimulationMap simulationMap) {
        int herbivoresCounter = simulationMap.getCountOfHerbivores();

        for (int i = 0; i < initialHerbivoreAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Herbivore("Herbivore" + (herbivoresCounter + 1)));
            herbivoresCounter = simulationMap.getCountOfHerbivores();
        }
    }
}
