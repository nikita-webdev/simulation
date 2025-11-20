package simulation.entities.animals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.config.logging.LoggerMessages;
import simulation.entities.Entity;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;
import simulation.pathfinder.PathFinder;
import simulation.renderer.EntityType;
import simulation.coordinate.Coordinate;
import simulation.simulation_map.SimulationMap;

public abstract class Creature extends Entity {
    private static final Logger logger = Logger.getLogger(Creature.class.getName());

    private final Mover mover;

    private int speed;
    private int hp;

    public Creature(EntityType entityType, String name, Mover mover, int speed, int hp) {
        super(entityType, name);
        this.mover = mover;
        this.speed = speed;
        this.hp = hp;
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

    public abstract Class<? extends Entity> getPreferredFoodType();

    public List<Coordinate> calculatePath(SimulationMap simulationMap, Coordinate from) {
        PathFinder pathFinder = new PathFinder();
        return pathFinder.searchPath(simulationMap, from, getPreferredFoodType());
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
        logger.log(Level.INFO, String.format(LoggerMessages.DIE_MESSAGE, this.getName()));

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
