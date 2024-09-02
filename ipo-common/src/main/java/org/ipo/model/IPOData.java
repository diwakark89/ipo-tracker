package org.ipo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Objects;

@DynamoDbBean
public class IPOData {

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

    @DynamoDbPartitionKey
    @DynamoDbAttribute("ipoName")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IPOData{" + "status='" + status + '\'' + ", ipoName='" + ipoName + '\'' + ", price=" + price + ", gmp=" + gmp + ", estListing=" + estListing + ", ipoSize=" + ipoSize + ", lot=" + lot + ", openDt='" + openDt + '\'' + ", closeDt='" + closeDt + '\'' + ", boaDate='" + boaDate + '\'' + ", listingDate='" + listingDate + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPOData ipoData)) {
            return false;
        }
        return getPrice() == ipoData.getPrice() && getGmp() == ipoData.getGmp() && getEstListing() == ipoData.getEstListing() && Double.compare(getIpoSize(), ipoData.getIpoSize()) == 0 && getLot() == ipoData.getLot() && Objects.equals(getStatus(), ipoData.getStatus()) && Objects.equals(getIpoName(), ipoData.getIpoName()) && Objects.equals(getOpenDt(), ipoData.getOpenDt()) && Objects.equals(getCloseDt(), ipoData.getCloseDt()) && Objects.equals(getBoaDate(), ipoData.getBoaDate()) && Objects.equals(getListingDate(), ipoData.getListingDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getIpoName(), getPrice(), getGmp(), getEstListing(), getIpoSize(), getLot(), getOpenDt(), getCloseDt(), getBoaDate(), getListingDate());
    }
}
