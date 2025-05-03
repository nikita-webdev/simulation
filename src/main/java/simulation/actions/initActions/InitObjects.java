package simulation.actions.initActions;

import simulation.map.SimulationMap;

public class InitObjects {
    private final SpawnGrassAction spawnGrassAction = new SpawnGrassAction(20);
    private final SpawnRockAction spawnRockAction = new SpawnRockAction(0);
    private final SpawnTreeAction spawnTreeAction = new SpawnTreeAction(0);
    private final SpawnPredatorAction spawnPredatorAction = new SpawnPredatorAction(1);
    private final SpawnHerbivoreAction spawnHerbivoreAction = new SpawnHerbivoreAction(1);

    public void initObjectsOnTheMap(SimulationMap simulationMap) {
        spawnGrassAction.execute(simulationMap);
        spawnRockAction.execute(simulationMap);
        spawnTreeAction.execute(simulationMap);
        spawnPredatorAction.execute(simulationMap);
        spawnHerbivoreAction.execute(simulationMap);
    }
}
