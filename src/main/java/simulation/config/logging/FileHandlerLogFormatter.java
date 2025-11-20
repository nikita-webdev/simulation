package simulation.config.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class FileHandlerLogFormatter extends Formatter {
    public FileHandlerLogFormatter() {
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String date = dateFormat.format(new Date(record.getMillis()));
        String levelAndMessage = " " + record.getLevel() + ": " + record.getMessage() + "\n";

        switch (record.getLevel().toString()) {
            case "INFO", "SEVERE", "WARNING":
                break;
            default:
                throw new IllegalArgumentException("Unexpected log level: " + record.getLevel());
        }


        return date + levelAndMessage;
    }
}
