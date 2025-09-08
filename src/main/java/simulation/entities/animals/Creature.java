package simulation.entities.animals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import static simulation.config.LoggerMessages.*;

public abstract class Creature extends Entity {
    private static final Logger logger = Logger.getLogger(Creature.class.getName());

    private int speed;
    private int hp;

    public Creature(String name) {
        super(name);
    }

    public void makeMove(SimulationMap simulationMap, Coordinate from, List<Coordinate> path) {
        int countOfSteps = Math.min(getSpeed(), path.size());

        for (int step = 0; step < countOfSteps; step++) {
            Coordinate nextStep = path.get(step);

            if(simulationMap.isFood(this, nextStep)) {
                eat(simulationMap, nextStep);
                simulationMap.updateMap();
                break;
            }

            if (!simulationMap.isCoordinatesOccupied(nextStep)) {
                if (step < 1) {
                    moveCreature(simulationMap, from, nextStep);
                } else {
                    moveCreature(simulationMap, path.get(step - 1), nextStep);
                }

                logger.log(Level.INFO, String.format(MOVE_MESSAGE, this.name, nextStep.row(), nextStep.column()));
            } else {
                logger.log(Level.INFO, String.format(FOOD_NOT_FOUND, this.name));
            }

            simulationMap.updateMap();
        }
    }

    public abstract boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate);

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    protected abstract void eat(SimulationMap simulationMap, Coordinate food);

    protected void takeDamage(SimulationMap simulationMap, Coordinate coordinate, int damage) {
        if (getHp() > 0) {
            setHp(getHp() - damage);
        }

        if (getHp() <= 0) {
            die(simulationMap, coordinate);
        }
    }

    protected void die(SimulationMap simulationMap, Coordinate coordinate) {
        String creatureName = simulationMap.getAllCreatures().get(coordinate).name;

        logger.log(Level.INFO, String.format(DIE_MESSAGE, creatureName));
        simulationMap.removeEntity(coordinate);
    }

    protected void setHp(int hp) {
        if (hp < 0) {
            hp = 0;
        }

        this.hp = hp;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    private void moveCreature(SimulationMap simulationMap, Coordinate from, Coordinate to) {
        Entity entity = this;

        if (from != null) {
            simulationMap.removeEntity(from);

            simulationMap.addEntity(to, entity);
        }
    }
}
