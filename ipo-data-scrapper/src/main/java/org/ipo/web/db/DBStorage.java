package org.ipo.web.db;

import org.ipo.db.DynamoDBFactory;
import org.ipo.log.LogTracker;
import org.ipo.model.IPOData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;

public class DBStorage {
    private static final Logger LOG = LoggerFactory.getLogger(DBStorage.class);
    public static final String IPO_DATA = "IPOData";

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<IPOData> ipoData;

    public DBStorage() {
        enhancedClient = DynamoDBFactory.getEnhancedClient();
        ipoData = enhancedClient.table(IPO_DATA, TableSchema.fromBean(IPOData.class));
    }

    public void saveDataToDB(IPOData dbData) {
        try {

            LOG.info("Data being stored: {} ", dbData);
            ipoData.putItem(dbData);
        } catch (DynamoDbException ex) {
            String message = String.format("Exception occurred while storing data: %s due to: %s", dbData, ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }
    }

    public void saveDataToDB(List<IPOData> dbDataList) {
        dbDataList.forEach(this::saveDataToDB);
    }
}
