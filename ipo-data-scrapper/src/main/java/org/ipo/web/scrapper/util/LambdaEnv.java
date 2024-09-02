package org.ipo.web.scrapper.util;

import org.ipo.web.constant.IPOStatus;

public class LambdaEnv {
    private static String websiteCurrentUrl ;
    private static  String websiteCloseUrl ;
    private static  String websiteListedUrl;
    private static  String websiteUpcomingUrl;

    static {
        websiteCurrentUrl = System.getenv("DB_HOST");
        websiteCloseUrl = System.getenv("DB_PORT");
        websiteListedUrl = System.getenv("DB_USER");
        websiteUpcomingUrl = System.getenv("DB_PASSWORD");
    }


    public static String getURL(IPOStatus ipoStatus) {
        switch (ipoStatus) {
            case CURRENT -> {
                return websiteCurrentUrl;
            }
            case CLOSED -> {
                return websiteCloseUrl;
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
