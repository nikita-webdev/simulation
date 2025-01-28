package simulation;

import simulation.objects.Grass;

import java.sql.Array;
import java.util.*;

public class SimulationMap {
    Map<String, Entity> map = new HashMap<>();
    // Здесь что-то вроде Singleton
    private static final SimulationMap instance = new SimulationMap();

    private SimulationMap() {
        this.map = map;
    }

    public static SimulationMap getInstance() {
        return instance;
    }

    public void addEntity(Entity entity) {
        map.put(entity.name, entity);
    }

//    public int[] getEntity(String nameOfEntity) {
//        return map.get(nameOfEntity);
//    }

    // Сделать отдельный массив со значениями? "grass1", "grass2, "grass3", "grass4" и т.д.
    // И перебирать эти значения, добавляя эти объекты на карту

    private List<String> allEntities = new ArrayList<>();

    public void howMuchEntriesInMap() {
        // Выводим ключ и значение для всех объектов в map
        for (Map.Entry<String, Entity> entry : map.entrySet()) {
            String key = entry.getKey();
            allEntities.add(key);
            int[] value = new int[2];
            value[0] = entry.getValue().positionX;
            value[1] = entry.getValue().positionY;
            System.out.println("howMuchEntriesInMap: " + key + " " + Arrays.toString(value));
        }
    }

    public void getMapValues() {
//        System.out.println(map.containsValue());
    }

    // Проверяем содержит ли map координаты
    public boolean containsCoordinates(int[] valueOfCoordinates) {
        boolean isContain = false;
        for (Map.Entry<String, Entity> entry : map.entrySet()) {
            String key = entry.getKey();
            int[] value = new int[2];
            value[0] = entry.getValue().positionX;
            value[1] = entry.getValue().positionY;

            isContain = Arrays.equals(valueOfCoordinates, value);
            if (isContain) {
                break;
            }
        }
        return isContain;
    }

    public List<String> getAllEntities() {
        return allEntities;
    }

    public int getEntityCoordinatesX(String nameOfEntity) {
        // Получаем координаты X сущности по её имени
        return map.get(nameOfEntity).positionX;
    }

    public int getEntityCoordinatesY(String nameOfEntity) {
        // Получаем координаты Y сущности по её имени
        return map.get(nameOfEntity).positionY;
    }
}
