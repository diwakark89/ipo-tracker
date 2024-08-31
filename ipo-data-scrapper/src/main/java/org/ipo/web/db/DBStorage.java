package org.ipo.web.db;

import org.ipo.db.DynamoDBFactory;
import org.ipo.model.IPOData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;

public class DBStorage {
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<IPOData> ipoData;

    public DBStorage() {
        enhancedClient = DynamoDBFactory.getEnhancedClient();
        ipoData = enhancedClient.table("IPOData", TableSchema.fromBean(IPOData.class));
    }

    public void saveDataToDB(IPOData dbData) throws DynamoDbException {
        try {
            ipoData.putItem(dbData);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Exception occurred while saving data "+e.getMessage());
        }
    }

    public void saveDataToDB(List<IPOData> dbDataList) throws DynamoDbException {
        try {
            dbDataList.forEach(this::saveDataToDB);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Exception occurred while saving data "+e.getMessage());
        }
    }
}
