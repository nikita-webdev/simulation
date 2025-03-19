package simulation.utils;

import simulation.Renderer;
import simulation.Simulation;
import simulation.actions.MoveAllCreatures;

public class GameLoop {
    Renderer renderer = new Renderer();
    MoveAllCreatures moveAllCreatures = new MoveAllCreatures();

    int turn = 0;
    int turnCount;

    public GameLoop(int turnCount) {
        this.turnCount = turnCount;
    }

    public void startGame() throws InterruptedException {
        while (turn < turnCount) {
            if (Simulation.runningThread == true) {
                if (turn>0) {
                    moveAllCreatures.makeMoveAllCreatures();
                }

                renderer.renderMap();

                turn++;
                System.out.println("Ход: " + turn);

                Thread.sleep(1000);
            } else if (Simulation.runningThread == false){
                System.out.println("Поток остановлен.");
                Thread.sleep(2000);
            }
        }
    }
}
