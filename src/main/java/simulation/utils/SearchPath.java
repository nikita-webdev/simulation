package simulation.utils;

import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.*;

public class SearchPath {
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

    public List<int[]> searchPath(SimulationMap simulationMap, Creature creature) {
        List<int[]> pathToFood = new LinkedList<>();
        int[] foodNode = new int[] {creature.coordinates[0], creature.coordinates[1]};

        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[]> parentMap = new HashMap<>();

        int[] previousChildCoordinates = new int[2];

        graphsQueue.add(foodNode);
        visited.add(Arrays.toString(foodNode));

        while (!graphsQueue.isEmpty()) {
            int[] currentPosition = graphsQueue.poll();
            int[][] childNodes = generateChildNodes(currentPosition);

            for (int[] child : childNodes) {
                String childCoordinatesIdentifier = Arrays.toString(child);

                if (!visited.contains(childCoordinatesIdentifier)) {
                    boolean childCoordinatesValidation = simulationMap.isCoordinatesWithinMapBounds(child);

                    if (childCoordinatesValidation) {
                        if (isFood(simulationMap, creature, child)) {
                            graphsQueue.add(child);
                            visited.add(childCoordinatesIdentifier);
                            foodNode = child;
                            parentMap.put(Arrays.toString(foodNode), previousChildCoordinates);
                            pathToFood = reconstructPath(parentMap, foodNode);
                            return pathToFood;
                        } else if (simulationMap.isTreeOrRock(child)) {
                            visited.add(childCoordinatesIdentifier);
                        } else if (creature instanceof Predator && simulationMap.isGrass(child)) {
                            visited.add(childCoordinatesIdentifier);
                        } else {
                            if (!parentMap.containsKey(Arrays.toString(child))) {
                                parentMap.put(Arrays.toString(child), currentPosition);
                                previousChildCoordinates = child;
                            }

                            graphsQueue.add(child);
                            visited.add(childCoordinatesIdentifier);
                        }
                    } else {
                        visited.add(childCoordinatesIdentifier);
                    }
                }
            }
        }

        pathToFood.add(new int[] {creature.coordinates[0], creature.coordinates[1]});
        return pathToFood;
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

    public boolean isFood(SimulationMap simulationMap, Creature creature, int[] node) {
        boolean isFood = false;

        if (creature instanceof Herbivore) {
            isFood = simulationMap.isGrass(node);
        } else if (creature instanceof Predator) {
            isFood = simulationMap.isHerbivore(node);
        }

        return isFood;
    }
}
