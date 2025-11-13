package simulation.entities.animals;

import simulation.entities.Entity;
import simulation.entities.objects.Grass;
import simulation.entities.objects.Rock;
import simulation.entities.objects.Tree;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static simulation.config.Icons.HERBIVORE_ICON;
import static simulation.config.logging.LoggerMessages.EAT_MESSAGE;

public class Herbivore extends Creature {
    private static final Logger logger = Logger.getLogger(Herbivore.class.getName());

    public Herbivore(String name) {
        super(name, new GroundCreatureMover());

        this.setHp(100);
        this.setSpeed(1);

        icon = HERBIVORE_ICON;
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
                String foodName = targetEntity.name;
                simulationMap.removeEntity(target);
                logger.log(Level.INFO, String.format(EAT_MESSAGE, this.name, foodName, target.row(), target.column()));
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
