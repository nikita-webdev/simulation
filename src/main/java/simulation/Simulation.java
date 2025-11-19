package simulation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.Action;
import simulation.actions.turn_actions.MoveAllCreatures;
import simulation.config.SimulationConfig;
import simulation.config.logging.LoggerMessages;
import simulation.menu.ConsoleMenuHandler;
import simulation.renderer.ConsoleRenderer;
import simulation.renderer.icon.AsciiIconProvider;
import simulation.renderer.icon.EmojiIconProvider;
import simulation.simulation_map.MapChangeListener;
import simulation.simulation_map.SimulationMap;
import simulation.menu.MenuOptionsPrinter;

public class Simulation implements MapChangeListener {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    private final Object pauseLock = new Object();
    private final SimulationMap simulationMap;

    private final ConsoleRenderer renderer = new ConsoleRenderer(new EmojiIconProvider());
//    private final ConsoleRenderer renderer = new ConsoleRenderer(new AsciiIconProvider());

    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();
    private ConsoleMenuHandler consoleMenuHandler;

    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

    private final List<Action> initActions;
    private final List<Action> turnActions;

    private volatile SimulationState state = SimulationState.NOT_STARTED;
    private volatile boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap, List<Action> initActions, List<Action> turnActions) {
        this.simulationMap = simulationMap;
        this.initActions = initActions;
        this.turnActions = turnActions;

        simulationMap.addListener(this);
    }

    public synchronized void launch() {
        this.consoleMenuHandler = new ConsoleMenuHandler(this, simulationMap, menuOptionsPrinter);
        consoleMenuHandler.start();
        startSimulation();
    }

    public void startSimulation() {
        if (state != SimulationState.NOT_STARTED) {
            return;
        }

        init();
        state = SimulationState.RUNNING;
        simulationThread.start();

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public synchronized void pauseSimulation() {
        if (state != SimulationState.RUNNING) {
            return;
        }

        state = SimulationState.PAUSED;
    }

    public synchronized void resumeSimulation() {
        if (state != SimulationState.PAUSED) {
            return;
        }

        state = SimulationState.RUNNING;

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public void nextTurn() {
        if (state != SimulationState.PAUSED) {
            return;
        }

        isNextTurn = true;

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public void stopSimulation() {
        logger.log(Level.INFO, LoggerMessages.STOPPED);
        state = SimulationState.NOT_STARTED;

        simulationThread.interrupt();

        if (consoleMenuHandler != null) {
            consoleMenuHandler.stop();
        }

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public boolean isRunning() {
        return state == SimulationState.RUNNING;
    }

    public boolean isPaused() {
        return state == SimulationState.PAUSED;
    }

    public SimulationState getState() {
        return state;
    }

    private void runSimulationLoop(SimulationMap simulationMap) {
        init();

        while (!Thread.currentThread().isInterrupted()) {
            tick();

            if (isPaused()) {
                handlePausedSimulationThread();
            } else {
                moveAllCreatures.execute(simulationMap);
            }

            if (isPaused() && isNextTurn) {
                moveAllCreatures.execute(simulationMap);
                isNextTurn = false;
            }
        }
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

    @Override
    public void onMapChange(SimulationMap simulationMap) {
        updateMap();
    }

    public void updateMap() {
        renderer.render(simulationMap);

        turnCount++;
        System.out.println("Turn: " + turnCount);

        try {
            Thread.sleep(SimulationConfig.DELAY_MOVE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
