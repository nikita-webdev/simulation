package simulation.actions;

import simulation.SimulationMap;
import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.objects.Grass;
import simulation.objects.Rock;
import simulation.objects.Tree;

import java.util.Random;

public class InitObjects {
    private SimulationMap map = SimulationMap.getInstance();

    public InitObjects() {

    }

    // Generate random coordinates for an object
    private int[] generateRandomCoordinates() {
        int[] xy = new int[2];
        Random random = new Random();

        do {
            xy[0] = random.nextInt((SimulationMap.MAP_SIZE_X));
            xy[1] = random.nextInt((SimulationMap.MAP_SIZE_Y));
        } while (map.isCoordinatesOccupied(xy));

        return xy;
    }

    public void initObjectsOnTheMap(int numberOfHerbivores, int numberOfPredators, int numberOfGrasses, int numberOfTrees, int numberOfRocks) {
        initGrass(numberOfGrasses);
        initTrees(numberOfTrees);
        initRocks(numberOfRocks);
        initHerbivore(numberOfHerbivores);
        initPredator(numberOfPredators);
    }

    // Add grass to map
    public void initGrass(int number) {
        // Quantity counter for assigning a number
        int amountOfGrasses = map.getAllGrassesCoordinatesForRemove().size();

        for (int i = 0; i < number; i++) {
            // Random coordinates are checked for a match in the map. If there is no match, the object is added to these coordinates.
            int[] xyGrass = generateRandomCoordinates();

            map.addEntity(new Grass("grass" + (amountOfGrasses + 1), xyGrass[0], xyGrass[1]));
            map.setAllEntityCoordinates(new int[] {xyGrass[0], xyGrass[1]});
            amountOfGrasses++;
        }
    }

    // Add trees to the map
    public void initTrees(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyTree = generateRandomCoordinates();

            map.addEntity(new Tree("tree" + (i + 1), xyTree[0], xyTree[1]));
            map.addAllTreesCoordinates(new int[] {xyTree[0], xyTree[1]});
            map.setAllEntityCoordinates(new int[] {xyTree[0], xyTree[1]});
        }
    }

    // Add rocks to the map
    public void initRocks(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyRock = generateRandomCoordinates();

            map.addEntity(new Rock("rock" + (i + 1), xyRock[0], xyRock[1]));
            map.addAllRocksCoordinates(new int[] {xyRock[0], xyRock[1]});
            map.setAllEntityCoordinates(new int[] {xyRock[0], xyRock[1]});
        }
    }

    // Add herbivores to the map
    public void initHerbivore(int number) {
        int numberHerbivores = 0;
        if (map.getAllHerbivoresCoordinatesForRemove().isEmpty()) {
            numberHerbivores = 0;
        } else if (!map.getAllHerbivoresCoordinatesForRemove().isEmpty()) {
            numberHerbivores = map.getAllHerbivoresCoordinatesForRemove().size();
        }

        for (int i = 0; i < number; i++) {
            int[] xyHerbivore = generateRandomCoordinates();

            map.addEntity(new Herbivore("herbivore" + (numberHerbivores + 1), xyHerbivore[0], xyHerbivore[1]));
            map.setAllEntityCoordinates(new int[] {xyHerbivore[0], xyHerbivore[1]});
            numberHerbivores = map.getAllHerbivoresCoordinatesForRemove().size();
        }
    }

    // Add predators to the map
    public void initPredator(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyPredator = generateRandomCoordinates();

            map.addEntity(new Predator("predator" + (i + 1), xyPredator[0], xyPredator[1]));
            map.setAllEntityCoordinates(new int[] {xyPredator[0], xyPredator[1]});
        }
    }
}
