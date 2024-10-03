package org.ipo.log.model;

public record Log(String message, LogType logType) {
    public Log(String message){
        this(message,LogType.INFO);
    }
}
