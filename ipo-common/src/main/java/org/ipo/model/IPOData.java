package org.ipo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class IPOData {

    private String ipoStatus;
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

    // No-argument constructor
    public IPOData() {
    }

    // Constructor with all fields
    public IPOData(String ipoStatus, String ipoName, int price, int gmp, int estListing, double ipoSize, int lot,
            String openDt, String closeDt, String boaDate, String listingDate) {
        this.ipoStatus = ipoStatus;
        this.ipoName = ipoName;
        this.price = price;
        this.gmp = gmp;
        this.estListing = estListing;
        this.ipoSize = ipoSize;
        this.lot = lot;
        this.openDt = openDt;
        this.closeDt = closeDt;
        this.boaDate = boaDate;
        this.listingDate = listingDate;
    }

    // Getter and Setter methods

    @DynamoDbAttribute("ipoStatus")
    public String getIpoStatus() {
        return ipoStatus;
    }

    public void setIpoStatus(String ipoStatus) {
        this.ipoStatus = ipoStatus;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("ipoName")
    public String getIpoName() {
        return ipoName;
    }

    public void setIpoName(String ipoName) {
        this.ipoName = ipoName;
    }

    @DynamoDbAttribute("price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @DynamoDbAttribute("gmp")
    public int getGmp() {
        return gmp;
    }

    public void setGmp(int gmp) {
        this.gmp = gmp;
    }

    @DynamoDbAttribute("estListing")
    public int getEstListing() {
        return estListing;
    }

    public void setEstListing(int estListing) {
        this.estListing = estListing;
    }

    @DynamoDbAttribute("ipoSize")
    public double getIpoSize() {
        return ipoSize;
    }

    public void setIpoSize(double ipoSize) {
        this.ipoSize = ipoSize;
    }

    @DynamoDbAttribute("lot")
    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    @DynamoDbAttribute("openDt")
    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    @DynamoDbAttribute("closeDt")
    public String getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    @DynamoDbAttribute("boaDate")
    public String getBoaDate() {
        return boaDate;
    }

    public void setBoaDate(String boaDate) {
        this.boaDate = boaDate;
    }

    @DynamoDbAttribute("listingDate")
    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    // Builder class for constructing IPOData instances
    public static class Builder {
        private String ipoStatus;
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

        public Builder() {}

        public Builder ipoStatus(String ipoStatus) {
            this.ipoStatus = ipoStatus;
            return this;
        }

        public Builder ipoName(String ipoName) {
            this.ipoName = ipoName;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder gmp(int gmp) {
            this.gmp = gmp;
            return this;
        }

        public Builder estListing(int estListing) {
            this.estListing = estListing;
            return this;
        }

        public Builder ipoSize(double ipoSize) {
            this.ipoSize = ipoSize;
            return this;
        }

        public Builder lot(int lot) {
            this.lot = lot;
            return this;
        }

        public Builder openDt(String openDt) {
            this.openDt = openDt;
            return this;
        }

        public Builder closeDt(String closeDt) {
            this.closeDt = closeDt;
            return this;
        }

        public Builder boaDate(String boaDate) {
            this.boaDate = boaDate;
            return this;
        }

        public Builder listingDate(String listingDate) {
            this.listingDate = listingDate;
            return this;
        }

        public IPOData build() {
            return new IPOData(ipoStatus, ipoName, price, gmp, estListing, ipoSize, lot, openDt, closeDt, boaDate, listingDate);
        }
    }
}

