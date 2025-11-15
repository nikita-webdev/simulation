package simulation.entities.animals;

import simulation.config.entities.AnimalStats;
import simulation.config.logging.LoggerMessages;
import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.entities.EntityType;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Herbivore extends Creature {
    private static final Logger logger = Logger.getLogger(Herbivore.class.getName());
    private static final Mover GROUND_MOVER = new GroundCreatureMover();

    public Herbivore(EntityType entityType, String name) {
        super(entityType, name, GROUND_MOVER, AnimalStats.HERBIVORE_SPEED, AnimalStats.HERBIVORE_HP);
    }

    @Override
    protected boolean canEat(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void eat(SimulationMap simulationMap, Coordinate target) {
        Optional<Entity> optionalEntity = simulationMap.getEntityAt(target);

        if (optionalEntity.isPresent()) {
            Entity targetEntity = optionalEntity.get();

            if (canEat(targetEntity)) {
                String foodName = targetEntity.getName();
                simulationMap.removeEntity(target);
                logger.log(Level.INFO, String.format(LoggerMessages.EAT_MESSAGE, this.getName(), foodName, target.row(), target.column()));
            }
        }
    }

    @Override
    public boolean isObstacle(SimulationMap simulationMap, Coordinate coordinate) {
        Optional <Entity> targetEntity = simulationMap.getEntityAt(coordinate);
        if (targetEntity.isPresent()) {
            return isSolid(targetEntity.get());
        }

        return false;
    }
}
