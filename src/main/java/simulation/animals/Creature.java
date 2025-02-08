package simulation.animals;

import simulation.Entity;
import simulation.SimulationMap;

import java.util.*;

public abstract class Creature extends Entity {
    private SimulationMap map = SimulationMap.getInstance();

    List<int[]> pathToGoal;
    int numberOfStep = 0;

    int speed;
    int hp;
    int[] goalEatCoordinates = new int[2];

    public Creature(String name, int positionX, int positionY) {
        super(name, positionX, positionY);
    }

    public void makeMove(int[] goalNode) {
        int finishX = goalNode[0];
        int finishY = goalNode[1];

        if(positionX != finishX || positionY != finishY) {
            int[] nextStep = coordinates;
            nextStep = pathToGoal.get(numberOfStep);
            System.out.println(Arrays.toString(pathToGoal.get(numberOfStep)));
            System.out.printf("numberOfStep: %s, pathToGoal.size(): %s%n", numberOfStep, pathToGoal.size());

                if(!map.isCoordinatesOccupied(nextStep)){
                    positionX = nextStep[0];
                    positionY = nextStep[1];
                    System.out.println("Take a step on: " + Arrays.toString(nextStep));
                    if(numberOfStep < pathToGoal.size() - 1) {
                        numberOfStep++;
                    }
                } else if(goalNode[0] == nextStep[0] && goalNode[1] == nextStep[1]) {
                    System.out.printf("Arrived at goal.%s, %s%n", Arrays.toString(goalNode), Arrays.toString(nextStep));
                } else {
                    System.out.println((Arrays.equals(goalNode, nextStep)));
                    System.out.printf("%s, %s%n", Arrays.toString(goalNode), Arrays.toString(nextStep));
                    System.out.println("Should have taken a step, but it was not taken on: " + Arrays.toString(nextStep));
                }
        }
    }

    public int[] searchPath() {
        int[] goalNode = new int[2];

        Queue<int[]> graphsQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[]> parentMap = new HashMap<>();

        int[] lastChild = new int[2];

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

            for (int[] child : childNodes) {
                String childKey = Arrays.toString(child);
                if (!visited.contains(childKey)) {
                    if ((child[0] < SimulationMap.MAP_SIZE_X && child[0] >= 0) && (child[1] < SimulationMap.MAP_SIZE_Y && child[1] >= 0)) {
                        if (map.isGrass(child)) {
                        System.out.println("Yes. This is Grass: " + Arrays.toString(child));
                            graphsQueue.add(child);
                            visited.add(childKey);
                            goalNode = child;
                            parentMap.put(Arrays.toString(goalNode), lastChild);
                            grassIsFind = true;
                            break;
                        } else if (map.isTreeOrRock(child)) {
                            visited.add(childKey);
                        } else {
                            if (!parentMap.containsKey(Arrays.toString(child))) {
                                parentMap.put(Arrays.toString(child), currentPosition);
                                lastChild = child;
                            }

                            graphsQueue.add(child);
                            visited.add(childKey);
                        }
                    } else {
                        visited.add(childKey);
                    }
                }
            }
        }
        pathToGoal = reconstructPath(parentMap, goalNode);
        return goalNode;
    }

    public List<int[]> reconstructPath(Map<String, int[]> parentMap, int[] goalCoordinates) {
        List<int[]> path = new LinkedList<>();
        for (int[] i = goalCoordinates; i != null; i = parentMap.get(Arrays.toString(i))) {
            path.add(i);
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
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
