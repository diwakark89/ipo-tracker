package org.ipo.web.constant;

import java.util.Arrays;
import java.util.List;

public enum IPOStatus {
    CURRENT("current"),
    UPCOMING("upcoming"),
    CLOSED("closed"),
    LISTED("listed");

    final String status;

    IPOStatus(String status){
        this.status=status;
    }

    public List<IPOStatus> getIPOStatus(){
        return Arrays.asList(IPOStatus.values());
    }

    // Static method to get enum by string value
    public static IPOStatus fromString(String status) {
        for (IPOStatus ipoStatus : IPOStatus.values()) {
            if (ipoStatus.status.equalsIgnoreCase(status)) {
                return ipoStatus;
            }
        }
        // If not found, throw an IllegalArgumentException or return null
        throw new IllegalArgumentException("No enum constant with status: " + status);
    }

}
