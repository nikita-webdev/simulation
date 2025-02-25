package simulation.utils;

import simulation.SimulationMap;
import simulation.animals.Creature;

import java.util.*;

public class SearchPath {
    private SimulationMap map = SimulationMap.getInstance();

    public int[] searchPath(Creature creature) {
        int[] goalNode = new int[2];

        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[]> parentMap = new HashMap<>();

        int[] lastChild = new int[2];

        int[] startPosition = new int[] {creature.positionX, creature.positionY};
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
                    if ((child[0] < SimulationMap.MAP_SIZE_X && child[0] >= 0) && (child[1] < SimulationMap.MAP_SIZE_Y && child[1] >= 0)) {
                        if (isEat(creature, child)) {
//                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childKey);
                            goalNode = child;
                            parentMap.put(Arrays.toString(goalNode), lastChild);
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
        creature.pathToGoal = reconstructPath(parentMap, goalNode);
        return goalNode;
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

    public boolean isEat(Creature creature, int[] node) {
        boolean isEat = false;

        if (creature.groupName.equals("herbivore")) {
            isEat = map.isGrass(node);
        } else if (creature.groupName.equals("predator")) {
            isEat = map.isHerbivore(node);
        }

        return isEat;
    }
}