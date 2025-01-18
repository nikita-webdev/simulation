package simulation;


import java.util.Arrays;

public class Renderer {
    int mapSizeX;
    int mapSizeY;
    String[][] matrix;

    public Renderer(int mapSizeX, int mapSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.matrix = new String[mapSizeX][mapSizeY];
    }

    public String[][] createMap() {
        for(int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], "..");
        }
        return matrix;
    }

    void renderMap() {
        createMap();
        // для примера вставляем H (Herbivore - травоядное) в матрицу
        matrix[1][1] = "H";

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
