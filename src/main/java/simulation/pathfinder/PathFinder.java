package simulation.pathfinder;

import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.map.SimulationMap;
import simulation.entities.animals.Creature;

import java.util.*;

public class PathFinder {
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
        int[] initialNode = new int[] {creature.getCell().getX(), creature.getCell().getY()};
        Queue<int[]> queue = new ArrayDeque<>();
        Set<String> visitedNodes = new HashSet<>();
        Map<String, int[]> path = new HashMap<>();
        List<int[]> pathToFood = new LinkedList<>();

        queue.add(initialNode);
        visitedNodes.add(Arrays.toString(initialNode));

        while (!queue.isEmpty()) {
            int[] currentPosition = queue.poll();
            int[][] neighboringNodes = generateNeighboringNodes(currentPosition);

            for (int[] currentExploringNode : neighboringNodes) {
                String nodeIdentifier = Arrays.toString(currentExploringNode);
                boolean nodeCoordinatesValidation = simulationMap.isCoordinatesWithinMapBounds(currentExploringNode);

                if(!nodeCoordinatesValidation || visitedNodes.contains(nodeIdentifier)) {
                    continue;
                }

                if (isFood(simulationMap, creature, currentExploringNode)) {
                    path.put(Arrays.toString(currentExploringNode), currentPosition);
                    pathToFood = reconstructPath(path, currentExploringNode);
                    return pathToFood;
                }

                if (simulationMap.isTreeOrRock(currentExploringNode)) {
                    visitedNodes.add(nodeIdentifier);
                } else if (creature instanceof Predator && simulationMap.isGrass(currentExploringNode)) {
                    visitedNodes.add(nodeIdentifier);
                } else {
                    if (!path.containsKey(Arrays.toString(currentExploringNode))) {
                        path.put(Arrays.toString(currentExploringNode), currentPosition);
                    }

                    queue.add(currentExploringNode);
                    visitedNodes.add(nodeIdentifier);
                }
            }
        }

        pathToFood.add(new int[] {creature.getCell().getX(), creature.getCell().getY()});
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

    private int[][] generateNeighboringNodes(int[] currentPosition) {
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
