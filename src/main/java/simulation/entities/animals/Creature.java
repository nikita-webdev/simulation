package simulation.entities.animals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Game;
import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

public abstract class Creature extends Entity {
    Game game = new Game();
    private static final Logger logger = Logger.getLogger(Creature.class.getName());

    public int speed;
    private int hp;

    public Creature(String name) {
        super(name);
    }

    public void makeMove(SimulationMap simulationMap, Coordinate from, List<Coordinate> path) {
        int countOfSteps = Math.min(getSpeed(), path.size());

        for (int i = 0; i < countOfSteps; i++) {
            Coordinate nextStep = path.get(i);

            if(simulationMap.isFood(this, nextStep)) {
                eat(simulationMap, nextStep);
                break;
            } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
                if (i < 1) {
                    moveCreature(simulationMap, from, nextStep);
                    logger.log(Level.INFO, String.format("%s moves to (%d,%d).", this.name, nextStep.getX(), nextStep.getY()));
                } else {
                    moveCreature(simulationMap, path.get(i - 1), nextStep);
                    logger.log(Level.INFO, String.format("%s moves to (%d,%d).", this.name, nextStep.getX(), nextStep.getY()));
                }
            } else {
                logger.log(Level.INFO, String.format("%s couldn't find any suitable food.", this.name));
            }

            game.updateMap(simulationMap);
        }
    }

    public abstract void eat(SimulationMap simulationMap, Coordinate food);

    public void takeDamage(SimulationMap simulationMap, Coordinate coordinate, int damage) {
        if (getHp() > 0) {
            setHp(getHp() - damage);
        }

        if (getHp() <= 0) {
            die(simulationMap, coordinate);
        }
    }

    public void die(SimulationMap simulationMap, Coordinate coordinate) {
        logger.log(Level.INFO, String.format("%s died.", simulationMap.getAllCreatures().get(coordinate).name));
        simulationMap.removeEntity(coordinate);
    }

    private void moveCreature(SimulationMap simulationMap, Coordinate from, Coordinate to) {
        Entity entity = this;

        if (from != null) {
            simulationMap.removeEntity(from);

            simulationMap.addEntity(to, entity);
        }
    }

    public abstract boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate);

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp) {
        if (hp < 0) {
            hp = 0;
        }

        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }
}
