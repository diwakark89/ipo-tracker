package org.ipo.web.scrapper;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
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

import java.util.List;
import java.util.Map;

public class WebScrapperService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final WebScrapper scrapper;
    private final DBStorage dbStorage;
    private final DataTransformer transformer;

    private static final String WEBSITE_CURRENT_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/current/";
    private static final String WEBSITE_CLOSE_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_LISTED_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final String WEBSITE_UPCOMING_URL = "https://www.investorgain.com/report/live-ipo-gmp/331/close/";


    public WebScrapperService() {
        this.dbStorage = new DBStorage();
        DataCleaner dataCleaner = new DataCleaner();
        FilterData filterData = new FilterData();
        this.transformer = new DataTransformer();
        scrapper = new WebScrapperImpl(dataCleaner, filterData);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = requestEvent.getHeaders();

        String status = headers.getOrDefault("STATUS", "Current");
        String message;
        try {
            scrapData(status, context);
            message = "Successful";
        } catch (Exception ex) {
            message = ex.getMessage();
        }

        return responseEvent.withStatusCode(200).withBody(message);
    }


    public void scrapData(String ipoStatus, Context context) {
        IPOStatus status = IPOStatus.fromString(ipoStatus);
        LambdaLogger logger = context.getLogger();

        List<IPOTableData> ipoList = scrapper.tableScrap(getURL(status), status.name());
        logger.log("Data found: " + ipoList.size());
        List<IPOData> ipoData = transformer.convertScrapToDBData(ipoList);
        logger.log("Data Transformed ");
        dbStorage.saveDataToDB(ipoData);
        logger.log("Data Stored ");
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

    public static void main(String[] args) {
        WebScrapperService webScrapperService = new WebScrapperService();
    }


}
