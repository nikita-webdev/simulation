package simulation.animals;

import simulation.Entity;
import simulation.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    private SimulationMap map = SimulationMap.getInstance();

    int speed;
    int hp;

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    // Наверное, лучше получать значения через геттер и устанавливать через сеттер
    public void makeMove() {
        int[] finish = new int[] {9, 9};
        int finishX = finish[0];
        int finishY = finish[1];

        if (positionX != finishX) {
            if (positionX < finishX) {
                positionX += speed;
            } else {
                positionX -= speed;
            }
        }

        if (positionY != finishY) {
            if (positionY < finishY) {
                positionY += speed;
            } else {
                positionY -= speed;
            }
        }
    }

    public void searchPath() {
        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        int[] startPosition = new int[] {this.positionX, this.positionY};
        graphsQueue.add(startPosition);
        visited.add(Arrays.toString(startPosition));

        boolean grassIsFind = false;

        while (!graphsQueue.isEmpty() && !grassIsFind) {
            int[] currentPosition = graphsQueue.poll();
            int[][] childNodes = new int[8][];

            int[][] offsets = {
                    {0, -1},
                    {1, -1},
                    {1, 0},
                    {1, 1},
                    {0, 1},
                    {-1, 1},
                    {-1, 0},
                    {-1, -1}
            };

            for (int i = 0; i < offsets.length; i++) {
                childNodes[i] = new int[] {
                        currentPosition[0] + offsets[i][0],
                        currentPosition[1] + offsets[i][1]
                };
            }

            System.out.println("currentPosition: " + Arrays.toString(currentPosition));

            for (int[] child : childNodes) {
                String childKey = Arrays.toString(child);
                if (!visited.contains(childKey)) {
                    if(map.isGrass(child)) {
                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                        graphsQueue.add(child);
                        visited.add(childKey);
                        grassIsFind = true;
                        break;
                    } else {
                        System.out.println("No. This is not Grass: " + Arrays.toString(child));
                        graphsQueue.add(child);
                        visited.add(childKey);
                    }
                }
            }
        }
    }
}
