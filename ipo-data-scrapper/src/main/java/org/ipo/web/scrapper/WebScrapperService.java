package org.ipo.web.scrapper;

import org.ipo.web.constant.IPOStatus;
import org.ipo.web.scrapper.model.IPOTableData;
import org.ipo.web.scrapper.util.DataCleaner;
import org.ipo.web.scrapper.util.FilterData;

import java.util.List;

public class WebScrapperService {

    private final WebScrapper scrapper;

    private static final String WEBSITE_CURRENT_URL="https://www.investorgain.com/report/live-ipo-gmp/331/current/";
    private static final String WEBSITE_CLOSE_URL="https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_LISTED_URL="https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_UPCOMING_URL="https://www.investorgain.com/report/live-ipo-gmp/331/close/";


    public WebScrapperService(){
        DataCleaner dataCleaner=new DataCleaner();
        FilterData filterData=new FilterData();
        scrapper=new WebScrapperImpl(dataCleaner, filterData);
    }

    public static void main(String[]args){
        WebScrapperService webScrapperService=new WebScrapperService();
    }


    public void scrapData(String ipoStatus){
        IPOStatus status= IPOStatus.fromString(ipoStatus);

        List<IPOTableData> ipoList = scrapper.tableScrap(getURL(status), status.name());

    }

    private String getURL(IPOStatus ipoStatus){
        switch (ipoStatus){
            case CURRENT -> {
                return WEBSITE_CURRENT_URL;
            }
            case CLOSED -> {
                return WEBSITE_CLOSE_URL;
            }
            case LISTED -> {
                return WEBSITE_LISTED_URL;
            }
            case UPCOMING -> {
                return WEBSITE_UPCOMING_URL;
            }
            default -> {
                return "Wrong";
            }
        }
    }

}
