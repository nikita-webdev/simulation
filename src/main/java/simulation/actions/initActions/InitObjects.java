package simulation.actions.initActions;

import simulation.actions.Action;
import simulation.map.SimulationMap;

import java.util.List;

public class InitObjects {
    private final SpawnGrassAction spawnGrassAction = new SpawnGrassAction(40);
    private final SpawnRockAction spawnRockAction = new SpawnRockAction(5);
    private final SpawnTreeAction spawnTreeAction = new SpawnTreeAction(5);
    private final SpawnPredatorAction spawnPredatorAction = new SpawnPredatorAction(2);
    private final SpawnHerbivoreAction spawnHerbivoreAction = new SpawnHerbivoreAction(5);

    List<Action> spawnActions = List.of(spawnGrassAction, spawnRockAction, spawnTreeAction, spawnPredatorAction, spawnHerbivoreAction);

    public void initObjectsOnTheMap(SimulationMap simulationMap) {
        for (Action action : spawnActions) {
            action.execute(simulationMap);
        }
    }
}
