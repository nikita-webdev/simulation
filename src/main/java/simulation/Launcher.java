package simulation;

import simulation.menu.ConsoleMenuHandler;
import simulation.menu.MenuOptionsPrinter;
import simulation.scenario.DefaultScenario;
import simulation.scenario.SimulationScenario;

public class Launcher {
    public synchronized void launch() {
        SimulationScenario scenario = new DefaultScenario();
        Simulation simulation = scenario.create();
        MenuOptionsPrinter menuPrinter = new MenuOptionsPrinter();
        ConsoleMenuHandler menu = new ConsoleMenuHandler(simulation, simulation.getSimulationMap(), menuPrinter);

        menu.start();
    }
}
