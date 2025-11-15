package simulation.renderer;

import simulation.entities.Entity;
import simulation.renderer.icon.IconProvider;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.Arrays;
import java.util.Map;

public class ConsoleRenderer implements Renderer {
    private final IconProvider iconProvider;
    private final String[][] field;

    public ConsoleRenderer(int mapSizeRow, int mapSizeColumn, IconProvider iconProvider) {
        this.iconProvider = iconProvider;
        this.field = new String[mapSizeColumn][mapSizeRow];
    }

    @Override
    public void render(SimulationMap simulationMap) {
        initializeEmptyField();
        updateMap(simulationMap);
        printMap();
    }

    private void initializeEmptyField() {
        for (String[] strings : field) {
            Arrays.fill(strings, iconProvider.getEmptyCellIcon());
        }
    }

    private void updateMap(SimulationMap simulationMap) {
        for (Map.Entry<Coordinate, Entity> entry : simulationMap.getEntities().entrySet()) {
            Coordinate currentCoordinate = entry.getKey();
            Entity entity = entry.getValue();

            int row = currentCoordinate.row();
            int column = currentCoordinate.column();
            String entityIcon = iconProvider.getIcon(entity);

            field[column][row] = entityIcon;
        }
    }

    private void printMap() {
        StringBuilder line = new StringBuilder();

        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                line.append(field[i][j] + " ");
            }
            line.append("\n");
        }
        System.out.println(line);
    }
}
