package simulation.actions;

import simulation.SimulationMap;
import simulation.animals.Herbivore;
import simulation.objects.Grass;

import java.util.Arrays;
import java.util.Map;

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

    // Может, в цикле добавлять названия объектов в массив?
    // Пока что количество объктов задаать нельзя
    // , int numberOfHerbivores, int numberOfPredators
    
    public void initObjectsOnTheMap(int numberOfGrasses) {
//        Grass grass1 = new Grass("grass12", 3, 1);
//        map.addEntity(grass1);

        // Массив для травы
        Grass[] grasses = new Grass[numberOfGrasses];
        // Заполнение массива элементами и добавление их в коллекцию
        for (int i = 0; i < numberOfGrasses; i++) {
            grasses[i] = new Grass("grass" + (i + 1),3, 1);
            map.addEntity(grasses[i]);
        }

//        for (Map<String, int[]> entry : map.entrySet()) {
//
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
