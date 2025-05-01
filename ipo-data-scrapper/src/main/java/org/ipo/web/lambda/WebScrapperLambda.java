package org.ipo.web.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.ipo.db.DataTransformer;
import org.ipo.log.LogTracker;
import org.ipo.model.IPOData;
import org.ipo.model.IPOTableData;
import org.ipo.web.constant.IPOStatus;
import org.ipo.web.db.DBStorage;
import org.ipo.web.model.DataStore;
import org.ipo.web.scrapper.WebScrapper;
import org.ipo.web.scrapper.WebScrapperImpl;
import org.ipo.web.scrapper.util.DataCleaner;
import org.ipo.web.scrapper.util.FilterData;
import org.ipo.web.scrapper.util.LambdaEnv;
import org.ipo.web.service.YAMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebScrapperLambda implements RequestHandler<Map<String, String>, String> {

    private static final Logger LOG = LoggerFactory.getLogger(WebScrapperLambda.class);

    private final WebScrapper scrapper;
    private final DBStorage dbStorage;
    private final DataTransformer transformer;
    private final YAMLWriter yamlWriter;
    private static final String FILE_NAME = "IPO_Data.yml";

    public WebScrapperLambda() {
        this.yamlWriter = new YAMLWriter();
        dbStorage = new DBStorage();
        DataCleaner dataCleaner = new DataCleaner();
        FilterData filterData = new FilterData();
        transformer = new DataTransformer();
        scrapper = new WebScrapperImpl(dataCleaner, filterData);
    }

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        try {

            String status = event.getOrDefault("STATUS", "Current");
            LogTracker.info("Data scrapping started for: " + status);

            scrapData(status);
            LogTracker.info("Completed the request");
        } catch (Exception ex) {
            String message = String.format("Exception occurred %s", ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }

        return LogTracker.buildResponse();
    }


    public void scrapData(String ipoStatus) {
        try {
            IPOStatus status = IPOStatus.fromString(ipoStatus);

            List<IPOTableData> ipoList = scrapper.tableScrap(LambdaEnv.getURL(status), status.name());

            LOG.info("Data found from scrap: {}", ipoList.size());
            DataStore dataStore = yamlWriter.readYaml(ipoStatus, FILE_NAME);

            List<IPOData> ipoDataList = trimList(dataStore, transformer.convertScrapToDBData(ipoList));
            LOG.info("Status: {}, Data count: {}, ", ipoStatus, ipoDataList.size());
            dataStore.ipoData().addAll(ipoDataList);
            yamlWriter.writeToYaml(ipoStatus,FILE_NAME, dataStore);

            dbStorage.saveDataToDB(ipoDataList);

        } catch (Exception ex) {
            String message = String.format("Exception occurred %s", ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }

    }

    public List<IPOData> trimList(DataStore dataStore, List<IPOData> ipoDataList){
        if(dataStore.ipoData().isEmpty()){
            return ipoDataList;
        }
        Set<IPOData> ipoDataSet = dataStore.ipoData();
        return ipoDataList.parallelStream().filter(ipoData -> !ipoDataSet.contains(ipoData)).toList();
    }


}
