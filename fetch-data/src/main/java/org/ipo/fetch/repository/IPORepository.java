package org.ipo.fetch.repository;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ipo.db.DynamoDBFactory;
import org.ipo.fetch.model.InputModel;
import org.ipo.model.IPOData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class IPORepository {

    private final DynamoDbEnhancedClient dynamoDbClient;

    private final DynamoDbTable<IPOData> ipoDataTable;

    public IPORepository() {
        dynamoDbClient = DynamoDBFactory.getEnhancedClient();
        ipoDataTable = dynamoDbClient.table("IPOData", TableSchema.fromBean(IPOData.class));
    }

    public APIGatewayProxyResponseEvent getData(APIGatewayProxyRequestEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String body = event.getBody();
            logger.log("Request body: " + body);

            InputModel input = objectMapper.readValue(body, InputModel.class);
            String keyString = input.getPartitionKey();

            if (keyString == null || keyString.isEmpty()) {
                return response.withStatusCode(400).withBody("Error: Missing key in input");
            }

            IPOData item;
            item = ipoDataTable.getItem(
                    Key.builder()
                            .partitionValue(input.getPartitionKey())
                            .sortValue(input.getSortValue())
                            .build());
            if (null == item) {
                logger.log("No item found with the key " + keyString);
                return response.withStatusCode(200).withBody("No data found");
            } else {
                String responseBody = objectMapper.writeValueAsString(item);
                return response.withStatusCode(200).withBody(responseBody);
            }

        } catch (Exception e) {
            logger.log("Error querying DynamoDB: " + e.getMessage());
            return response.withStatusCode(500).withBody("Error querying DynamoDB");
        }
    }
}
