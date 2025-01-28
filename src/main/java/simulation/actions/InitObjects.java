package simulation.actions;

import simulation.SimulationMap;
import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.objects.Grass;
import simulation.objects.Rock;
import simulation.objects.Tree;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class InitObjects {
    private SimulationMap map = SimulationMap.getInstance();
    // нужно указывать при вызове какого-нибудь метода или при создании объекта InitObjects - сколько и каких объектов создавать
    // то есть, это значение должно быть можно откуда-то настраивать и регулировать, а уже эти методы, чтобы его принимали

    // Сделать несколько пресетов (не уровней сложности) Симуляции
    // Чтобы можно было их выбирать в конструкторе при инициализации
    // И, чтобы количество существ в каждом варьировалось
    // Храниться они могут, например, массивом здесь
    // 1, 2, 3 - easy, medium, hard
    // И потом эти массивы значений как должны передаваться в метод?

    Integer[] one = {1, 2, 3};

    public InitObjects() {

    }

    // Генерируем случайные координаты для объекта
    private int[] generateRandomCoordinates(int xLimit, int yLimit) {
        int[] xy = new int[2];
        Random random = new Random();

        do {
            xy[0] = random.nextInt((xLimit - 1));
            xy[1] = random.nextInt((yLimit - 1));
        } while (map.containsCoordinates(xy));

        return xy;
    }


    // Может, в цикле добавлять названия объектов в массив?
    // Пока что количество объктов задавать нельзя
    // , int numberOfHerbivores, int numberOfPredators

    public void initObjectsOnTheMap(int numberOfObjects) {

        // Заменить на keySet()
        for (int i = 0; i < numberOfObjects; i++) {
            // Рандомные координаты проверяются на совпадение в map и, если совпадений нет, то объект добавляется на эти координаты
            int[] xyGrass = generateRandomCoordinates(20, 15);
            int[] xyRock = generateRandomCoordinates(20, 15);
            int[] xyTree = generateRandomCoordinates(20, 15);
            int[] xyHerbivore = generateRandomCoordinates(20, 15);
            int[] xyPredator = generateRandomCoordinates(20, 15);
//
            map.addEntity(new Grass("grass" + (i + 1), xyGrass[0], xyGrass[1]));
            map.addEntity(new Rock("rock" + (i + 1), xyRock[0], xyRock[1]));
            map.addEntity(new Tree("tree" + (i + 1), xyTree[0], xyTree[1]));
            map.addEntity(new Herbivore("herbivore" + (i + 1), xyHerbivore[1], xyHerbivore[0]));
            map.addEntity(new Predator("predator" + (i + 1), xyPredator[1], xyPredator[0]));
        }

//        Grass[] grasses = new Grass[numberOfGrasses];
//        // Заполнение массива элементами и добавление их в коллекцию
//        for (int i = 0; i < numberOfGrasses; i++) {
//            // Рандомные координаты проверяются на совпадение в map и, если совпадений нет, то объект добавляется на эти координаты
//            int[] xy = generateRandomCoordinates(20, 15);
//            grasses[i] = new Grass("grass" + (i + 1), xy[0], xy[1]);
//            map.addEntity(grasses[i]);
//        }


//        Herbivore[] herbivores = new Herbivore[numberOfHerbivores];
//
//        for (int i = 0; i < numberOfHerbivores; i++) {
//            herbivores[i] = new Herbivore("herbivore" + i, 1, 1, 2, 3);
//        }
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
