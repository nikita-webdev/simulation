package simulation.config.spawn;

public class SpawnConfig {
    private SpawnConfig() {
    }

    // Spawn config
    public static final int INITIAL_GRASS = 40;
    public static final int INITIAL_ROCKS = 5;
    public static final int INITIAL_TREES = 5;
    public static final int INITIAL_PREDATORS = 2;
    public static final int INITIAL_HERBIVORES = 5;

    // Respawn config
    public static final int RESPAWN_GRASS_THRESHOLD = 20;
    public static final int RESPAWN_HERBIVORE_THRESHOLD = 3;
    public static final int RESPAWN_GRASS = 10;
    public static final int RESPAWN_HERBIVORE = 5;
}
