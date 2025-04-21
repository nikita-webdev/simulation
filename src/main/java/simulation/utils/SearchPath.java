package simulation.utils;

import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.*;

public class SearchPath {
    private SimulationMap map = SimulationMap.getInstance();

    int[][] offsets = {
            {0, -1},
            {1, -1},
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0},
            {-1, -1}
    };

    public int[] searchPath(Creature creature) {
        int[] foodNode = new int[] {creature.coordinates[0], creature.coordinates[1]};

        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[]> parentMap = new HashMap<>();

        int[] previousChildCoordinates = new int[2];

        int[] startPosition = new int[] {creature.coordinates[0], creature.coordinates[1]};
        graphsQueue.add(startPosition);
        visited.add(Arrays.toString(startPosition));

        boolean foodIsFind = false;

        while (!graphsQueue.isEmpty() && !foodIsFind) {
            int[] currentPosition = graphsQueue.poll();
            int[][] childNodes = generateChildNodes(currentPosition);

            for (int[] child : childNodes) {
                String childCoordinatesKey = Arrays.toString(child);

                if (!visited.contains(childCoordinatesKey)) {
                    boolean childCoordinatesValidation = isCoordinatesWithinMapBounds(child);

                    if (childCoordinatesValidation) {
                        if (isFood(creature, child)) {
//                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childCoordinatesKey);
                            foodNode = child;
                            parentMap.put(Arrays.toString(foodNode), previousChildCoordinates);
                            foodIsFind = true;
                            break;
                        } else if (map.isTreeOrRock(child)) {
                            visited.add(childCoordinatesKey);
                        } else {
                            if (!parentMap.containsKey(Arrays.toString(child))) {
                                parentMap.put(Arrays.toString(child), currentPosition);
                                previousChildCoordinates = child;
                            }

                            graphsQueue.add(child);
                            visited.add(childCoordinatesKey);
                        }
                    } else {
                        visited.add(childCoordinatesKey);
                    }
                }
            }
        }
        creature.pathToFood = reconstructPath(parentMap, foodNode);
        return foodNode;
    }

    public List<int[]> reconstructPath(Map<String, int[]> parentMap, int[] goalCoordinates) {
        List<int[]> path = new LinkedList<>();

        for (int[] i = goalCoordinates; i != null; i = parentMap.get(Arrays.toString(i))) {
            path.add(i);
        }

        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private int[][] generateChildNodes(int[] currentPosition) {
        int[][] childNodes = new int[offsets.length][];

        for (int i = 0; i < offsets.length; i++) {
            childNodes[i] = new int[] {
                    currentPosition[0] + offsets[i][0],
                    currentPosition[1] + offsets[i][1]
            };
        }

        return childNodes;
    }

    private boolean isCoordinatesWithinMapBounds(int[] targetCoordinates) {
        int x = targetCoordinates[0];
        int y = targetCoordinates[1];

        return (x < SimulationMap.MAP_SIZE_X && x >= 0) && (y < SimulationMap.MAP_SIZE_Y && y >= 0);
    }

    public boolean isFood(Creature creature, int[] node) {
        boolean isFood = false;

        if (creature instanceof Herbivore) {
            isFood = map.isGrass(node);
        } else if (creature instanceof Predator) {
            isFood = map.isHerbivore(node);
        }

        return isFood;
    }
}
