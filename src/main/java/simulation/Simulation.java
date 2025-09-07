package simulation;

import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.initActions.InitObjects;
import simulation.actions.turnActions.MoveAllCreatures;
import simulation.actions.turnActions.RespawnGrassAction;
import simulation.actions.turnActions.RespawnHerbivoreAction;
import simulation.map.SimulationMap;
import simulation.menu.UserInput;
import simulation.menu.MenuOptionsPrinter;

import static simulation.config.LoggerMessages.*;

public class Simulation {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());
    private static final Object pauseLock = new Object();

    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();
    private final SimulationMap simulationMap;
    private final InitObjects initObjects = new InitObjects();
    private final RespawnGrassAction respawnGrassAction = new RespawnGrassAction();
    private final RespawnHerbivoreAction respawnHerbivoreAction = new RespawnHerbivoreAction();
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();
    private final UserInput userInput = new UserInput(this);

    public boolean isRunning = false;
    private boolean isPaused = false;
    public static boolean isMoveActive = false;
    private boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;
    }

    public void launch() {
        menuOptionsPrinter.printStartOptions();
        userInput.startInputThread();
    }

    private void runSimulationLoop(SimulationMap simulationMap) {
        initMap();

        while (!Thread.currentThread().isInterrupted()) {
            if (shouldRespawn()) {
                respawnGrassAction.execute(simulationMap);
                respawnHerbivoreAction.execute(simulationMap);
            }

            if (isPaused) {
                handleStoppedSimulationThread();
            } else {
                moveAllCreatures.execute(simulationMap);
            }

            if (isPaused && isNextTurn) {
                moveAllCreatures.execute(simulationMap);
                isNextTurn = false;
            }

            turnCount++;
        }
    }

    private final Thread simulationThread = new Thread() {
        public void run() {
            runSimulationLoop(simulationMap);
        }
    };

    private void initMap() {
        initObjects.initObjectsOnTheMap(simulationMap);
    }

    private boolean shouldRespawn() {
        return turnCount % 10 == 0 && !simulationMap.isMapFull();
    }

    public void startSimulation() {
        simulationThread.start();
        resumeSimulation();
    }

    public void resumeSimulation() {
        isPaused = false;
        isMoveActive = false;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private void handleStoppedSimulationThread() {
        logger.log(Level.INFO, PAUSED);
        menuOptionsPrinter.printPauseOptions();

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.INFO, THREAD_INTERRUPTED);
            }
        }
    }

    public void pauseSimulation() {
        isPaused = true;
        isMoveActive = true;
    }

    public void nextTurn() {
        isNextTurn = true;
        isMoveActive = false;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    public void stopSimulation() {
        logger.log(Level.INFO, STOPPED);
        resumeSimulation();
        simulationThread.interrupt();
        userInput.interruptInputThread();
        System.exit(0);
    }

    public void respawnGrass() {
        respawnGrassAction.execute(simulationMap);
        logger.log(Level.INFO, ADDED_GRASS);
    }

    public void respawnHerbivore() {
        respawnHerbivoreAction.execute(simulationMap);
        logger.log(Level.INFO, ADDED_HERBIVORES);
    }
}
