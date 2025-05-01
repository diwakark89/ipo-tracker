package org.ipo.fetch.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ipo.db.DynamoDBFactory;
import org.ipo.fetch.repository.IPORepository;
import org.ipo.model.IPOData;

import java.util.List;

public class IPOLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public static final String STATUS = "ipoStatus";
    private final IPORepository repository;

    public IPOLambdaHandler(){
        repository = new IPORepository();
        DynamoDBFactory.initClient();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent=new APIGatewayProxyResponseEvent();
        String status = requestEvent.getQueryStringParameters().get(STATUS);
        List<IPOData> dataByStatus = repository.getDataByStatus(status);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(dataByStatus);
        } catch (Exception e) {
            jsonResponse = "{\"error\": \"Failed to convert products to JSON\"}";
        }
        return responseEvent.withStatusCode(200).withBody(jsonResponse);
    }
}
