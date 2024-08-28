package org.ipo.fetch.db;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ipo.fetch.model.InputModel;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryDynamoDBHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = System.getenv("TABLE_NAME");

    public QueryDynamoDBHandler() {
        Region region = Region.AP_SOUTH_1;
        dynamoDbClient = DynamoDbClient.builder()
                .region(region)
                .build();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Received event: " + event);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String body = event.getBody();
            logger.log("Request body: " + body);

            InputModel input = objectMapper.readValue(body, InputModel.class);
            String keyString = input.getKey();

            if (keyString == null || keyString.isEmpty()) {
                return response.withStatusCode(400).withBody("Error: Missing key in input");
            }

            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(keyString, AttributeValue.builder()
                    .s(input.getValue())
                    .build());

            GetItemRequest request = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();
            Map<String, AttributeValue> returnedItem =null;
            try {
                // If there is no matching item, GetItem does not return any data.
                returnedItem = dynamoDbClient.getItem(request).item();
                if (returnedItem.isEmpty())
                    System.out.format("No item found with the key %s!\n", keyString);
                else {
                    Set<String> keys = returnedItem.keySet();
                    System.out.println("Amazon DynamoDB table attributes: \n");
                    for (String key1 : keys) {
                        System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
                    }
                }

            } catch (DynamoDbException e) {
                logger.log(e.getMessage());
                System.exit(1);
            }

            String responseBody = objectMapper.writeValueAsString(returnedItem);
            return response.withStatusCode(200).withBody(responseBody);

        } catch (Exception e) {
            logger.log("Error querying DynamoDB: " + e.getMessage());
            return response.withStatusCode(500).withBody("Error querying DynamoDB");
        }
    }
}
