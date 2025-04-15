package simulation;

import simulation.actions.MoveAllCreatures;
import simulation.field.Renderer;

public class Game {
    Renderer renderer = new Renderer();
    MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

    public static int turn = 0;
    int turnCount;

    public Game(int turnCount) {
        this.turnCount = turnCount;
    }

    public Game() {

    }

    public void gameLoop() throws InterruptedException {
        while (turn < turnCount) {
            if (turn == 0) {
                updateMap();
            }

            if (Simulation.runningThread) {
                if (turn>0) {
                    moveAllCreatures.makeMoveAllCreatures();
                }
            } else if (!Simulation.runningThread){
                System.out.println("Поток остановлен.");
                Thread.sleep(2000);
            }

            if (!Simulation.runningThread && Simulation.nextTurn) {
                if (turn>0) {
                    moveAllCreatures.makeMoveAllCreatures();
                }

                Simulation.nextTurn = false;
            }
        }
    }

    public void updateMap() throws InterruptedException {
        renderer.renderMap();
        turn++;
        System.out.println("Ход: " + turn);
        Thread.sleep(1000);
    }
}
