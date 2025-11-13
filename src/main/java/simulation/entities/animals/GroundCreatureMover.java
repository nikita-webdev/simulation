package simulation.entities.animals;

import simulation.config.logging.LoggerMessages;
import simulation.entities.Entity;
import simulation.simulation_map.Coordinate;
import simulation.simulation_map.SimulationMap;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroundCreatureMover implements Mover {
    private static final Logger logger = Logger.getLogger(GroundCreatureMover.class.getName());

    public void move(Creature creature, SimulationMap simulationMap, Coordinate from, List<Coordinate> path) {
        int steps = Math.min(creature.getSpeed(), path.size());

        Coordinate current = from;
        for (int i = 0; i < steps; i++) {
            Coordinate next = path.get(i);

            if (creature.isObstacle(simulationMap, next)) {
                logger.log(Level.INFO, String.format(LoggerMessages.OBSTACLE_BLOCKED, creature.name, next.row(), next.column()));

                return;
            }

            Optional<Entity> target = simulationMap.getEntityAt(next);

            if (target.isPresent() && creature.canEat(target.get())) {
                creature.eat(simulationMap, next);
                return;
            }

            if (!simulationMap.isCoordinateOccupied(next)) {
                simulationMap.moveEntity(current, next);
                current = next;

                logger.log(Level.INFO, String.format(LoggerMessages.MOVE_MESSAGE, creature.name, next.row(), next.column()));
            } else {
                logger.log(Level.INFO, String.format(LoggerMessages.FOOD_NOT_FOUND, creature.name));

                return;
            }
        }
    }
}

