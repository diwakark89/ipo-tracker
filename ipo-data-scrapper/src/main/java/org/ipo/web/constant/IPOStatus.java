package org.ipo.web.constant;

import java.util.Arrays;
import java.util.List;

public enum IPOStatus {
    CURRENT("Current"),
    UPCOMING("Upcoming"),
    CLOSED("Closed"),
    LISTED("Listed");

    String status;

    IPOStatus(String status){
        this.status=status;
    }

    public List<IPOStatus> getIPOStatus(){
        return Arrays.asList(IPOStatus.values());
    }
}
