package simulation;

import java.util.*;

public class SimulationMap {
    public Map<String, Entity> map = new HashMap<>();
    private static final SimulationMap instance = new SimulationMap();

    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_Y = 15;

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

    private List<String> allEntities = new ArrayList<>();
    private List<int[]> allGrassesCoordinates = new ArrayList<>();
    private List<int[]> allEntityCoordinates = new ArrayList<>();

    public void howMuchEntriesInMap() {
        // Выводим ключ и значение для всех объектов в map
        for (Map.Entry<String, Entity> entry : map.entrySet()) {
            String key = entry.getKey();
            allEntities.add(key);
        }
    }

    public void setAllEntityCoordinates(int[] thisEntity) {
        allEntityCoordinates.add(thisEntity);
    }

    public List<int[]> getAllEntityCoordinates() {
        return allEntityCoordinates;
    }

    public void setAllGrassesCoordinates(int[] thisGrass) {
        allGrassesCoordinates.add(thisGrass);
    }

    public List<int[]> getAllGrassesCoordinates() {
        return allGrassesCoordinates;
    }

    public boolean isGrass(int[] thisPosition) {
        for (int i = 0; i < allGrassesCoordinates.size(); i++) {
            if (Arrays.equals(allGrassesCoordinates.get(i), thisPosition)) {
                return true;
            }
        }
        return false;
    }

    public void getMapValues() {
//        System.out.println(map.containsValue());
    }

    // Проверяем содержит ли map координаты
    public boolean isCoordinatesOccupied(int[] newCoordinates) {
        boolean isContain = false;

        for (Map.Entry<String, Entity> entry : map.entrySet()) {
            int[] existingCoordinates = new int[2];
            existingCoordinates[0] = entry.getValue().positionX;
            existingCoordinates[1] = entry.getValue().positionY;

            isContain = Arrays.equals(newCoordinates, existingCoordinates);

            if (isContain) {
                break;
            }
        }
        return isContain;
    }

    public List<String> getAllEntities() {
        return allEntities;
    }

    public String getEntityName(int[] entityCoordinates) {
        return map.get(entityCoordinates).name;
    }

    public int getEntityCoordinatesX(String nameOfEntity) {
        // Получаем координаты X сущности по её имени
        return map.get(nameOfEntity).positionX;
    }

    public int getEntityCoordinatesY(String nameOfEntity) {
        // Получаем координаты Y сущности по её имени
        return map.get(nameOfEntity).positionY;
    }

    public String getEntityIcon(String nameOfEntity) {
        return map.get(nameOfEntity).icon;
    }
}
