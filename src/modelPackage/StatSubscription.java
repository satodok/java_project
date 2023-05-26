package modelPackage;

import java.math.BigDecimal;

public class StatSubscription {
    private Integer totalSub;
    private Integer totalRevenue;
    private BigDecimal goldPercentage;
    private BigDecimal silverPercentage;
    private BigDecimal bronzePercentage;


    public StatSubscription(Integer totalSub, Integer totalRevenue, BigDecimal goldPercentage, BigDecimal silverPercentage, BigDecimal bronzePercentage){
        setTotalSub(totalSub);
        setTotalRevenue(totalRevenue);
        setGoldPercentage(goldPercentage);
        setSilverPercentage(silverPercentage);
        setBronzePercentage(bronzePercentage);
    }

    public Integer getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(Integer totalSub) {
        if (totalSub > 0)
            this.totalSub = totalSub;
    }

    public Integer getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Integer totalRevenue) {
        if (totalRevenue > 0)
            this.totalRevenue = totalRevenue;
    }

    public BigDecimal getGoldPercentage() {
        return goldPercentage;
    }

    public void setGoldPercentage(BigDecimal goldPercentage) {
        if (goldPercentage.compareTo(BigDecimal.ZERO) > 0)
            this.goldPercentage = goldPercentage;
    }

    public BigDecimal getSilverPercentage() {
        return silverPercentage;
    }

    public void setSilverPercentage(BigDecimal silverPercentage) {
        if (silverPercentage.compareTo(BigDecimal.ZERO) > 0)
            this.silverPercentage = silverPercentage;
    }

    public BigDecimal getBronzePercentage() {
        return bronzePercentage;
    }

    public void setBronzePercentage(BigDecimal bronzePercentage) {
        if (bronzePercentage.compareTo(BigDecimal.ZERO) > 0)
            this.bronzePercentage = bronzePercentage;
    }
}
