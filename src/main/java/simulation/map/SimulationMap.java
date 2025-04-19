package simulation.map;

import simulation.entities.Entity;
import simulation.entities.animals.Creature;
import simulation.entities.animals.Herbivore;
import simulation.entities.animals.Predator;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;

import java.util.*;

public class SimulationMap {
//    public Map<String, Entity> map = new HashMap<>();
    public Map<Cell, Entity> map = new HashMap<>();
    private static final SimulationMap instance = new SimulationMap();
    private Cell cell = new Cell();

    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_Y = 15;

    private SimulationMap() {
        this.map = map;
    }

    public static SimulationMap getInstance() {
        return instance;
    }

    public void addEntity(Cell cell, Entity entity) {
        map.put(cell, entity);
    }

    public Entity getEntity(String nameOfEntity) {
        return map.get(nameOfEntity);
    }

    public Map<Cell, Entity> getAllEntities() {
        return map;
    }

    private Map<String, Creature> allCreatures = new HashMap<>();
    private Map<String, Herbivore> allHerbivores = new HashMap<>();
    private Map<String, Predator> allPredators = new HashMap<>();

    private Map<String, Grass> allGrassesCoordinatesForRemove = new HashMap<>();
    private Map<String, Herbivore> allHerbivoresCoordinatesForRemove = new HashMap<>();

    public void howMuchEntriesInMap() {
        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            // Перебираем коллекцию и рассортировываем объекты по отдельным коллекциям
            // Делаем это каждый ход, вызывая howMuchEntriesInMap() в классе Render

            if (entry.getValue() instanceof Herbivore herbivore) {
                allHerbivores.put(herbivore.name, herbivore);
                allHerbivoresCoordinatesForRemove.put(Arrays.toString(herbivore.coordinates), herbivore);
                allCreatures.put(herbivore.name, herbivore);
            }

            if (entry.getValue() instanceof Predator predator) {
                allPredators.put(predator.name, predator);
                allCreatures.put(predator.name, predator);
            }

            if (entry.getValue() instanceof Grass grass) {
                allGrassesCoordinatesForRemove.put(Arrays.toString(grass.coordinates), grass);
            }

//            Cell cell = entry.getKey();
//            Entity entity = entry.getValue();
//            cell.setCoordinates(entity.positionX, entity.positionY);
        }
    }

//    public Map<String, Herbivore> getAllHerbivores() {
//        return allHerbivores;
//    }
//
//    public Map<String, Predator> getAllPredators() {
//        return allPredators;
//    }

    public Map<String, Grass> getAllGrassesCoordinatesForRemove() {
        return allGrassesCoordinatesForRemove;
    }

    public Map<String, Herbivore> getAllHerbivoresCoordinatesForRemove() {
        return allHerbivoresCoordinatesForRemove;
    }

//    public void setAllHerbivoresCoordinatesForRemove(Herbivore herbivore) {
//        allHerbivoresCoordinatesForRemove.put(herbivore.name, herbivore);
//    }

//    public void clearAllHerbivoresCoordinatesForRemove() {
//        allHerbivoresCoordinatesForRemove.clear();
//    }

//    public void removeGrass(int[] thisGrass) {
//        String nameOfThisGrass = allGrassesCoordinatesForRemove.get(Arrays.toString(thisGrass)).name;
//
//        allGrassesCoordinatesForRemove.remove(Arrays.toString(thisGrass));
//        map.remove(nameOfThisGrass);
//    }

    public boolean isGrass(int[] thisPosition) {
        for (Map.Entry<String, Grass> entry : allGrassesCoordinatesForRemove.entrySet()) {
            String nameOfThisGrass = entry.getKey();

            if (Arrays.equals(allGrassesCoordinatesForRemove.get(nameOfThisGrass).coordinates, thisPosition)) {
                return true;
            }
        }
        return false;
    }

//    public void removeHerbivore(int[] thisHerbivore) {
//        String nameOfThisHerbivore = allHerbivoresCoordinatesForRemove.get(Arrays.toString(thisHerbivore)).name;
//
//        allHerbivoresCoordinatesForRemove.remove(Arrays.toString(thisHerbivore));
//        allHerbivores.remove(nameOfThisHerbivore);
////        allCreatures.remove(nameOfThisHerbivore);
//        map.remove(nameOfThisHerbivore);
//    }

    public boolean isHerbivore(int[] thisPosition) {
        for (Map.Entry<String, Herbivore> entry : allHerbivoresCoordinatesForRemove.entrySet()) {
            String nameOfThisHerbivore = entry.getKey();

            if (Arrays.equals(allHerbivoresCoordinatesForRemove.get(nameOfThisHerbivore).coordinates, thisPosition)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTreeOrRock(int[] thisPosition) {Cell currentCell = cell.findCellInMap(map, thisPosition[0], thisPosition[1]);

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
            Cell checkThisCell = entry.getKey();
            Entity entity = entry.getValue();

            if (checkThisCell == currentCell) {
                if (entity instanceof Tree || entity instanceof Rock) {
                    return true;
                }
            }
        }

        return false;
    }

    // Проверяем содержит ли map координаты
    public boolean isCoordinatesOccupied(int[] newCoordinates) {
        boolean isContain = false;

        for (Map.Entry<Cell, Entity> entry : map.entrySet()) {
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

//    public String getEntityName(int[] entityCoordinates) {
//        return map.get(entityCoordinates).name;
//    }
//
//    public int getEntityCoordinatesX(String nameOfEntity) {
//        // Получаем координаты X сущности по её имени
//        return map.get(nameOfEntity).positionX;
//    }
//
//    public int getEntityCoordinatesY(String nameOfEntity) {
//        // Получаем координаты Y сущности по её имени
//        return map.get(nameOfEntity).positionY;
//    }
//
//    public String getEntityIcon(String nameOfEntity) {
//        return map.get(nameOfEntity).icon;
//    }

    public Map<String, Creature> getAllCreatures() {
        return allCreatures;
    }

    public void removeCell(int x, int y) {
        Cell targetCell = cell.findCellInMap(map, x, y);

        if (targetCell != null) {
            map.remove(targetCell);
        } else {
            System.out.println("Клетка не найдена.");
        }
    }
}
