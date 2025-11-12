package simulation.simulation_map;

public class SimulationMapBounds {
    private final int width;
    private final int height;

    public SimulationMapBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isWithinMapBounds(Coordinate targetCoordinate) {
        if (targetCoordinate == null) {
            throw new IllegalArgumentException("Coordinate must not be null");
        }

        int row = targetCoordinate.row();
        int column = targetCoordinate.column();

        return (row < width && row >= 0) && (column < height && column >= 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
