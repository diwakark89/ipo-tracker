package org.ipo.log;

import org.ipo.log.model.Log;
import org.ipo.log.model.LogType;

import java.util.ArrayList;
import java.util.List;

public class LogTracker {
    private static List<Log> logList;

    public static void append(Log log){
        if(null == logList){
            logList=new ArrayList<>(15);
        }
        logList.add(log);
    }

    public static void append(String completedTheRequest) {
        logList.add(new Log(completedTheRequest));
    }

    public static void append(String message, LogType logType) {
        logList.add(new Log(message,logType));
    }

    public static String getLogMessage(){
        StringBuilder builder=new StringBuilder();
        logList.forEach(log -> builder.append(log.logType().getLogType()).append(" : ").append(log.message()));
        return builder.toString();
    }

    public static void clearLog(){
        logList.clear();
    }


    public static String buildResponse() {
        String logs= getLogMessage();
        clearLog();
        return logs;
    }
}
