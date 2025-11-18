package simulation;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.Action;
import simulation.actions.init_actions.*;
import simulation.actions.turn_actions.MoveAllCreatures;
import simulation.config.SimulationConfig;
import simulation.config.logging.LoggerMessages;
import simulation.config.spawn.SpawnConfig;
import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.renderer.ConsoleRenderer;
import simulation.renderer.icon.AsciiIconProvider;
import simulation.renderer.icon.EmojiIconProvider;
import simulation.renderer.EntityType;
import simulation.simulation_map.MapChangeListener;
import simulation.simulation_map.SimulationMap;
import simulation.menu.MenuOptionsPrinter;

public class Simulation implements MapChangeListener {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());
    private static final Object pauseLock = new Object();

    private static final String START_RESUME = "1";
    private static final String PAUSE = "2";
    private static final String NEXT_TURN = "3";
    private static final String RESPAWN_GRASS = "4";
    private static final String RESPAWN_HERBIVORE = "5";
    private static final String EXIT = "0";

    private final SimulationMap simulationMap;

    private final ConsoleRenderer renderer = new ConsoleRenderer(new EmojiIconProvider());
//    private final ConsoleRenderer renderer = new ConsoleRenderer(new AsciiIconProvider());

    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();

    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

    private final List<Action> initActions;
    private final List<Action> turnActions;

    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap, List<Action> initActions, List<Action> turnActions) {
        this.simulationMap = simulationMap;
        this.initActions = initActions;
        this.turnActions = turnActions;

        simulationMap.addListener(this);
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
        init();

        while (!Thread.currentThread().isInterrupted()) {
            tick();

            if (isPaused) {
                handlePausedSimulationThread();
            } else {
                moveAllCreatures.execute(simulationMap);
            }

            if (isPaused && isNextTurn) {
                moveAllCreatures.execute(simulationMap);
                isNextTurn = false;
            }
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
        logger.log(Level.INFO, LoggerMessages.STOPPED);
        simulationThread.interrupt();
        userInputThread.interrupt();
        System.exit(0);
    }

    private void handlePausedSimulationThread() {
        logger.log(Level.INFO, LoggerMessages.PAUSED);
        menuOptionsPrinter.printPauseOptions();

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.INFO, LoggerMessages.THREAD_INTERRUPTED);
            }
        }
    }

    private void init() {
        for (Action action : initActions) {
            action.execute(simulationMap);
        }
    }

    private void tick() {
        for (Action action : turnActions) {
            action.execute(simulationMap);
        }
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
                logger.log(Level.INFO, LoggerMessages.PAUSE_UNAVAILABLE);
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, LoggerMessages.NO_SUCH_COMMAND);
            }
        }
    }

    private void handlePauseMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> resumeSimulation();
            case PAUSE -> pauseSimulation();
            case NEXT_TURN -> nextTurn();
            case RESPAWN_GRASS -> {
                new SpawnAction(() -> new Grass(EntityType.GRASS, "Grass"), SpawnConfig.RESPAWN_GRASS).execute(simulationMap);
                logger.log(Level.INFO, LoggerMessages.ADDED_GRASS);
            }
            case RESPAWN_HERBIVORE -> {
                new SpawnAction(() -> new Herbivore(EntityType.HERBIVORE, "Herbivore"), SpawnConfig.RESPAWN_HERBIVORE).execute(simulationMap);
                logger.log(Level.INFO, LoggerMessages.ADDED_HERBIVORES);
            }
            case EXIT -> stopSimulation();
            default -> {
                logger.log(Level.INFO, LoggerMessages.NO_SUCH_COMMAND);
                menuOptionsPrinter.printPauseOptions();
            }
        }
    }

    @Override
    public void onMapChange(SimulationMap simulationMap) {
        updateMap();
        turnCount++;
        System.out.println("Turn: " + turnCount);
    }

    public void updateMap() {
        renderer.render(simulationMap);

        try {
            Thread.sleep(SimulationConfig.DELAY_MOVE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
