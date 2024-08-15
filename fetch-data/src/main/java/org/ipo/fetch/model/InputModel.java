package org.ipo.fetch.model;

public class InputModel {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "InputModel{key='" + key + '\'' + '}';
    }
}