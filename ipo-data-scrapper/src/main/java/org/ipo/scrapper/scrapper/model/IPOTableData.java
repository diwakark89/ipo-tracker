package org.ipo.scrapper.scrapper.model;

import java.util.Objects;

public class IPOTableData {
    private String ipoId;
    private String status;
    private String ipoName;
    private String price;
    private String gmp;
    private String estListing;
    private String ipoSize;
    private String lot;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGmp() {
        return gmp;
    }

    public void setGmp(String gmp) {
        this.gmp = gmp;
    }

    public String getEstListing() {
        return estListing;
    }

    public void setEstListing(String estListing) {
        this.estListing = estListing;
    }

    public String getIpoSize() {
        return ipoSize;
    }

    public void setIpoSize(String ipoSize) {
        this.ipoSize = ipoSize;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPOTableData data)) {
            return false;
        }
        return Objects.equals(getIpoId(), data.getIpoId()) && Objects.equals(getStatus(), data.getStatus()) && Objects.equals(getIpoName(), data.getIpoName()) && Objects.equals(getPrice(), data.getPrice()) && Objects.equals(getGmp(), data.getGmp()) && Objects.equals(getEstListing(), data.getEstListing()) && Objects.equals(getIpoSize(), data.getIpoSize()) && Objects.equals(getLot(), data.getLot()) && Objects.equals(getOpenDt(), data.getOpenDt()) && Objects.equals(getCloseDt(), data.getCloseDt()) && Objects.equals(getBoaDate(), data.getBoaDate()) && Objects.equals(getListingDate(), data.getListingDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIpoId(), getStatus(), getIpoName(), getPrice(), getGmp(), getEstListing(), getIpoSize(), getLot(), getOpenDt(), getCloseDt(), getBoaDate(), getListingDate());
    }

    @Override
    public String toString() {
        return "IPOTableData{" + "ipoId='" + ipoId + '\'' + ", status='" + status + '\'' + ", ipoName='" + ipoName + '\'' + ", price='" + price + '\'' + ", gmp='" + gmp + '\'' + ", estListing='" + estListing + '\'' + ", ipoSize='" + ipoSize + '\'' + ", lot='" + lot + '\'' + ", openDt='" + openDt + '\'' + ", closeDt='" + closeDt + '\'' + ", boaDate='" + boaDate + '\'' + ", listingDate='" + listingDate + '\'' + '}';
    }
}
