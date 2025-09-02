package simulation.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileHandlerFormatter extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String date = dateFormat.format(new Date(record.getMillis()));
        String levelAndMessage = " " + record.getLevel() + ": " + record.getMessage() + "\n";

        switch (record.getLevel().toString()) {
            case "INFO", "SEVERE", "WARNING":
                break;
            default:
        }

        return date + levelAndMessage;
    }
}
