package edu.ucla.cs.cs144;

import java.util.List;
import java.util.Map;

public class XMLBean implements java.io.Serializable {
    private String itemID;
    private String name;
    private String currently;
    private String buyPrice;
    private String firstBid;
    private String numOfBids;
    private String location;
    private String country;
    private String started;
    private String ends;
    private String description;
    private String latitude;
    private String longitude;
    private String sellerRating;
    private String sellerID;
    private List<String> categories;
    private List<Map<String, String>> bids;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(String firstBid) {
        this.firstBid = firstBid;
    }

    public String getNumOfBids() {
        return numOfBids;
    }

    public void setNumOfBids(String numOfBids) {
        this.numOfBids = numOfBids;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrently() {
        return currently;
    }

    public void setCurrently(String currently) {
        this.currently = currently;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(String sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public List<Map<String, String>> getBids() {
        return bids;
    }

    public void setBids(List<Map<String, String>> bids) {
        this.bids = bids;
    }
}
