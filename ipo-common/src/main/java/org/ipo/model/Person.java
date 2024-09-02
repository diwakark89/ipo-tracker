package org.ipo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public record Person(
        @DynamoDbPartitionKey
        String id,

        @DynamoDbSortKey
        String name,

        @DynamoDbAttribute("age")
        int age
) {
    // No need for additional methods; the record implicitly provides getters
}