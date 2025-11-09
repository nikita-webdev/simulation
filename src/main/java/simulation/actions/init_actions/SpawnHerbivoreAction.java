package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.animals.Herbivore;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

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
