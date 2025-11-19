package simulation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.Action;
import simulation.actions.turn_actions.MoveAllCreatures;
import simulation.config.SimulationConfig;
import simulation.config.logging.LoggerMessages;
import simulation.renderer.Renderer;
import simulation.simulation_map.MapChangeListener;
import simulation.simulation_map.SimulationMap;

public class Simulation implements MapChangeListener {
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    private final Object pauseLock = new Object();
    private final SimulationMap simulationMap;
    private final Renderer renderer;
    private final MoveAllCreatures moveAllCreatures = new MoveAllCreatures();
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private volatile SimulationState state = SimulationState.NOT_STARTED;
    private volatile boolean isNextTurn = false;
    private int turnCount = 0;

    public Simulation(SimulationMap simulationMap, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.simulationMap = simulationMap;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;

        simulationMap.addListener(this);
    }

    public SimulationMap getSimulationMap() {
        return simulationMap;
    }

    public void start() {
        if (state != SimulationState.NOT_STARTED) {
            return;
        }

        startSimulation();

        state = SimulationState.RUNNING;
    }

    public synchronized void pauseSimulation() {
        if (!isRunning()) {
            return;
        }

        state = SimulationState.PAUSED;
    }

    public synchronized void resumeSimulation() {
        if (!isPaused()) {
            return;
        }

        state = SimulationState.RUNNING;

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public void nextTurn() {
        if (!isPaused()) {
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

            if (isPaused() && !isNextTurn) {
                handlePausedSimulationThread();
            }

            if (isPaused() && isNextTurn) {
                tick();
                isNextTurn = false;
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
            turnCount++;
        }
    }

    private void startSimulation() {
        init();
        simulationThread.start();

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    private void handlePausedSimulationThread() {
        logger.log(Level.INFO, LoggerMessages.PAUSED);

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.INFO, LoggerMessages.THREAD_INTERRUPTED);
            }
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

    private void updateMap() {
        renderer.render(simulationMap);
        System.out.println("Turn: " + turnCount);

        sleep(SimulationConfig.DELAY_MOVE);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
