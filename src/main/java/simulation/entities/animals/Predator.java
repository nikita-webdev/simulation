package simulation.entities.animals;

import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.map.Coordinate;
import simulation.map.SimulationMap;

import static simulation.config.Icons.PREDATOR_ICON;
import static simulation.config.LoggerMessages.ATTACK_MESSAGE;

public class Predator extends Creature {
    private static final Logger logger = Logger.getLogger(Predator.class.getName());
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
            int preyHp = simulationMap.getAllCreatures().get(food).getHp();
            if (preyHp > 0) {
                String preyName = simulationMap.getAllCreatures().get(food).name;
                logger.log(Level.INFO, String.format(ATTACK_MESSAGE, this.name, preyName, food.row(), food.column()));
                attack(simulationMap, food);
            }
        }
    }

    private void attack(SimulationMap simulationMap, Coordinate prey) {
        takeDamage(simulationMap, prey, attackPower);
    }

    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        return simulationMap.isTreeOrRock(coordinate) || simulationMap.isGrass(coordinate);
    }
}
