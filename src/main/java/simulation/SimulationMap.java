package simulation;

import simulation.animals.Herbivore;
import simulation.animals.Predator;
import simulation.objects.Grass;

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
    private Map<String, Herbivore> allHerbivores = new HashMap<>();
    private Map<String, Predator> allPredators = new HashMap<>();
    private List<int[]> allGrassesCoordinates = new ArrayList<>();
    private Map<String, Grass> allGrassesCoordinatesForRemove = new HashMap<>();
    private Map<String, Herbivore> allHerbivoresCoordinatesForRemove = new HashMap<>();
    private List<int[]> allTreesAndRocksCoordinates = new ArrayList<>();
    private List<int[]> allEntityCoordinates = new ArrayList<>();

    public void howMuchEntriesInMap() {
        for (Map.Entry<String, Entity> entry : map.entrySet()) {
            String key = entry.getKey();
            allEntities.add(key);

            if (entry.getValue() instanceof Herbivore herbivore) {
                allHerbivores.put(herbivore.name, herbivore);
                allHerbivoresCoordinatesForRemove.put(Arrays.toString(herbivore.coordinates), herbivore);
            }

            if (entry.getValue() instanceof Predator predator) {
                allPredators.put(predator.name, predator);
            }

            if (entry.getValue() instanceof Grass grass) {
                allGrassesCoordinatesForRemove.put(Arrays.toString(grass.coordinates), grass);
            }
        }
    }

    public Map<String, Herbivore> getAllHerbivores() {
        return allHerbivores;
    }

    public Map<String, Predator> getAllPredators() {
        return allPredators;
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

    public Map<String, Grass> getAllGrassesCoordinatesForRemove() {
        return allGrassesCoordinatesForRemove;
    }

    public Map<String, Herbivore> getAllHerbivoresCoordinatesForRemove() {
        return allHerbivoresCoordinatesForRemove;
    }

    public void setAllHerbivoresCoordinatesForRemove(Herbivore herbivore) {
        allHerbivoresCoordinatesForRemove.put(herbivore.name, herbivore);
    }

    public void clearAllHerbivoresCoordinatesForRemove() {
        allHerbivoresCoordinatesForRemove.clear();
    }

    public void removeGrass(int[] thisGrass) {
        String nameOfThisGrass = allGrassesCoordinatesForRemove.get(Arrays.toString(thisGrass)).name;

        allGrassesCoordinatesForRemove.remove(Arrays.toString(thisGrass));
        map.remove(nameOfThisGrass);
    }

    public boolean isGrass(int[] thisPosition) {
        for (Map.Entry<String, Grass> entry : allGrassesCoordinatesForRemove.entrySet()) {
            String nameOfThisGrass = entry.getKey();

            if (Arrays.equals(allGrassesCoordinatesForRemove.get(nameOfThisGrass).coordinates, thisPosition)) {
                return true;
            }
        }
        return false;
    }

    public void removeHerbivore(int[] thisHerbivore) {
        String nameOfThisHerbivore = allHerbivoresCoordinatesForRemove.get(Arrays.toString(thisHerbivore)).name;

        allHerbivoresCoordinatesForRemove.remove(Arrays.toString(thisHerbivore));
        allHerbivores.remove(nameOfThisHerbivore);
        map.remove(nameOfThisHerbivore);
    }

    public boolean isHerbivore(int[] thisPosition) {
        for (Map.Entry<String, Herbivore> entry : allHerbivoresCoordinatesForRemove.entrySet()) {
            String nameOfThisHerbivore = entry.getKey();

            if (Arrays.equals(allHerbivoresCoordinatesForRemove.get(nameOfThisHerbivore).coordinates, thisPosition)) {
                return true;
            }
        }
        return false;
    }

    public void setAllTreesAndRocksCoordinates(int[] thisGrass) {
        allTreesAndRocksCoordinates.add(thisGrass);
    }

    public List<int[]> getAllTreesAndRocksCoordinates() {
        return allTreesAndRocksCoordinates;
    }

    public boolean isTreeOrRock(int[] thisPosition) {
        for (int i = 0; i < allTreesAndRocksCoordinates.size(); i++) {
            if (Arrays.equals(allTreesAndRocksCoordinates.get(i), thisPosition)) {
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
