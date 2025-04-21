package simulation.map;

import simulation.entities.Entity;

import java.util.Arrays;
import java.util.Map;

import static simulation.config.Icons.EMPTY_ICON;

public class Renderer {
    private SimulationMap map = SimulationMap.getInstance();
    int mapSizeX = SimulationMap.MAP_SIZE_X;
    int mapSizeY = SimulationMap.MAP_SIZE_Y;
    String[][] field;

    public Renderer() {
//        this.mapSizeX = mapSizeX;
//        this.mapSizeY = mapSizeY;

        this.field = new String[mapSizeY][mapSizeX];
    }

    public void createMap() {
        for(int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], EMPTY_ICON);
        }
    }

    public void renderMap() {
        createMap();
        updateField();
        printField();
    }

    public void updateField() {
        // Update elements in the matrix
        for (Map.Entry<Cell, Entity> entry : map.map.entrySet()) {
            Cell currentCell = entry.getKey();
            Entity entity = entry.getValue();

            int x = currentCell.getX();
            int y = currentCell.getY();
            String entityIcon = entity.icon;

            field[y][x] = entityIcon;
        }
    }

    public void printField() {
        // Display the matrix on the screen
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
