package simulation;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.initActions.InitObjects;
import simulation.actions.turnActions.MoveAllCreatures;
import simulation.actions.turnActions.RespawnGrassAction;
import simulation.actions.turnActions.RespawnHerbivoreAction;
import simulation.map.SimulationMap;
import simulation.printer.StartPrinter;

public class Simulation {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());
    private static final StartPrinter startPrinter = new StartPrinter();
    private static final Object pauseLock = new Object();
    private final SimulationMap simulationMap = new SimulationMap();
    private final InitObjects initObjects = new InitObjects();
    private final RespawnGrassAction respawnGrassAction = new RespawnGrassAction();
    private final RespawnHerbivoreAction respawnHerbivoreAction = new RespawnHerbivoreAction();
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();


    public static boolean runningThread = false;
    public static boolean nextTurn = false;

    public void launch() {
        simulationThread.start();
        userInputThread.start();
    }

    private void simulationLoop(SimulationMap simulationMap) throws InterruptedException {
        initObjects.initObjectsOnTheMap(simulationMap);

        while (true) {
            if (!Thread.currentThread().isInterrupted()) {
                if (runningThread) {
                    moveAllCreatures.execute(simulationMap);
                } else {
                    handleStoppedThread();
                }

                if (!runningThread && nextTurn) {
                    moveAllCreatures.execute(simulationMap);

                    nextTurn = false;
                }
            }
        }
    }

    Thread simulationThread = new Thread() {
        public void run() {
            try {
                simulationLoop(simulationMap);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    Thread userInputThread = new Thread() {
        public void run() {
            Scanner scanner = new Scanner(System.in);

            if (!Thread.currentThread().isInterrupted()) {
                while (true) {
                    String userInput = scanner.nextLine().trim().toLowerCase();

                    switch (userInput) {
                        case "1" -> startSimulation();
                        case "2" -> pauseSimulation();
                        case "3" -> nextTurn();
                        case "4" -> respawnGrassAction.execute(simulationMap);
                        case "5" -> respawnHerbivoreAction.execute(simulationMap);
                        case "0" -> stopSimulation();
                        default -> logger.log(Level.INFO, "No such command. Enter a number from the list.");
                    }
                }
            } else {
                scanner.close();
            }
        }
    };

    private void handleStoppedThread() throws InterruptedException {
        logger.log(Level.INFO, "The simulation has been paused.");
        startPrinter.startPrint();

        synchronized (pauseLock) {
            pauseLock.wait();
        }
    }

    private static void nextTurn() {
        nextTurn = true;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private static void startSimulation() {
        runningThread = true;

        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    private static void pauseSimulation() {
        runningThread = false;
    }

    private void stopSimulation() {
        logger.log(Level.INFO, "The simulation has been stopped.");
        startSimulation();
        simulationThread.interrupt();
        userInputThread.interrupt();
        System.exit(0);
    }
}
