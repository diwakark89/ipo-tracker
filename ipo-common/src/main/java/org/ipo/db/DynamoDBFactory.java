package org.ipo.db;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBFactory {
    private static DynamoDbClient dynamoDbClient;
    private static DynamoDbEnhancedClient enhancedClient;

    private DynamoDBFactory(){}

    public static void initClient(){
        Region region = Region.AP_SOUTH_1;
        dynamoDbClient = DynamoDbClient.builder()
                .region(region)
                .build();
    }
    public static void initEnhancedClient() {
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    public static DynamoDbClient getDynamoDbClient(){
        if(null == dynamoDbClient){
            initClient();
        }
        return dynamoDbClient;
    }

    public static DynamoDbEnhancedClient getEnhancedClient(){
        if(null == enhancedClient){
            initEnhancedClient();
        }
        return enhancedClient;
    }

}
