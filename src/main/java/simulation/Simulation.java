package simulation;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.initActions.InitObjects;
import simulation.actions.turnActions.MoveAllCreatures;
import simulation.actions.turnActions.RespawnGrassAction;
import simulation.actions.turnActions.RespawnHerbivoreAction;
import simulation.map.SimulationMap;
import simulation.printer.MenuOptionsPrinter;

public class Simulation {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());
    private static final String START_RESUME = "1";
    private static final String PAUSE = "2";
    private static final String NEXT_TURN = "3";
    private static final String RESPAWN_GRASS = "4";
    private static final String RESPAWN_HERBIVORE = "5";
    private static final String EXIT = "0";
    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();
    private static final Object pauseLock = new Object();
    private final SimulationMap simulationMap;
    private final InitObjects initObjects = new InitObjects();
    private final RespawnGrassAction respawnGrassAction = new RespawnGrassAction();
    private final RespawnHerbivoreAction respawnHerbivoreAction = new RespawnHerbivoreAction();
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();


    private boolean isSimulationRunning = false;
    private boolean isLoopActive = false;
    public static boolean isMoveActive = false;
    private boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;
    }

    public void launch() {
        menuOptionsPrinter.printStartOptions();
        userInputThread.start();
    }

    private void simulationLoop(SimulationMap simulationMap) {
        initObjects.initObjectsOnTheMap(simulationMap);

        while (!Thread.currentThread().isInterrupted()) {
            if (turnCount % 10 == 0 && !simulationMap.isMapFull()) {
                respawnGrassAction.execute(simulationMap);
                respawnHerbivoreAction.execute(simulationMap);
            }

            if (isLoopActive) {
                moveAllCreatures.execute(simulationMap);
            } else {
                handleStoppedThread();
            }

            if (!isLoopActive && isNextTurn) {
                moveAllCreatures.execute(simulationMap);
                isNextTurn = false;
            }

            turnCount++;
        }
    }

    Thread simulationThread = new Thread() {
        public void run() {
            simulationLoop(simulationMap);
        }
    };

    Thread userInputThread = new Thread() {
        public void run() {
            Scanner scanner = new Scanner(System.in);

                while (!Thread.currentThread().isInterrupted()) {
                    String userInput = scanner.nextLine().trim().toLowerCase();

                    if (!isSimulationRunning) {
                        handleStartMenu(userInput);
                    } else {
                        handlePauseMenu(userInput);
                    }
                }

            scanner.close();
        }
    };

    private void startSimulation() {
        simulationThread.start();
        resumeSimulation();
    }

    private void resumeSimulation() {
        isLoopActive = true;
        isMoveActive = false;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private void handleStoppedThread() {
        logger.log(Level.INFO, "The simulation has been paused.");
        menuOptionsPrinter.printPauseOptions();

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.INFO, "The thread was interrupted.");
            }
        }
    }

    private void pauseSimulation() {
        isLoopActive = false;
        isMoveActive = true;
    }

    private void nextTurn() {
        isNextTurn = true;
        isMoveActive = false;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private void stopSimulation() {
        logger.log(Level.INFO, "The simulation has been stopped.");
        resumeSimulation();
        simulationThread.interrupt();
        userInputThread.interrupt();
        System.exit(0);
    }

    private void handleStartMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> {
                startSimulation();
                isSimulationRunning = true;
            }
            case PAUSE -> {
                logger.log(Level.INFO, "Pause unavailable: simulation hasn't started yet. Choose option 1 to start.");
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, "No such command. Enter a number from the list:");
                menuOptionsPrinter.printStartOptions();
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
                logger.log(Level.INFO, "Grass has been added to the simulation.");
            }
            case RESPAWN_HERBIVORE -> {
                respawnHerbivoreAction.execute(simulationMap);
                logger.log(Level.INFO, "Herbivores have been added to the simulation.");
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, "No such command. Enter a number from the list:");
                menuOptionsPrinter.printPauseOptions();
            }
        }
    }
}
