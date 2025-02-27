package simulation;

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

    void createMap() {
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

    void updateMatrix() {
        // Update elements in the matrix
        for (Map.Entry<String, Entity> entry : map.map.entrySet()) {
            String currentEntityName = entry.getKey();
            int x = map.getEntityCoordinatesX(currentEntityName);
            int y = map.getEntityCoordinatesY(currentEntityName);
            String entityIcon = map.getEntityIcon(currentEntityName);

            matrix[y][x] = entityIcon;
        }
    }

    void printMatrix() {
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
