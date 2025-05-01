package org.ipo.fetch.repository;

import org.ipo.db.DataTransformer;
import org.ipo.db.DynamoDBFactory;
import org.ipo.model.IPOData;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ipo.db.DBConstant.*;


public class IPORepository {

    private final DynamoDbClient dynamoDbClient;
    private final DataTransformer transformer;


    public IPORepository() {
        dynamoDbClient = DynamoDBFactory.getDynamoDbClient();
        transformer = new DataTransformer();
    }


    public List<IPOData> getDataByStatus(String status){


        // Build DynamoDB Query Request
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":ipoStatus", AttributeValue.builder().s(status).build());

        QueryRequest queryRequest = QueryRequest.builder()
                .tableName(IPO_DATA_TABLE)
                .indexName(IPO_GSI_INDEX)
                .keyConditionExpression(STATUS+" = :ipoStatus")
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        // Perform the Query
        QueryResponse queryResponse = dynamoDbClient.query(queryRequest);

        return transformer.dbResponseTOBean(queryResponse);
    }

}
