package simulation.config;

public class LoggerMessages {
    private LoggerMessages() {

    }

    // Simulation
    public static final String PAUSED = "The simulation has been paused.";
    public static final String THREAD_INTERRUPTED = "The thread was interrupted.";
    public static final String STOPPED = "The simulation has been stopped.";
    public static final String ADDED_GRASS = "Grass has been added to the simulation.";
    public static final String ADDED_HERBIVORES = "Herbivores have been added to the simulation.";

    // UserInput
    public static final String PAUSE_UNAVAILABLE = "Pause unavailable: simulation hasn't started yet. Choose option 1 to start.";
    public static final String NO_SUCH_COMMAND = "No such command. Enter a number from the list.";

    // SimulationMap
    public static final String MAP_FULL = "Unable to add new object. Maximum number of objects on the map reached.";

    // Creature
    public static final String MOVE_MESSAGE = "\uD83D\uDC3E %s moves to (%d,%d).";
    public static final String FOOD_NOT_FOUND = "❌ %s couldn't find any suitable food.";
    public static final String DIE_MESSAGE = "\uD83D\uDC80 %s died.";

    public static final String EAT_MESSAGE = "\uD83C\uDF3E %s ate %s at (%d,%d).";
    public static final String ATTACK_MESSAGE = "❗ %s attacked %s at (%d,%d).";
}
