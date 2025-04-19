package simulation.actions;

import simulation.map.Cell;
import simulation.map.SimulationMap;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

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
        int numberOfGrasses = map.getCountOfGrasses();

        for (int i = 0; i < number; i++) {
            // Random coordinates are checked for a match in the map. If there is no match, the object is added to these coordinates.
            int[] xyGrass = generateRandomCoordinates();

            map.addEntity(new Cell(xyGrass[0], xyGrass[1]), new Grass("grass" + (numberOfGrasses + 1), xyGrass[0], xyGrass[1]));
            numberOfGrasses++;
        }
    }

    // Add trees to the map
    public void initTrees(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyTree = generateRandomCoordinates();

            map.addEntity(new Cell(xyTree[0], xyTree[1]), new Tree("tree" + (i + 1), xyTree[0], xyTree[1]));
        }
    }

    // Add rocks to the map
    public void initRocks(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyRock = generateRandomCoordinates();

            map.addEntity(new Cell(xyRock[0], xyRock[1]), new Rock("rock" + (i + 1), xyRock[0], xyRock[1]));
        }
    }

    // Add herbivores to the map
    public void initHerbivore(int number) {
        int numberOfHerbivores = map.getCountOfHerbivores();

        for (int i = 0; i < number; i++) {
            int[] xyHerbivore = generateRandomCoordinates();

            Cell cell = new Cell(xyHerbivore[0], xyHerbivore[1]);

            map.addEntity(cell, new Herbivore(cell, "herbivore" + (numberOfHerbivores + 1)));
            numberOfHerbivores = map.getCountOfHerbivores();
        }
    }

    // Add predators to the map
    public void initPredator(int number) {
        for (int i = 0; i < number; i++) {
            int[] xyPredator = generateRandomCoordinates();

            Cell cell = new Cell(xyPredator[0], xyPredator[1]);

            map.addEntity(cell, new Predator(cell,"predator" + (i + 1)));
        }
    }
}
