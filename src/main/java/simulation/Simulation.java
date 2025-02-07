package simulation;

import simulation.actions.InitObjects;
import simulation.actions.MoveAllCreatures;

public class Simulation {
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

        InitObjects initObjects = new InitObjects();
        initObjects.initObjectsOnTheMap(1, 1, 1, 10);

        Renderer renderer = new Renderer();
        renderer.createMap();
        int i = 0;

        MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

        while (true) {
            // makeMove();

            if(i>0) {
                moveAllCreatures.makeMoveAllCreatures();
            }

            renderer.renderMap();


            i++;
            System.out.println("Ход: " + i);


            if (i == 10) {
                break;
            }

//            Thread.sleep(1000);

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
