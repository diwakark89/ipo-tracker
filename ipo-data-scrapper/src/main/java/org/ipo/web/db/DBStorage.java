package org.ipo.web.db;

import org.ipo.model.IPOData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DBStorage {
    private final DynamoDbEnhancedClient enhancedClient;

    public DBStorage() {
        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }

    public void saveDataToDB(IPOData dbData) {
        try {
            DynamoDbTable<IPOData> ipoData = enhancedClient.table("IPOData", TableSchema.fromBean(IPOData.class));
            ipoData.putItem(dbData);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
