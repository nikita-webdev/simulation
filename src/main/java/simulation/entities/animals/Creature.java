package simulation.entities.animals;

import simulation.Game;
import simulation.entities.Entity;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    Game game = new Game();

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
            } else if (!simulationMap.isCoordinatesOccupied(nextStep)) {
                if (i < 1) {
                    moveCreature(simulationMap, from, nextStep);
                } else {
                    moveCreature(simulationMap, path.get(i - 1), nextStep);
                }
            } else {
                System.out.println(name + " should have taken a step, but it was not taken on: " + Arrays.toString(new int[] {nextStep.getX(), nextStep.getY()}));
            }

            game.updateMap(simulationMap);
        }
    }

    public void eat(SimulationMap simulationMap, Coordinate food) {
        if (simulationMap.isFood(this, food)) {
            simulationMap.removeEntity(food);
        }
    }

    public void takeDamage(SimulationMap simulationMap, Coordinate coordinate, int damage) {
        if (getHp() > 0) {
            setHp(getHp() - damage);
            System.out.println(getHp());
        }

        if (getHp() <= 0) {
            die(simulationMap, coordinate);
        }
    }

    public void die(SimulationMap simulationMap, Coordinate coordinate) {
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
