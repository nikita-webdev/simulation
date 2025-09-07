package simulation.menu;

import simulation.Simulation;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInput {
    private static final Logger logger = Logger.getLogger(UserInput.class.getName());
    private static final String START_RESUME = "1";
    private static final String PAUSE = "2";
    private static final String NEXT_TURN = "3";
    private static final String RESPAWN_GRASS = "4";
    private static final String RESPAWN_HERBIVORE = "5";
    private static final String EXIT = "0";

    private final Simulation simulation;
    private final MenuOptionsPrinter menuOptionsPrinter = new MenuOptionsPrinter();

    public UserInput(Simulation simulation) {
        this.simulation = simulation;
    }

    private final Thread userInputThread = new Thread() {
        public void run() {
            Scanner scanner = new Scanner(System.in);

            while (!Thread.currentThread().isInterrupted()) {
                String userInput = scanner.nextLine().trim().toLowerCase();

                if (!simulation.isRunning) {
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
                simulation.startSimulation();
                simulation.isRunning = true;
            }
            case PAUSE -> {
                logger.log(Level.INFO, "Pause unavailable: simulation hasn't started yet. Choose option 1 to start.");
            }
            case EXIT -> simulation.stopSimulation();
            default -> {
                logger.log(Level.INFO, "No such command. Enter a number from the list:");
                menuOptionsPrinter.printStartOptions();
            }
        }
    }

    private void handlePauseMenu(String userInput) {
        switch (userInput) {
            case START_RESUME -> simulation.resumeSimulation();
            case PAUSE -> simulation.pauseSimulation();
            case NEXT_TURN -> simulation.nextTurn();
            case RESPAWN_GRASS -> simulation.respawnGrass();
            case RESPAWN_HERBIVORE -> simulation.respawnHerbivore();
            case EXIT -> simulation.stopSimulation();
            default -> {
                logger.log(Level.INFO, "No such command. Enter a number from the list:");
                menuOptionsPrinter.printPauseOptions();
            }
        }
    }

    public void startInputThread() {
        userInputThread.start();
    }

    public void interruptInputThread() {
        userInputThread.interrupt();
    }
}
