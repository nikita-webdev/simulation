package simulation.actions;

import simulation.Entity;
import simulation.SimulationMap;
import simulation.animals.Creature;
import simulation.animals.Herbivore;
//import simulation.utils.SearchPath;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MoveAllCreatures {
    private SimulationMap map = SimulationMap.getInstance();

//    SearchPath searchPath = new SearchPath();


    public void makeMoveAllCreatures() {
        for (Map.Entry<String, Entity> entry : map.map.entrySet()) {
            if (entry.getValue() instanceof Creature) {
                Creature creature = (Creature) entry.getValue();
//                searchPath.addEdge(creature.coordinates, );
//                creature.makeMove();

                if (entry.getValue() instanceof Herbivore) {
//                    System.out.println(creature.name);
//                    searchPath.addEdge(entry.getValue().coordinates, map.getAllGrassesCoordinates());
                    if(Objects.equals(entry.getValue().name, "herbivore1")) {
                        creature.searchPath();
                    }

                }
            }
        }
    }
}
