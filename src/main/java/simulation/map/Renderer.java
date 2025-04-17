package simulation.map;

import simulation.entities.Entity;

import java.util.Arrays;
import java.util.Map;

public class Renderer {
    private SimulationMap map = SimulationMap.getInstance();
    int mapSizeX = SimulationMap.MAP_SIZE_X;
    int mapSizeY = SimulationMap.MAP_SIZE_Y;
    String[][] matrix;

    public Renderer() {
//        this.mapSizeX = mapSizeX;
//        this.mapSizeY = mapSizeY;

        this.matrix = new String[mapSizeY][mapSizeX];
    }

    public void createMap() {
        for(int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], "⬛");
        }
    }

    public void renderMap() {
        map.howMuchEntriesInMap();
        createMap();
        updateMatrix();
        printMatrix();
    }

    public void updateMatrix() {
        // Update elements in the matrix
        for (Map.Entry<Cell, Entity> entry : map.map.entrySet()) {
            Cell currentCell = entry.getKey();
            Entity entity = entry.getValue();

            int x = currentCell.getX();
            int y = currentCell.getY();
            String entityIcon = entity.icon;

            matrix[y][x] = entityIcon;
        }
    }

    public void printMatrix() {
        // Display the matrix on the screen
        StringBuilder line = new StringBuilder();

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                line.append(matrix[i][j] + " ");
            }
            line.append("\n");
        }
        System.out.println(line);
    }
}
