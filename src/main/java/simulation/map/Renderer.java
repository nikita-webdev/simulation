package simulation.map;

import simulation.entities.Entity;

import java.util.Arrays;
import java.util.Map;

import static simulation.config.Icons.EMPTY_ICON;

public class Renderer {
    int mapSizeRow = SimulationMap.MAP_SIZE_ROW;
    int mapSizeColumn = SimulationMap.MAP_SIZE_COLUMN;
    String[][] field;

    public Renderer() {
        this.field = new String[mapSizeColumn][mapSizeRow];
    }

    private void createMap() {
        for(int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], EMPTY_ICON);
        }
    }

    public void renderMap(SimulationMap simulationMap) {
        createMap();
        updateMap(simulationMap);
        printMap();
    }

    private void updateMap(SimulationMap simulationMap) {
        for (Map.Entry<Coordinate, Entity> entry : simulationMap.getEntities().entrySet()) {
            Coordinate currentCoordinate = entry.getKey();
            Entity entity = entry.getValue();

            int row = currentCoordinate.row();
            int column = currentCoordinate.column();
            String entityIcon = entity.icon;

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
