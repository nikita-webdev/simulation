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

        /*
         * 1. Создаю список и добавляю объекты в список
         * 2. Рендерер рендерит карту
         */

        InitObjects initObjects = new InitObjects();
        initObjects.initObjectsOnTheMap(25);

        Renderer renderer = new Renderer(9,5);
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
