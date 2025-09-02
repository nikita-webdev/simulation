package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.entities.objects.Tree;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public class SpawnTreeAction implements Action {
    private final int initialTreeAmount;

    public SpawnTreeAction(int initialTreeAmount) {
        this.initialTreeAmount = initialTreeAmount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        for (int i = 0; i < initialTreeAmount; i++) {
            int[] xyCoordinates = simulationMap.generateRandomCoordinates();

            Coordinate coordinate = new Coordinate(xyCoordinates[0], xyCoordinates[1]);

            simulationMap.addEntity(coordinate, new Tree("tree" + (i + 1)));
        }
    }
}
