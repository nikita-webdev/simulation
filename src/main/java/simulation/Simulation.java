package simulation;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.initActions.InitObjects;
import simulation.actions.turnActions.MoveAllCreatures;
import simulation.actions.turnActions.RespawnGrassAction;
import simulation.actions.turnActions.RespawnHerbivoreAction;
import simulation.map.SimulationMap;
import simulation.menu.MenuOptionsPrinter;

import static simulation.config.LoggerMessages.*;

public class Simulation {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());
    private static final Object pauseLock = new Object();
    private static final String START_RESUME = "1";
    private static final String PAUSE = "2";
    private static final String NEXT_TURN = "3";
    private static final String RESPAWN_GRASS = "4";
    private static final String RESPAWN_HERBIVORE = "5";
    private static final String EXIT = "0";

    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();
    private final SimulationMap simulationMap;
    private final InitObjects initObjects = new InitObjects();
    private final RespawnGrassAction respawnGrassAction = new RespawnGrassAction();
    private final RespawnHerbivoreAction respawnHerbivoreAction = new RespawnHerbivoreAction();
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;
    }

    public void launch() {
        menuOptionsPrinter.printStartOptions();
        userInputThread.start();
    }

    private void startSimulation() {
        simulationThread.start();
        resumeSimulation();
    }

    private void runSimulationLoop(SimulationMap simulationMap) {
        initMap();

        while (!Thread.currentThread().isInterrupted()) {
            if (shouldRespawn()) {
                respawnGrassAction.execute(simulationMap);
                respawnHerbivoreAction.execute(simulationMap);
            }

            if (isPaused) {
                handlePausedSimulationThread();
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

    private void resumeSimulation() {
        isPaused = false;
        moveAllCreatures.setMoveAllowed(true);

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private void nextTurn() {
        isNextTurn = true;
        moveAllCreatures.setMoveAllowed(true);

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private void pauseSimulation() {
        isPaused = true;
        moveAllCreatures.setMoveAllowed(false);
    }

    private void stopSimulation() {
        logger.log(Level.INFO, STOPPED);
        simulationThread.interrupt();
        userInputThread.interrupt();
        System.exit(0);
    }

    private void handlePausedSimulationThread() {
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

    private void initMap() {
        initObjects.initObjectsOnTheMap(simulationMap);
    }

    private boolean shouldRespawn() {
        boolean isTurnMultipleOfTen = turnCount % 10 == 0;

        return isTurnMultipleOfTen && !simulationMap.isMapFull();
    }

    private final Thread simulationThread = new Thread() {
        public void run() {
            runSimulationLoop(simulationMap);
        }
    };

    private final Thread userInputThread = new Thread() {
        public void run() {
            Scanner scanner = new Scanner(System.in);

            while (!Thread.currentThread().isInterrupted()) {
                String userInput = scanner.nextLine().trim().toLowerCase();

                if (!isRunning) {
                    handleStartMenu(userInput);
                } else {
                    handlePauseMenu(userInput);
                }
            }

            scanner.close();
        }
    };

    private void handleStartMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> {
                startSimulation();
                isRunning = true;
            }
            case PAUSE -> {
                logger.log(Level.INFO, PAUSE_UNAVAILABLE);
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, NO_SUCH_COMMAND);
            }
        }
    }

    private void handlePauseMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> resumeSimulation();
            case PAUSE -> pauseSimulation();
            case NEXT_TURN -> nextTurn();
            case RESPAWN_GRASS -> {
                respawnGrassAction.execute(simulationMap);
                logger.log(Level.INFO, ADDED_GRASS);
            }
            case RESPAWN_HERBIVORE -> {
                respawnHerbivoreAction.execute(simulationMap);
                logger.log(Level.INFO, ADDED_HERBIVORES);
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, NO_SUCH_COMMAND);
                menuOptionsPrinter.printPauseOptions();
            }
        }
    }
}
