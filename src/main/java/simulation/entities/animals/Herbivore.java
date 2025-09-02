package simulation.entities.animals;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import java.util.logging.Level;
import java.util.logging.Logger;

import static simulation.config.Icons.HERBIVORE_ICON;

public class Herbivore extends Creature {
    private static final Logger logger = Logger.getLogger(Herbivore.class.getName());
    public Herbivore(String name) {
        super(name);

        this.setHp(100);
        this.setSpeed(1);

        icon = HERBIVORE_ICON;
    }

    public void eat(SimulationMap simulationMap, Coordinate food) {
        if (simulationMap.isFood(this, food)) {
            String foodName = simulationMap.getEntities().get(food).name;
            logger.log(Level.INFO, String.format("%s ate %s at (%d,%d).", this.name, foodName, food.getX(), food.getY()));
            simulationMap.removeEntity(food);
        }
    }

    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        return simulationMap.isTreeOrRock(coordinate);
    }
}
