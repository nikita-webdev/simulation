package simulation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.actions.Action;
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

    public synchronized void pause() {
        if (!isRunning()) {
            return;
        }

        state = SimulationState.PAUSED;
    }

    public synchronized void resume() {
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

    public void stop() {
        logger.log(Level.INFO, LoggerMessages.STOPPED);
        state = SimulationState.STOPPED;

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

    public boolean isNotStarted() {
        return state == SimulationState.NOT_STARTED;
    }

    private void runSimulationLoop() {
        init();

        while (!Thread.currentThread().isInterrupted()) {
            if (isPaused()) {
                if (isNextTurn) {
                    tick();
                    isNextTurn = false;
                } else {
                    handlePausedSimulationThread();
                }
                continue;
            }

            if (state == SimulationState.STOPPED) {
                break;
            }

            tick();
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

    private void startSimulation() {
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
        @Override
        public void run() {
            runSimulationLoop();
        }
    };

    @Override
    public void onMapChange(SimulationMap simulationMap) {
        updateMap();
    }

    private void updateMap() {
        renderer.render(simulationMap);

        turnCount++;
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
