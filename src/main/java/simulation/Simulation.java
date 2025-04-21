package simulation;

import simulation.actions.InitObjects;
import simulation.map.Renderer;

import java.util.Scanner;

public class Simulation {
    public static boolean runningThread = true; // Thread with game is running
    public static boolean nextTurn = false;

    public static void main(String[] args) throws InterruptedException {
//        final int NUMBER_OF_OBJECTS = 10;
//        final int MAX_X_MAP_SIZE = 9;
//        final int MAX_Y_MAP_SIZE = 5;

        Game game = new Game(2000);

        InitObjects initObjects = new InitObjects();
        initObjects.initObjectsOnTheMap(1, 0, 20, 0, 0);

        Renderer renderer = new Renderer();
        renderer.createMap();

        Thread gameThread = new Thread() {
            public void run() {
                try {
                    game.gameLoop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread userInputThread = new Thread() {
            public void run() {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String userInput = scanner.nextLine().trim().toLowerCase();

                    if (userInput.equals("p")) {
                        pauseSimulation();
                    } else if (userInput.equals("s")) {
                        startSimulation();
                    } else if (userInput.equals("n")) {
                        nextTurn();
                    } else if (userInput.equals("g")) {
                        initObjects.initGrass(10);
                    } else if (userInput.equals("h")) {
                        initObjects.initHerbivore(1);
                    }
                }
            }
        };

        gameThread.start();
        userInputThread.start();

//            try {
//                if (System.getProperty("os.name").contains("Windows")) {
//                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//                } else {
//                    // Для Unix-подобных систем
//                    new ProcessBuilder("clear").inheritIO().start().waitFor();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }

    static void nextTurn() {
        // просимулировать и отрендерить один ход
        // For each creature
        nextTurn = true;
    }

    static void startSimulation() {
        // запустить бесконечный цикл симуляции и рендеринга
        runningThread = true;
    }

    private static void pauseSimulation() {
        // приостановить бесконечный цикл симуляции и рендеринга
        runningThread = false;
    }
}
