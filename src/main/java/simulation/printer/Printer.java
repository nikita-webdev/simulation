package simulation.printer;

public class Printer {
    public void printStartMenu() {
        System.out.println("Welcome to the simulation!");
        System.out.println("Please select an option and enter a number:");
        System.out.println("1 - Start");
        System.out.println("2 - Pause");
        System.out.println("0 - Quit");
    }

    public void printPauseMenu() {
        System.out.println("Please select an option and enter a number:");
        System.out.println("1 - Resume");
        System.out.println("2 - Pause");
        System.out.println("3 - Next turn");
        System.out.println("4 - Add Grass");
        System.out.println("5 - Add Herbivores");
        System.out.println("0 - Quit");
    }
}
