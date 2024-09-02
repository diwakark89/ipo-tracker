package org.ipo.log.model;

public enum LogType {
    INFO("INFO"),
    ERROR("ERROR"),
    WARN("WARN");
    private final String logType;
    LogType(String logType) {

        this.logType = logType;
    }

    public String getLogType(){
        return this.logType;
    }
}
