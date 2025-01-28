package simulation;

import simulation.actions.InitObjects;

public class Simulation {
    public static void main(String[] args) {
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
        initObjects.initObjectsOnTheMap(20, 10, 20, 10);

        Renderer renderer = new Renderer();
        renderer.createMap();
        renderer.renderMap();
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
