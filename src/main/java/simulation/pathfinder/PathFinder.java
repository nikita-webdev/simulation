package simulation.pathfinder;

import simulation.entities.Entity;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.*;

public class PathFinder {
    private static final List<Coordinate> OFFSETS = List.of(
        new Coordinate(0, -1),
        new Coordinate(1, -1),
        new Coordinate(1, 0),
        new Coordinate(1, 1),
        new Coordinate(0, 1),
        new Coordinate(-1, 1),
        new Coordinate(-1, 0),
        new Coordinate(-1, -1)
    );

    public List<Coordinate> searchPath(SimulationMap simulationMap, Coordinate from, Class<? extends Entity> targetType) {
        Queue<Node> queue = new ArrayDeque<>();
        Set<Coordinate> visited = new HashSet<>();

        queue.add(new Node(from, null));
        visited.add(from);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Coordinate currentPosition = currentNode.getCoordinate();

            for (Coordinate neighbor : generateNeighboringCoordinates(currentPosition)) {
                if(!simulationMap.getMapBounds().isWithinMapBounds(neighbor)) {
                    continue;
                }

                if(visited.contains(neighbor)) {
                    continue;
                }

                Optional<Entity> entityTarget = simulationMap.getEntityAt(neighbor);
                if (entityTarget.isPresent() && targetType.isInstance(entityTarget.get())) {
                    return reconstructPath(new Node(neighbor, currentNode));
                }

                if (simulationMap.isCoordinateOccupied(neighbor)) {
                    continue;
                }

                visited.add(neighbor);
                queue.add(new Node(neighbor, currentNode));

            }
        }

        return List.of(from);
    }

    private List<Coordinate> reconstructPath(Node endNode) {
        LinkedList<Coordinate> path = new LinkedList<>();

        for (Node node = endNode; node != null; node = node.getParent()) {
            path.addFirst(node.getCoordinate());
        }

        if (!path.isEmpty()) {
            path.removeFirst();
        }

        return path;
    }

    private List<Coordinate> generateNeighboringCoordinates(Coordinate currentPosition) {
        List<Coordinate> neighboringCoordinates = new LinkedList<>();

        for (Coordinate offset : OFFSETS) {
            neighboringCoordinates.add(new Coordinate(
                    currentPosition.row() + offset.row(),
                    currentPosition.column() + offset.column()
            ));
        }

        return neighboringCoordinates;
    }
}
