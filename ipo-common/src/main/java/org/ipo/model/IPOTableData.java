package org.ipo.model;

import java.util.Objects;

public class IPOTableData {
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
    private String listedPrice;

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
        return !price.isEmpty() ? price:"0" ;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGmp() {
        return  !gmp.isEmpty() ? gmp:"0" ;
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

    public String getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(String listedPrice) {
        this.listedPrice = listedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPOTableData that = (IPOTableData) o;
        return Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getIpoName(), that.getIpoName()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getGmp(), that.getGmp()) && Objects.equals(getEstListing(), that.getEstListing()) && Objects.equals(getIpoSize(), that.getIpoSize()) && Objects.equals(getLot(), that.getLot()) && Objects.equals(getOpenDt(), that.getOpenDt()) && Objects.equals(getCloseDt(), that.getCloseDt()) && Objects.equals(getBoaDate(), that.getBoaDate()) && Objects.equals(getListingDate(), that.getListingDate()) && Objects.equals(getListedPrice(), that.getListedPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getIpoName(), getPrice(), getGmp(), getEstListing(), getIpoSize(), getLot(), getOpenDt(), getCloseDt(), getBoaDate(), getListingDate(), getListedPrice());
    }

    @Override
    public String toString() {
        return "IPOTableData{" +
                "boaDate='" + getBoaDate() + '\'' +
                ", closeDt='" + getCloseDt() + '\'' +
                ", estListing='" + getEstListing() + '\'' +
                ", gmp='" + getGmp() + '\'' +
                ", ipoName='" + getIpoName() + '\'' +
                ", ipoSize='" + getIpoSize() + '\'' +
                ", listedPrice='" + getListedPrice() + '\'' +
                ", listingDate='" + getListingDate() + '\'' +
                ", lot='" + getLot() + '\'' +
                ", openDt='" + getOpenDt() + '\'' +
                ", price='" + getPrice() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
