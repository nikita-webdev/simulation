package simulation.entities.animals;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import static simulation.config.Icons.PREDATOR_ICON;
import static simulation.config.logging.LoggerMessages.ATTACK_MESSAGE;

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

    @Override
    protected boolean canEat(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected void eat(SimulationMap simulationMap, Coordinate target) {
        Optional <Creature> optionalEntity = simulationMap.getCreatureAt(target);

        if (optionalEntity.isPresent()) {
            Creature targetCreature = optionalEntity.get();

            if (canEat(targetCreature)) {
                int preyHp = targetCreature.getHp();

                if (preyHp > 0) {
                    String preyName = targetCreature.name;
                    logger.log(Level.INFO, String.format(ATTACK_MESSAGE, this.name, preyName, target.row(), target.column()));
                    attack(simulationMap, target);
                }
            }
        }
    }

    @Override
    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        Optional <Entity> targetEntity = simulationMap.getEntityAt(coordinate);

        if (targetEntity.isPresent()) {
            return (targetEntity.get() instanceof Tree || targetEntity.get() instanceof Rock || targetEntity.get() instanceof Grass);
        }

        return false;
    }

    private void attack(SimulationMap simulationMap, Coordinate prey) {
        takeDamage(simulationMap, prey, attackPower);
    }
}
