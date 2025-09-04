package simulation.actions.initActions;

import simulation.map.SimulationMap;

public class InitObjects {
    private final SpawnGrassAction spawnGrassAction = new SpawnGrassAction(40);
    private final SpawnRockAction spawnRockAction = new SpawnRockAction(5);
    private final SpawnTreeAction spawnTreeAction = new SpawnTreeAction(5);
    private final SpawnPredatorAction spawnPredatorAction = new SpawnPredatorAction(2);
    private final SpawnHerbivoreAction spawnHerbivoreAction = new SpawnHerbivoreAction(5);

    public void initObjectsOnTheMap(SimulationMap simulationMap) {
        spawnGrassAction.execute(simulationMap);
        spawnRockAction.execute(simulationMap);
        spawnTreeAction.execute(simulationMap);
        spawnPredatorAction.execute(simulationMap);
        spawnHerbivoreAction.execute(simulationMap);
    }
}
