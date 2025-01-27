package simulation;

import simulation.actions.InitObjects;
import simulation.objects.Grass;

import java.util.Arrays;

public class Renderer {
    private SimulationMap map = SimulationMap.getInstance();
    int mapSizeX;
    int mapSizeY;
    String[][] matrix;

    public Renderer(int mapSizeX, int mapSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.matrix = new String[mapSizeY][mapSizeX];
    }

    /*
    * Поле - многомерный массив с индексами a=[0][0], b=[0][1] и т.д.
    * То есть, у объектов должно быть поле с их индексом на карте.
    */

    // сделать размер матрицы динамическим
    // чтобы, когда задаёшь число - матрица сама генерировалась нужного размера


    // Вот так создаём матрицу нужной размерности ТОЛЬКО ОДИН РАЗ - ПРИ СОЗДАНИИ ПОЛЯ
    // и при создании поля (карты) вставляем в матрицу пустые элементы (пустые поля) - тоже циклом?
    // и затем вставляем в неё элементы по мере изменения, когда будет необходимо
    // matrix[1][1] = "H"; - вот так

    // создаём массив с элементами, которые должны быть на карте (создаём один раз в начале симуляции - при вызове функции)
    public String[][] createMap() {
        for(int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], ". .");
        }
        return matrix;
    }

    void renderMap() {
        map.howMuchEntriesInMap();
        // для примера вставляем H (Herbivore - травоядное) в матрицу
        matrix[1][1] = " H ";

        System.out.println("size getAllEntities: " + map.getAllEntities().size());

        for (int i = 0; i < map.getAllEntities().size(); i++) {
            matrix[map.getEntityCoordinatesX(map.getAllEntities().get(i))][map.getEntityCoordinatesY(map.getAllEntities().get(i))] = " G ";
        }



//        System.out.println("getAllEntities from Renderer: " + map.getAllEntities().get(0));

        // в цикле мы только выводим матрицу на экран (ничего не вставляем в неё)
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                line.append(matrix[i][j] + "  ");
            }
            line.append("\n");
        }
        System.out.println(line);

        System.out.println("keySet from Renderer: " + map.map.keySet());
    }
}
