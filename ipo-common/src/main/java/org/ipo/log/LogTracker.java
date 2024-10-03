package org.ipo.log;

import org.ipo.log.model.Log;
import org.ipo.log.model.LogType;

import java.util.ArrayList;
import java.util.List;

public class LogTracker {
    private static final List<Log> logList;

    static {
        logList = new ArrayList<>(15);
    }


    public static void error(String message) {
        logList.add(new Log(message, LogType.ERROR));
    }

    public static void info(String message) {
        logList.add(new Log(message, LogType.INFO));
    }

    public static void warn(String message) {
        logList.add(new Log(message, LogType.WARN));
    }


    public static String getLogMessage() {
        StringBuilder builder = new StringBuilder();
        logList.forEach(log -> builder.append(log.logType().getLogType()).append(" : ").append(log.message()).append("\n"));
        return builder.toString();
    }

    public static void clearLog() {
        logList.clear();
    }


    public static String buildResponse() {
        String logs = getLogMessage();
        clearLog();
        return logs;
    }
}
