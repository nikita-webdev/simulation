package simulation.renderer;

import simulation.entities.Entity;
import simulation.renderer.icon.IconProvider;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.Optional;

public class ConsoleRenderer implements Renderer {
    private final IconProvider iconProvider;

    public ConsoleRenderer(IconProvider iconProvider) {
        this.iconProvider = iconProvider;
    }

    @Override
    public void render(SimulationMap simulationMap) {
        int width = simulationMap.getMapBounds().getWidth();
        int height = simulationMap.getMapBounds().getHeight();

        for (int column = 0; column < height; column++) {
            for (int row = 0; row < width; row++) {
                Coordinate coordinate = new Coordinate(row, column);

                if (!simulationMap.isCoordinateOccupied(coordinate)) {
                    System.out.print(iconProvider.getEmptyCellIcon());
                } else {
                    Optional<Entity> entityOpt = simulationMap.getEntityAt(coordinate);

                    if (entityOpt.isPresent()) {
                        System.out.print(iconProvider.getIcon(entityOpt.get()));
                    }
                }
            }
            System.out.println();
        }
    }
}
