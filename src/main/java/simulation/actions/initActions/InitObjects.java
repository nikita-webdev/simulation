package simulation.actions.initActions;

import simulation.map.SimulationMap;

public class InitObjects {
    private SimulationMap simulationMap = SimulationMap.getInstance();

    private final SpawnGrassAction spawnGrassAction = new SpawnGrassAction(20);
    private final SpawnRockAction spawnRockAction = new SpawnRockAction(0);
    private final SpawnTreeAction spawnTreeAction = new SpawnTreeAction(0);
    private final SpawnPredatorAction spawnPredatorAction = new SpawnPredatorAction(0);
    private final SpawnHerbivoreAction spawnHerbivoreAction = new SpawnHerbivoreAction(1);

    public InitObjects() {

    }

    public void initObjectsOnTheMap() {
        spawnGrassAction.execute(simulationMap);
        spawnRockAction.execute(simulationMap);
        spawnTreeAction.execute(simulationMap);
        spawnPredatorAction.execute(simulationMap);
        spawnHerbivoreAction.execute(simulationMap);
    }
}
