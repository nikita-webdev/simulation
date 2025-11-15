package simulation.entities.animals;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.config.entities.AnimalStats;
import simulation.config.logging.LoggerMessages;
import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.entities.EntityType;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

public class Predator extends Creature {
    private static final Logger logger = Logger.getLogger(Predator.class.getName());
    private static final Mover GROUND_MOVER = new GroundCreatureMover();
    private final int attackPower;

    public Predator(EntityType entityType, String name, int attackPower) {
        super(entityType, name, GROUND_MOVER, AnimalStats.PREDATOR_SPEED, AnimalStats.PREDATOR_HP);
        this.attackPower = attackPower;
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
                    String preyName = targetCreature.getName();
                    logger.log(Level.INFO, String.format(LoggerMessages.ATTACK_MESSAGE, this.getName(), preyName, target.row(), target.column()));
                    attack(simulationMap, target);
                }
            }
        }
    }

    @Override
    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        Optional <Entity> targetEntity = simulationMap.getEntityAt(coordinate);

        if (targetEntity.isPresent()) {
            return (isSolid(targetEntity.get()) || targetEntity.get() instanceof Grass);
        }

        return false;
    }

    private void attack(SimulationMap simulationMap, Coordinate prey) {
        Optional<Creature> optionalTarget = simulationMap.getCreatureAt(prey);

        if (optionalTarget.isPresent()) {
            Creature target = optionalTarget.get();
            target.takeDamage(simulationMap, prey, attackPower);
        }
    }
}
