package simulation;

import java.sql.Array;
import java.util.*;

public class SimulationMap {
    Map<String, int[]> map = new HashMap<>();
    // Здесь что-то вроде Singleton
    private static final SimulationMap instance = new SimulationMap();

    private SimulationMap() {
        this.map = map;
    }

    public static SimulationMap getInstance() {
        return instance;
    }

    public void addEntity(Entity entity) {
        map.put(entity.name, new int[]{entity.positionY, entity.positionX});
    }

    public int[] getEntity(String nameOfEntity) {
        return map.get(nameOfEntity);
    }

    // Сделать отдельный массив со значениями? "grass1", "grass2, "grass3", "grass4" и т.д.
    // И перебирать эти значения, добавляя эти объекты на карту

    private List<String> allEntities = new ArrayList<>();

    public void howMuchEntriesInMap() {
        // Выводим ключ и значение для всех объектов в map
        for (Map.Entry<String, int[]> entry : map.entrySet()) {
            String key = entry.getKey();
            allEntities.add(key);
            int[] value = entry.getValue();
            System.out.println("containsValue: " + map.containsValue(value));
            System.out.println("howMuchEntriesInMap: " + key + " " + Arrays.toString(value));
            System.out.println("howMuchEntriesInMap: " + key + " " + value[0]);
            // можно первый элемент минус первый и второй также, потом сложить их разность и, если в сумме получается 0, то эта координата занята
            // вот же мы проверяем есть ли у нас такое значение map.containsValue(value)
        }
    }

    public void getMapValues() {
//        System.out.println(map.containsValue());
    }

    public List<String> getAllEntities() {
        return allEntities;
    }

    public int getEntityCoordinatesX(String nameOfEntity) {
        // Получаем координаты X сущности по её имени
        return map.get(nameOfEntity)[1];
    }

    public int getEntityCoordinatesY(String nameOfEntity) {
        // Получаем координаты Y сущности по её имени
        return map.get(nameOfEntity)[0];
    }
}
