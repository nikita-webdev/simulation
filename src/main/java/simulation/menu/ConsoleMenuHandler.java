package simulation.menu;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.Simulation;
import simulation.config.logging.LoggerMessages;
import simulation.config.spawn.SpawnConfig;
import simulation.entities.animals.Herbivore;
import simulation.entities.objects.Grass;
import simulation.renderer.EntityType;
import simulation.simulation_map.SimulationMap;
import simulation.actions.init_actions.SpawnAction;

public class ConsoleMenuHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ConsoleMenuHandler.class.getName());

    private static final String START_RESUME = "1";
    private static final String PAUSE = "2";
    private static final String NEXT_TURN = "3";
    private static final String RESPAWN_GRASS = "4";
    private static final String RESPAWN_HERBIVORE = "5";
    private static final String EXIT = "0";

    private final Simulation simulation;
    private final SimulationMap simulationMap;
    private final MenuOptionsPrinter menuOptionsPrinter;

    private final Thread thread;
    private volatile boolean running = false;

    public ConsoleMenuHandler(Simulation simulation, SimulationMap simulationMap, MenuOptionsPrinter menuOptionsPrinter) {
        this.simulation = simulation;
        this.simulationMap = simulationMap;
        this.menuOptionsPrinter = menuOptionsPrinter;
        this.thread = new Thread(this, "ConsoleMenuHandler");
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            menuOptionsPrinter.printStartOptions();

            while (running && !Thread.currentThread().isInterrupted()) {
                if (!scanner.hasNextLine()) {
                    break;
                }

                String userInput = scanner.nextLine().trim().toLowerCase();

                if (!simulation.isRunning()) {
                    handleStartMenu(userInput);
                } else {
                    handlePauseMenu(userInput);
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "ConsoleMenuHandler stopped: " + e.getMessage());
        }
    }

    private void handleStartMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> {
                simulation.start();
            }
            case PAUSE -> {
                simulation.pauseSimulation();
                logger.log(Level.INFO, LoggerMessages.PAUSE_UNAVAILABLE);
            }
            case EXIT -> {
                simulation.stopSimulation();
                stop();
                logger.log(Level.INFO, LoggerMessages.STOPPED);
            }
            default -> logger.log(Level.INFO, LoggerMessages.NO_SUCH_COMMAND);
        }
    }

    private void handlePauseMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> simulation.resumeSimulation();
            case PAUSE -> {
                simulation.pauseSimulation();
                logger.log(Level.INFO, LoggerMessages.PAUSE_UNAVAILABLE);
            }
            case NEXT_TURN -> simulation.nextTurn();
            case RESPAWN_GRASS -> {
                new SpawnAction(() -> new Grass(EntityType.GRASS, "Grass"), SpawnConfig.RESPAWN_GRASS).execute(simulationMap);
                logger.log(Level.INFO, LoggerMessages.ADDED_GRASS);
            }
            case RESPAWN_HERBIVORE -> {
                new SpawnAction(() -> new Herbivore(EntityType.HERBIVORE, "Herbivore"), SpawnConfig.RESPAWN_HERBIVORE).execute(simulationMap);
                logger.log(Level.INFO, LoggerMessages.ADDED_HERBIVORES);
            }
            case EXIT -> {
                simulation.stopSimulation();
                stop();
                logger.log(Level.INFO, LoggerMessages.STOPPED);
            }
            default -> {
                logger.log(Level.INFO, LoggerMessages.NO_SUCH_COMMAND);
                menuOptionsPrinter.printPauseOptions();
            }
        }
    }
}
