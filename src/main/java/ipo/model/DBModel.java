package ipo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class DBModel {
    private String ipoId;
    private String status;
    private String ipoName;
    private int price;
    private int gmp;
    private int estListing;
    private double ipoSize;
    private int lot;
    private String openDt;
    private String closeDt;
    private String boaDate;
    private String listingDate;

    public String getIpoId() {
        return ipoId;
    }

    public void setIpoId(String ipoId) {
        this.ipoId = ipoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpoName() {
        return ipoName;
    }

    public void setIpoName(String ipoName) {
        this.ipoName = ipoName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGmp() {
        return gmp;
    }

    public void setGmp(int gmp) {
        this.gmp = gmp;
    }

    public int getEstListing() {
        return estListing;
    }

    public void setEstListing(int estListing) {
        this.estListing = estListing;
    }

    public double getIpoSize() {
        return ipoSize;
    }

    public void setIpoSize(double ipoSize) {
        this.ipoSize = ipoSize;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    public String getBoaDate() {
        return boaDate;
    }

    public void setBoaDate(String boaDate) {
        this.boaDate = boaDate;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }
}
