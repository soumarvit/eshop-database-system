package cvut.fel.store;

import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * Application logger class
 */
public class StoreLogger {

    private static final Logger logger= Logger.getLogger(StoreLogger.class.getName());

    private StoreLogger(){
    }

    public static void initLogger(){

        int loggerLevel = 1;

        switch (loggerLevel){
            case 1 -> logger.setLevel(Level.ALL);
            case 2 -> logger.setLevel(Level.CONFIG);
            case 3 -> logger.setLevel(Level.INFO);
            case 4 -> logger.setLevel(Level.SEVERE);
            case 5 -> logger.setLevel(Level.OFF);
        }

        for (Handler handler : logger.getHandlers()) {
            handler.setFormatter(new CustomFormatter());
            logger.addHandler(handler);
        }

        CustomFormatter formatter = new CustomFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(formatter);

        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

//        StoreLogger.logFinest("LOGGER LEVEL FINEST");
//        StoreLogger.logConfig("LOGGER LEVEL CONFIG");
//        StoreLogger.logInfo("LOGGER LEVEL INFO");
//        StoreLogger.logSevere("LOGGER LEVEL SEVERE");
    }

    private static class CustomFormatter extends Formatter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd yyyy, h:mm:ss a");

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            //builder.append(dateFormat.format(new Date(record.getMillis()))).append(" ");
            //builder.append(record.getLevel()).append(": ");
            builder.append(record.getMessage()).append("\n");
            return builder.toString();
        }
    }


    public static void logSuccess(String text){
        //green
        logger.log(Level.INFO,  "\u001B[32m" + text + "\u001B[0m");
    }

    public static void logConfig(String text){
        //blue
        logger.log(Level.INFO,  "\u001B[34m" + text + "\u001B[0m");
    }

    public static void logStatus(String text){
        //white
        logger.log(Level.INFO,  "\u001B[37m" + text + "\u001B[0m");
    }

    public static void logError(String text){
        //red
        logger.log(Level.INFO,  "\u001B[31m" + text + "\u001B[0m");
    }

    public static void logUpdate(String text){
        //yellow
        logger.log(Level.INFO, "\u001B[33m" + text + "\u001B[0m");
    }


    public static void logFinestOld(String text){
        //white color
        logger.log(Level.FINEST,  "\u001B[37m" + text + "\u001B[0m");
    }

    public static void logConfigOld(String text){
        //green color
        //logger.log(Level.CONFIG,  "\u001B[32m" + text + "\u001B[0m");

        //blue color
        logger.log(Level.INFO,  "\u001B[34m" + text + "\u001B[0m");
    }

    public static void logInfoOld(String text){
        //orange
        logger.log(Level.CONFIG, "\u001B[33m" + text + "\u001B[0m");

    }

    public static void logSevereOld(String text){
        //red color
        logger.log(Level.SEVERE,  "\u001B[31m" + text + "\u001B[0m");
    }


}

