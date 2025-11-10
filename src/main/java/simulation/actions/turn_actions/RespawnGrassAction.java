//package simulation.actions.turn_actions;
//
//import simulation.actions.init_actions.SpawnAction;
//import simulation.config.spawn.SpawnConfig;
//import simulation.entities.Entity;
//import simulation.entities.objects.Grass;
//import simulation.simulation_map.Coordinate;
//import simulation.simulation_map.SimulationMap;
//
//public class RespawnGrassAction extends SpawnAction {
//    public RespawnGrassAction() {
//        super(SpawnConfig.RESPAWN_GRASS);
//    }
//
//    @Override
//    public void execute(SimulationMap simulationMap) {
//        int grassCount = simulationMap.getCountOfGrass();
//
//        for (int i = 0; i < SpawnConfig.RESPAWN_GRASS; i++) {
//            Coordinate coordinate = simulationMap.generateRandomFreeCoordinate();
//            Entity entity = createEntity(simulationMap, grassCount + i);
//
//            simulationMap.addEntity(coordinate, entity);
//
//            grassCount = simulationMap.getCountOfGrass();
//        }
//    }
//
//    @Override
//    protected Entity createEntity(SimulationMap simulationMap, int index) {
//        return new Grass("Grass" + (index + 1));
//    }
//}
