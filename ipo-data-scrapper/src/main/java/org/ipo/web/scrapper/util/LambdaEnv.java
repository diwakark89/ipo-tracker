package org.ipo.web.scrapper.util;

import org.ipo.web.constant.IPOStatus;

public final class LambdaEnv {
    private static final String WEBSITE_CURRENT_URL;
    private static final String WEBSITE_CLOSED_URL;
    private static final String WEBSITE_LISTED_URL;
    private static final String WEBSITE_UPCOMING_URL;
    private LambdaEnv(){}

    static {
        WEBSITE_CURRENT_URL = System.getenv("CURRENT");
        WEBSITE_CLOSED_URL = System.getenv("CLOSED");
        WEBSITE_LISTED_URL = System.getenv("LISTED");
        WEBSITE_UPCOMING_URL = System.getenv("UPCOMING");
    }


    public static String getURL(IPOStatus ipoStatus) {
        switch (ipoStatus) {
            case CURRENT -> {
                return WEBSITE_CURRENT_URL;
            }
            case CLOSED -> {
                return WEBSITE_CLOSED_URL;
            }
            case LISTED -> {
                return WEBSITE_LISTED_URL;
            }
            case UPCOMING -> {
                return WEBSITE_UPCOMING_URL;
            }
            default -> {
                return "Wrong IPO Status";
            }
        }
    }
}
