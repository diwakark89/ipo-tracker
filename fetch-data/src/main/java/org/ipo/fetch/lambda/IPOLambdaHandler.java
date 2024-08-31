package org.ipo.fetch.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.ipo.db.DynamoDBFactory;
import org.ipo.fetch.repository.IPORepository;

public class IPOLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final IPORepository IPORepository;
    public IPOLambdaHandler(){
        this.IPORepository = new IPORepository();
        DynamoDBFactory.initClient();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        return IPORepository.getData(requestEvent,context);
    }
}
