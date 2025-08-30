package simulation.pathfinder;

import simulation.entities.animals.Predator;
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

    public List<Coordinate> searchPath(SimulationMap simulationMap, Creature creature) {
        Coordinate initialNode = new Coordinate(creature.getCoordinate().getX(), creature.getCoordinate().getY());
        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<String> visitedNodes = new HashSet<>();
        Map<String, Coordinate> path = new HashMap<>();
        List<Coordinate> pathToFood = new LinkedList<>();

        queue.add(initialNode);
        visitedNodes.add(Arrays.toString(new int[] {initialNode.getX(), initialNode.getY()}));

        while (!queue.isEmpty()) {
            Coordinate currentPosition = queue.poll();
            List<Coordinate> neighboringNodes = generateNeighboringNodes(currentPosition);

            for (Coordinate currentExploringNode : neighboringNodes) {
                String nodeIdentifier = Arrays.toString(new int[] {currentExploringNode.getX(), currentExploringNode.getY()});
                boolean isWithinBounds = simulationMap.isCoordinatesWithinMapBounds(currentExploringNode);
                boolean isObstacle = simulationMap.isTreeOrRock(currentExploringNode) || (creature instanceof Predator && simulationMap.isGrass(currentExploringNode));

                if(!isWithinBounds || visitedNodes.contains(nodeIdentifier)) {
                    continue;
                }

                if (creature.getCoordinate().isFood(simulationMap, creature, currentExploringNode)) {
                    path.put(Arrays.toString(new int[] {currentExploringNode.getX(), currentExploringNode.getY()}), currentPosition);
                    pathToFood = reconstructPath(path, currentExploringNode);
                    return pathToFood;
                }

                if (isObstacle) {
                    visitedNodes.add(nodeIdentifier);
                } else {
                    if (!path.containsKey(Arrays.toString(new int[] {currentExploringNode.getX(), currentExploringNode.getY()}))) {
                        path.put(Arrays.toString(new int[] {currentExploringNode.getX(), currentExploringNode.getY()}), currentPosition);
                    }

                    queue.add(currentExploringNode);
                    visitedNodes.add(nodeIdentifier);
                }
            }
        }

        pathToFood.add(new Coordinate(creature.getCoordinate().getX(), creature.getCoordinate().getY()));
        return pathToFood;
    }

    public List<Coordinate> reconstructPath(Map<String, Coordinate> parentMap, Coordinate goalCoordinate) {
        List<Coordinate> path = new LinkedList<>();

        for (Coordinate i = new Coordinate(goalCoordinate.getX(), goalCoordinate.getY()); i != null; i = parentMap.get(Arrays.toString(new int[] {i.getX(), i.getY()}))) {
            Coordinate coordinate = new Coordinate(i.getX(), i.getY());
            path.add(coordinate);
        }

        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private List<Coordinate> generateNeighboringNodes(Coordinate currentPosition) {
        List<Coordinate> childNodes = new LinkedList<>();

        for (int i = 0; i < offsets.length; i++) {
            childNodes.add(new Coordinate(currentPosition.getX() + offsets[i][0], currentPosition.getY() + offsets[i][1]));
        }

        return childNodes;
    }
}
