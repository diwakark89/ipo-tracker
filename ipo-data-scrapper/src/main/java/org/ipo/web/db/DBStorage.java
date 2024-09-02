package org.ipo.web.db;

import org.ipo.db.DynamoDBFactory;
import org.ipo.log.LogTracker;
import org.ipo.log.model.Log;
import org.ipo.log.model.LogType;
import org.ipo.model.IPOData;
import org.ipo.web.scrapper.WebScrapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;

public class DBStorage {
    private static final Logger LOG = LoggerFactory.getLogger(DBStorage.class);

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<IPOData> ipoData;

    public DBStorage() {
        enhancedClient = DynamoDBFactory.getEnhancedClient();
        ipoData = enhancedClient.table("IPOData", TableSchema.fromBean(IPOData.class));
    }

    public void saveDataToDB(IPOData dbData) {
        try {

            LOG.info("Data being stored: {} ", dbData);
            ipoData.putItem(dbData);
        } catch (DynamoDbException e) {
            String message=String.format("Exception occurred while storing data: %s due to: %s",dbData, e.getMessage());
            LogTracker.append(new Log(message, LogType.ERROR));
            LOG.error(message, e);
            throw new RuntimeException("Exception occurred while saving data " + e.getMessage());
        }
    }

    public void saveDataToDB(List<IPOData> dbDataList) {
        dbDataList.forEach(this::saveDataToDB);
    }
}
