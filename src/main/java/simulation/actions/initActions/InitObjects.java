package simulation.actions.initActions;

import simulation.map.SimulationMap;

public class InitObjects {
    private SimulationMap simulationMap = SimulationMap.getInstance();

    private final SpawnGrassAction spawnGrassAction = new SpawnGrassAction();
    private final SpawnRockAction spawnRockAction = new SpawnRockAction();
    private final SpawnTreeAction spawnTreeAction = new SpawnTreeAction();
    private final SpawnHerbivoreAction spawnHerbivoreAction = new SpawnHerbivoreAction();
    private final SpawnPredatorAction spawnPredatorAction = new SpawnPredatorAction();

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
