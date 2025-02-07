package simulation.animals;

import simulation.Entity;
import simulation.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    private SimulationMap map = SimulationMap.getInstance();

    int speed;
    int hp;
    int[] goalEatCoordinates = new int[2];

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    public void makeMove(int[] goalNode) {
        int finishX = goalNode[0];
        int finishY = goalNode[1];


        if (positionX != finishX) {
            if (positionX < finishX) {
                positionX = positionX + speed;
            } else {
                positionX = positionX - speed;
            }
        }

        if (positionY != finishY) {
            if (positionY < finishY) {
                positionY = positionY + speed;
            } else {
                positionY = positionY - speed;
            }
        }

//        if(positionX != finishX || positionY != finishY) {
//            int[] nextStep = new int[2];
//
//            if(!map.isCoordinatesOccupied(nextStep)) {
//                if (positionX != finishX) {
//                    if (positionX < finishX) {
//                        nextStep[0] = positionX + speed;
//                    } else {
//                        nextStep[0] = positionX - speed;
//                    }
//                }
//
//                if (positionY != finishY) {
//                    if (positionY < finishY) {
//                        nextStep[1] = positionY + speed;
//                    } else {
//                        nextStep[1] = positionY - speed;
//                    }
//                }
//
//                if(!map.isCoordinatesOccupied(nextStep)){
//                    positionX = nextStep[0];
//                    positionY = nextStep[1];
//                    System.out.println("Сделать шаг");
//                }
//            }
//        }
    }

    public int[] searchPath() {
        int[] goalNode = new int[2];

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
                    if((child[0] < SimulationMap.MAP_SIZE_X && child[0] >= 0) && (child[1] < SimulationMap.MAP_SIZE_Y && child[1] >= 0)) {
                        if(map.isGrass(child)) {
                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childKey);
                            goalNode = child;
                            grassIsFind = true;
                            break;
                        } else {
                        System.out.println("No. This is not Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childKey);
                        }
                    } else {
                        visited.add(childKey);
                    }
                }
            }
        }
        return goalNode;
    }

    public void eat() {

    }

    public int[] getGoalEatCoordinates() {
        return goalEatCoordinates;
    }

    public void setGoalEatCoordinates(int[] newEatCoordinates) {
        goalEatCoordinates[0] = newEatCoordinates[0];
        goalEatCoordinates[1] = newEatCoordinates[1];
    }
}
