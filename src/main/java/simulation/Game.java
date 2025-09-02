package simulation;

import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.actions.turnActions.MoveAllCreatures;
import simulation.entities.animals.Creature;
import simulation.map.Renderer;
import simulation.map.SimulationMap;

import static simulation.Simulation.turn;

public class Game {
    private static final Logger logger = Logger.getLogger(Creature.class.getName());
    private final Renderer renderer = new Renderer();
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();
    private int turnCount;

    private static final int SLEEP_TIME = 1000;
    private static final int THREAD_STOP_DELAY = 2000;

    public Game(int turnCount) {
        this.turnCount = turnCount;
    }

    public Game() {

    }

    public void gameLoop(SimulationMap simulationMap) throws InterruptedException {
        while (turn < turnCount) {
            if (turn == 0) {
                updateMap(simulationMap);
            }

            if (Simulation.runningThread) {
                if (turn>0) {
                    moveAllCreatures.makeMoveAllCreatures(simulationMap);
                }
            } else {
                handleStoppedThread();
            }

            if (!Simulation.runningThread && Simulation.nextTurn) {
                if (turn>0) {
                    moveAllCreatures.makeMoveAllCreatures(simulationMap);
                }

                Simulation.nextTurn = false;
            }
        }
    }

    private void handleStoppedThread() {
        logger.log(Level.INFO, "The thread has been paused.");

        try {
            Thread.sleep(THREAD_STOP_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void updateMap(SimulationMap simulationMap) {
        turn++;
//        logger.log(Level.INFO, String.format("Iteration: %d", turn));
        renderer.renderMap(simulationMap);
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
