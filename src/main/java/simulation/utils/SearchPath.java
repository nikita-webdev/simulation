package simulation.utils;

import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.*;

public class SearchPath {
    private SimulationMap map = SimulationMap.getInstance();

    public int[] searchPath(Creature creature) {
        int[] foodNode = creature.coordinates;

        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[]> parentMap = new HashMap<>();

        int[] lastChild = new int[2];

        int[] startPosition = new int[] {creature.coordinates[0], creature.coordinates[1]};
        graphsQueue.add(startPosition);
        visited.add(Arrays.toString(startPosition));

        boolean eatIsFind = false;

        while (!graphsQueue.isEmpty() && !eatIsFind) {
            int[] currentPosition = graphsQueue.poll();
            int[][] childNodes = new int[8][];

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

            for (int i = 0; i < offsets.length; i++) {
                childNodes[i] = new int[] {
                    currentPosition[0] + offsets[i][0],
                    currentPosition[1] + offsets[i][1]
                };
            }

            for (int[] child : childNodes) {
                String childKey = Arrays.toString(child);

                if (!visited.contains(childKey)) {
                    boolean childCoordinatesValidation = (child[0] < SimulationMap.MAP_SIZE_X && child[0] >= 0) && (child[1] < SimulationMap.MAP_SIZE_Y && child[1] >= 0);

                    if (childCoordinatesValidation) {
                        if (isFood(creature, child)) {
//                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childKey);
                            foodNode = child;
                            parentMap.put(Arrays.toString(foodNode), lastChild);
                            eatIsFind = true;
                            break;
                        } else if (map.isTreeOrRock(child)) {
                            visited.add(childKey);
                        } else {
                            if (!parentMap.containsKey(Arrays.toString(child))) {
                                parentMap.put(Arrays.toString(child), currentPosition);
                                lastChild = child;
                            }

                            graphsQueue.add(child);
                            visited.add(childKey);
                        }
                    } else {
                        visited.add(childKey);
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
