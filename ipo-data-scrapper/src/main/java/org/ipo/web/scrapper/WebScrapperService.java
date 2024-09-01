package org.ipo.web.scrapper;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.ipo.model.IPOData;
import org.ipo.web.constant.IPOStatus;
import org.ipo.web.db.DBStorage;
import org.ipo.web.db.DataTransformer;
import org.ipo.web.scrapper.model.IPOTableData;
import org.ipo.web.scrapper.util.DataCleaner;
import org.ipo.web.scrapper.util.FilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class WebScrapperService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(WebScrapperService.class);

    private final WebScrapper scrapper;
    private final DBStorage dbStorage;
    private final DataTransformer transformer;

    private static final String WEBSITE_CURRENT_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/current/";
    private static final String WEBSITE_CLOSE_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_LISTED_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_UPCOMING_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";


    public WebScrapperService() {
        dbStorage = new DBStorage();
        DataCleaner dataCleaner = new DataCleaner();
        FilterData filterData = new FilterData();
        transformer = new DataTransformer();
        scrapper = new WebScrapperImpl(dataCleaner, filterData);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = requestEvent.getHeaders();

        String status = headers.getOrDefault("STATUS", "Current");
        String message;
        try {
            scrapData(status);
            message = "Successful";
        } catch (Exception ex) {
            message = ex.getMessage();
        }

        return responseEvent.withStatusCode(200).withBody(message);
    }


    public void scrapData(String ipoStatus) {
        try{
            IPOStatus status = IPOStatus.fromString(ipoStatus);

            List<IPOTableData> ipoList = scrapper.tableScrap(getURL(status), status.name());

            LOG.info("Data found from scrap: {}", ipoList.size());
            List<IPOData> ipoDataList = transformer.convertScrapToDBData(ipoList);
            LOG.info("Data Transformed ");
            dbStorage.saveDataToDB(ipoDataList);

            LOG.info("All Data Stored ");
        }catch (Exception ex){
            LOG.error("Exception occurred : {}",ex.getMessage(),ex);
        }

    }

    private String getURL(IPOStatus ipoStatus) {
        switch (ipoStatus) {
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
