package simulation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        Simulation simulation = new Simulation();

        simulation.start();
    }
}