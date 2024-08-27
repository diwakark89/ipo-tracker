package org.ipo.db;


import ipo.model.DBModel;

public class DBStorage {
    private final DynamoDB dynamoDB;
    public DBStorage() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    public void saveDataToDB(DBModel dbData){

    }
}
