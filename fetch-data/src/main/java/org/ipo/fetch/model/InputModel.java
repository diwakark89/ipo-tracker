package org.ipo.fetch.model;

public class InputModel {
    private String partitionKey;
    private String sortValue;

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    @Override
    public String toString() {
        return "InputModel{key='" + partitionKey + '\'' + '}';
    }
}