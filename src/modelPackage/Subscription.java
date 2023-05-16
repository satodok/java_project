package modelPackage;

import java.util.Date;

public class Subscription {
    private String subscriptionID;
    private int price;
    private double discount;
    private Date startDate;
    private Date endDate;
    private boolean automaticRenewal;
    private boolean pricePayed;
    private boolean cautionPayed;
    private String typeName;
    private int clientNumber;

    public Subscription(){}
    public Subscription(int price, double discount,
                        Date startDate, Date endDate, boolean automaticRenewal,
                        boolean pricePayed, boolean cautionPayed, String typeName, int clientNumber){
        this.price = price;
        this.discount = discount;
        this.startDate =startDate;
        this.endDate = endDate;
        this.automaticRenewal = automaticRenewal;
        this.pricePayed = pricePayed;
        this.cautionPayed = cautionPayed;
        this.typeName = typeName;
        this.clientNumber = clientNumber;
    }

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setAutomaticRenewal(boolean automaticRenewal) {
        this.automaticRenewal = automaticRenewal;
    }

    public void setPricePayed(boolean pricePayed) {
        this.pricePayed = pricePayed;
    }

    public void setCautionPayed(boolean cautionPayed) {
        this.cautionPayed = cautionPayed;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getSubscriptionID() {
        return subscriptionID;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getClientNumber() {
        return clientNumber;
    }
    public boolean getAutomaticRenewal(){
        return automaticRenewal;
    }
    public boolean getPricePayed(){
        return pricePayed;
    }
    public boolean getCautionPayed(){
        return cautionPayed;
    }
}
