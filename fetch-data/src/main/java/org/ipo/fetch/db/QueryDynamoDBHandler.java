package org.ipo.fetch.db;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ipo.fetch.model.InputModel;

public class QueryDynamoDBHandler implements RequestHandler<Object, Object> {

    private final DynamoDB dynamoDB;
    private final String tableName = System.getenv("TABLE_NAME");

    public QueryDynamoDBHandler() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    @Override
    public Object handleRequest(Object input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Received input: " + input);

        if (input instanceof APIGatewayProxyRequestEvent requestEvent) {
            return handleApiGatewayRequest(requestEvent, context);
        } else {
            return handleDirectInvocation(input, context);
        }
    }

    private APIGatewayProxyResponseEvent handleApiGatewayRequest(APIGatewayProxyRequestEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String body = event.getBody();
            logger.log("Request body: " + body);

            InputModel input = objectMapper.readValue(body, InputModel.class);
            String key = input.getKey();

            if (key == null || key.isEmpty()) {
                return response.withStatusCode(400).withBody("Error: Missing key in input");
            }

            Table table = dynamoDB.getTable(tableName);

            GetItemSpec spec = new GetItemSpec().withPrimaryKey("key", key);
            logger.log("GetItemSpec: " + spec);

            Item item = table.getItem(spec);
            logger.log("Item: " + item);

            String responseBody = item != null ? item.toJSON() : "{}";
            return response.withStatusCode(200).withBody(responseBody);

        } catch (Exception e) {
            logger.log("Error querying DynamoDB: " + e.getMessage());
            return response.withStatusCode(500).withBody("Error querying DynamoDB");
        }
    }

    private String handleDirectInvocation(Object input, Context context) {
        LambdaLogger logger = context.getLogger();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            InputModel inputModel = objectMapper.convertValue(input, InputModel.class);
            String key = inputModel.getKey();

            if (key == null || key.isEmpty()) {
                return "Error: Missing key in input";
            }

            Table table = dynamoDB.getTable(tableName);

            GetItemSpec spec = new GetItemSpec().withPrimaryKey("key", key);
            logger.log("GetItemSpec: " + spec);

            Item item = table.getItem(spec);
            logger.log("Item: " + item);

            return item != null ? item.toJSON() : "{}";

        } catch (Exception e) {
            logger.log("Error querying DynamoDB: " + e.getMessage());
            throw new RuntimeException("Error querying DynamoDB", e);
        }
    }
}
