package simulation.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_LIGHT_GRAY = "\u001B[37m";

    @Override
    public String format(LogRecord record) {
        String baseFormat = dateFormat.format(new Date(record.getMillis())) + " " +
                record.getLevel() + ": " + record.getMessage() + "\n";

        String color = switch (record.getLevel().toString()) {
            case "INFO" -> ANSI_LIGHT_GRAY;
            case "WARNING" -> ANSI_YELLOW;
            case "SEVERE" -> ANSI_RED;
            default -> ANSI_LIGHT_GRAY;
        };

        return color + baseFormat + ANSI_RESET;
    }
}
