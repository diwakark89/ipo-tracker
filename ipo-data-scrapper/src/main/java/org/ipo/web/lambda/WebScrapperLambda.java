package org.ipo.web.scrapper;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.ipo.log.LogTracker;
import org.ipo.model.IPOData;
import org.ipo.web.constant.IPOStatus;
import org.ipo.web.db.DBStorage;
import org.ipo.db.DataTransformer;
import org.ipo.model.IPOTableData;
import org.ipo.web.scrapper.util.DataCleaner;
import org.ipo.web.scrapper.util.FilterData;
import org.ipo.web.scrapper.util.LambdaEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.ipo.db.DBConstant.STATUS;

public class WebScrapperService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(WebScrapperService.class);

    private final WebScrapper scrapper;
    private final DBStorage dbStorage;
    private final DataTransformer transformer;


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

        try {

            String status = requestEvent.getQueryStringParameters().get(STATUS);
            LogTracker.info("Data scrapping started for: " + status);

            scrapData(status);
            LogTracker.info("Completed the request");
        }
        catch (Exception ex) {
            String message = String.format("Exception occurred %s", ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }

        return responseEvent.withStatusCode(200).withBody(LogTracker.buildResponse());
    }


    public void scrapData(String ipoStatus) {
        try {
            IPOStatus status = IPOStatus.fromString(ipoStatus);

            List<IPOTableData> ipoList = scrapper.tableScrap(LambdaEnv.getURL(status), status.name());

            LOG.info("Data found from scrap: {}", ipoList.size());
            List<IPOData> ipoDataList = transformer.convertScrapToDBData(ipoList);
            LOG.info("Data Transformed ");
            dbStorage.saveDataToDB(ipoDataList);

            LOG.info("All Data Stored ");
        }
        catch (Exception ex) {
            String message = String.format("Exception occurred %s", ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }

    }


}
