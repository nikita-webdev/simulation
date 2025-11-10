package simulation;

import simulation.scenario.DefaultScenario;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        } catch (IOException e) {
            System.err.println("Logger configuration file not found.");
        }

        DefaultScenario defaultScenario = new DefaultScenario();
        Simulation simulation = defaultScenario.create();

        simulation.launch();
    }
}