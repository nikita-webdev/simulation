package simulation;

import simulation.actions.InitObjects;
import simulation.utils.GameLoop;
import java.util.Scanner;

public class Simulation {
    public static boolean runningThread = true;

    public static void main(String[] args) throws InterruptedException {
        /*Главный класс приложения, включает в себя:
            Карту
            Счётчик ходов
            Рендерер поля
            Actions - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)
            Методы:
                nextTurn() - просимулировать и отрендерить один ход
                startSimulation() - запустить бесконечный цикл симуляции и рендеринга
                pauseSimulation() - приостановить бесконечный цикл симуляции и рендеринга

        */

//        final int NUMBER_OF_OBJECTS = 10;
//        final int MAX_X_MAP_SIZE = 9;
//        final int MAX_Y_MAP_SIZE = 5;

        GameLoop gameLoop = new GameLoop(2000);

        InitObjects initObjects = new InitObjects();
        initObjects.initObjectsOnTheMap(10, 0, 2, 1);

        Renderer renderer = new Renderer();
        renderer.createMap();

        Thread gameThread = new Thread() {
            public void run() {
                try {
                    gameLoop.startGame();
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
                        runningThread = false;
                    } else if (userInput.equals("s")) {
                        runningThread = true;
                    } else if (userInput.equals("g")) {
                        initObjects.initGrass(10);
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

    void nextTurn() {
        // просимулировать и отрендерить один ход

    }

    void startSimulation() {
        // запустить бесконечный цикл симуляции и рендеринга

    }

    void pauseSimulation() {
        // приостановить бесконечный цикл симуляции и рендеринга

    }
}
