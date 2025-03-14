package simulation.utils;

import simulation.Renderer;
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
        while (turnCount > 0) {
            if(turn>0) {
                moveAllCreatures.makeMoveAllCreatures();
            }

            renderer.renderMap();

            turn++;
            System.out.println("Ход: " + turn);

            Thread.sleep(1000);
        }
    }
}
