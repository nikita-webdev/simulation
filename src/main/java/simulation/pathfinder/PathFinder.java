package simulation.pathfinder;

import simulation.map.Coordinate;
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

    public List<Coordinate> searchPath(SimulationMap simulationMap, Creature creature, Coordinate from) {
        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<Coordinate> visitedNodes = new HashSet<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        List<Coordinate> path = new LinkedList<>();

        queue.add(from);
        visitedNodes.add(from);

        while (!queue.isEmpty()) {
            Coordinate currentPosition = queue.poll();
            List<Coordinate> neighboringNodes = generateNeighboringNodes(currentPosition);

            for (Coordinate currentNeighbor : neighboringNodes) {
                boolean isWithinBounds = simulationMap.isCoordinateWithinMapBounds(currentNeighbor);

                if(!isWithinBounds || visitedNodes.contains(currentNeighbor)) {
                    continue;
                }

                if (simulationMap.isFood(creature, currentNeighbor)) {
                    cameFrom.put(currentNeighbor, currentPosition);
                    path = reconstructPath(cameFrom, currentNeighbor);
                    return path;
                }

                if (creature.isObstacle(simulationMap, currentNeighbor)) {
                    visitedNodes.add(currentNeighbor);
                } else {
                    if (!cameFrom.containsKey(currentNeighbor)) {
                        cameFrom.put(currentNeighbor, currentPosition);
                    }

                    queue.add(currentNeighbor);
                    visitedNodes.add(currentNeighbor);
                }
            }
        }

        path.add(new Coordinate(from.getX(), from.getY()));
        return path;
    }

    public List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate food) {
        List<Coordinate> path = new LinkedList<>();

        for (Coordinate i = new Coordinate(food.getX(), food.getY()); i != null; i = cameFrom.get(i)) {
            Coordinate coordinate = new Coordinate(i.getX(), i.getY());
            path.add(coordinate);
        }

        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private List<Coordinate> generateNeighboringNodes(Coordinate currentPosition) {
        List<Coordinate> neighboringNodes = new LinkedList<>();

        for (int i = 0; i < offsets.length; i++) {
            neighboringNodes.add(new Coordinate(currentPosition.getX() + offsets[i][0], currentPosition.getY() + offsets[i][1]));
        }

        return neighboringNodes;
    }
}
