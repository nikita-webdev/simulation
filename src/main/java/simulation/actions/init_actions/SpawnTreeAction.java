package simulation.actions.init_actions;

import simulation.actions.Action;
import simulation.entities.objects.Tree;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class SpawnTreeAction implements Action {
    private final int initialTreeAmount;

    public SpawnTreeAction(int initialTreeAmount) {
        this.initialTreeAmount = initialTreeAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialTreeAmount; i++) {
            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();

            simulationMap.addEntity(coordinate, new Tree("Tree" + (i + 1)));
        }
    }
}
