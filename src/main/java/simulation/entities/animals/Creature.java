package simulation.entities.animals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.entities.Entity;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import static simulation.config.logging.LoggerMessages.*;

public abstract class Creature extends Entity {
    private static final Logger logger = Logger.getLogger(Creature.class.getName());

    private int speed;
    private int hp;

    private final Mover mover;

    public Creature(String name, Mover mover) {
        super(name);
        this.mover = mover;
    }

    public void makeMove(SimulationMap simulationMap, Coordinate from, List<Coordinate> path) {
        mover.move(this, simulationMap, from, path);
    }

    public abstract boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate);

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    protected abstract void eat(SimulationMap simulationMap, Coordinate food);

    protected abstract boolean canEat(Entity entity);

    protected void takeDamage(SimulationMap simulationMap, Coordinate coordinate, int damage) {
        if (getHp() > 0) {
            setHp(getHp() - damage);
        }

        if (getHp() <= 0) {
            die(simulationMap, coordinate);
        }
    }

    protected void die(SimulationMap simulationMap, Coordinate coordinate) {
        simulationMap.removeEntity(coordinate);
        logger.log(Level.INFO, String.format(DIE_MESSAGE, this.name));

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

    protected boolean isSolid(Entity entity) {
        return entity instanceof Tree || entity instanceof Rock;
    }
}
