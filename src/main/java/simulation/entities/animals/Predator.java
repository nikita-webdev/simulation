package simulation.entities.animals;

import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import static simulation.config.Icons.PREDATOR_ICON;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(String name) {
        super(name);

        this.setHp(100);
        this.setSpeed(2);
        this.attackPower = 50;

        icon = PREDATOR_ICON;
    }

    public void eat(SimulationMap simulationMap, Coordinate food) {
        if (simulationMap.isFood(this, food)) {
            attack(simulationMap, food);
        }
    }

    private void attack(SimulationMap simulationMap, Coordinate prey) {
        takeDamage(simulationMap, prey, attackPower);
    }

    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        return simulationMap.isTreeOrRock(coordinate) || simulationMap.isGrass(coordinate);
    }
}
