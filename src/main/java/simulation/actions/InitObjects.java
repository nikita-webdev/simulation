package simulation.actions;

import simulation.SimulationMap;
import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.objects.Grass;
import simulation.objects.Rock;
import simulation.objects.Tree;

import java.util.Random;

public class InitObjects {
    private SimulationMap map = SimulationMap.getInstance();

    public InitObjects() {

    }

    // Генерируем случайные координаты для объекта
    private int[] generateRandomCoordinates() {
        int[] xy = new int[2];
        Random random = new Random();

        do {
            xy[0] = random.nextInt((SimulationMap.MAP_SIZE_X - 1));
            xy[1] = random.nextInt((SimulationMap.MAP_SIZE_Y - 1));
        } while (map.isCoordinatesOccupied(xy));

        return xy;
    }

    public void initObjectsOnTheMap(int numberOfGrasses, int numberOfRocksAndTrees, int numberOfHerbivores, int numberOfPredators) {

        // Добавляем траву в map
        for (int i = 0; i < numberOfGrasses; i++) {
            // Рандомные координаты проверяются на совпадение в map и, если совпадений нет, то объект добавляется на эти координаты
            int[] xyGrass = generateRandomCoordinates();

            map.addEntity(new Grass("grass" + (i + 1), xyGrass[0], xyGrass[1]));
            map.setAllGrassesCoordinates(new int[] {xyGrass[0], xyGrass[1]});
            map.setAllEntityCoordinates(new int[] {xyGrass[0], xyGrass[1]});
        }

        // Добавляем камни и деревья в map
        for (int i = 0; i < numberOfRocksAndTrees; i++) {
            int[] xyRock = generateRandomCoordinates();
            int[] xyTree = generateRandomCoordinates();

            map.addEntity(new Rock("rock" + (i + 1), xyRock[0], xyRock[1]));
            map.addEntity(new Tree("tree" + (i + 1), xyTree[0], xyTree[1]));
            map.setAllEntityCoordinates(new int[] {xyRock[0], xyRock[1]});
            map.setAllEntityCoordinates(new int[] {xyTree[0], xyTree[1]});

        }

        // Добавляем травоядных в map
        for (int i = 0; i < numberOfHerbivores; i++) {
            int[] xyHerbivore = generateRandomCoordinates();

            map.addEntity(new Herbivore("herbivore" + (i + 1), xyHerbivore[0], xyHerbivore[1]));
            map.setAllEntityCoordinates(new int[] {xyHerbivore[0], xyHerbivore[1]});
        }

        // Добавляем хищников в map
        for (int i = 0; i < numberOfPredators; i++) {
            int[] xyPredator = generateRandomCoordinates();

            map.addEntity(new Predator("predator" + (i + 1), xyPredator[0], xyPredator[1]));
            map.setAllEntityCoordinates(new int[] {xyPredator[0], xyPredator[1]});
        }
    }

    
    /*
    void simulationSettings

    void createObjects(int numberOfGrass, int numberOfTree, int numberOfRock, int numberOfHerbivore, int numberOfPredator) {
        // здесь в цикле создаём объекты до заданного количества quantity
        // и записываем их в список Map
        // это 2 действия или 1? Создать статичные объекты, травоядных и хищников
    }

    int generatePosition(objectName) {
        // случайно генерирует координаты для объекта и возвращает их
        return x, y;
    }

    boolean checkPosition(objectName) {
        // проверяет заняты ли координаты на карте
    }

    // и надо ещё отдельное действие для установки не 1, а всех объектов на карте
    int setPosition(objectName) {
        // устанавливает координаты для объекта
        // то есть, устанавливаем объект на карту
        objectName.positionX = x;
        objectName.positionY = y;
    }

     */

//    public SimulationMap createObjects() {
//        return map;
//    }
}
