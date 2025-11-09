package simulation.menu;

public class MenuOptionsPrinter {

    public void printStartOptions() {
        System.out.println("Welcome to the simulation!");
        System.out.println("Please select an option and enter a number:");
        System.out.println(MenuOptions.START + " - " + MenuOptions.START_TEXT);
        System.out.println(MenuOptions.PAUSE + " - " + MenuOptions.PAUSE_TEXT);
        System.out.println(MenuOptions.QUIT + " - " + MenuOptions.QUIT_TEXT);
    }

    public void printPauseOptions() {
        System.out.println("Please select an option and enter a number:");
        System.out.println(MenuOptions.RESUME + " - " + MenuOptions.RESUME_TEXT);
        System.out.println(MenuOptions.PAUSE + " - " + MenuOptions.PAUSE_TEXT);
        System.out.println(MenuOptions.NEXT_TURN + " - " + MenuOptions.NEXT_TURN_TEXT);
        System.out.println(MenuOptions.ADD_GRASS + " - " + MenuOptions.ADD_GRASS_TEXT);
        System.out.println(MenuOptions.ADD_HERBIVORES + " - " + MenuOptions.ADD_HERBIVORES_TEXT);
        System.out.println(MenuOptions.QUIT + " - " + MenuOptions.QUIT_TEXT);
    }
}