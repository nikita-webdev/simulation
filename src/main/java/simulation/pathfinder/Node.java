package simulation.pathfinder;

import simulation.simulation_map.Coordinate;

public class Node {
    private final Coordinate coordinate;
    private final Node parent;

    public Node(Coordinate coordinate, Node parent) {
        this.coordinate = coordinate;
        this.parent = parent;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Node getParent() {
        return parent;
    }
}
