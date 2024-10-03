package org.ipo.web.scrapper.util;

import org.ipo.web.constant.IPOStatus;

public class LambdaEnv {
    private static final String websiteCurrentUrl ;
    private static final String websiteClosedUrl;
    private static final String websiteListedUrl;
    private static final String websiteUpcomingUrl;

    static {
        websiteCurrentUrl = System.getenv("CURRENT");
        websiteClosedUrl = System.getenv("CLOSED");
        websiteListedUrl = System.getenv("LISTED");
        websiteUpcomingUrl = System.getenv("UPCOMING");
    }


    public static String getURL(IPOStatus ipoStatus) {
        switch (ipoStatus) {
            case CURRENT -> {
                return websiteCurrentUrl;
            }
            case CLOSED -> {
                return websiteClosedUrl;
            }
            case LISTED -> {
                return websiteListedUrl;
            }
            case UPCOMING -> {
                return websiteUpcomingUrl;
            }
            default -> {
                return "Wrong IPO Status";
            }
        }
    }
}
