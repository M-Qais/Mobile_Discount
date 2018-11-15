package hamza.m.mobile_discount;

public class ShopkeeperData {
    private String pName;
    private String pType;
    private String pDesc;
    private String pPrice;
    private String pdiscount;

    public ShopkeeperData() {
    }

    public ShopkeeperData(String pName, String pType, String pDesc, String pPrice, String pdiscount) {
        this.pName = pName;
        this.pType = pType;
        this.pDesc = pDesc;
        this.pPrice = pPrice;
        this.pdiscount = pdiscount;
    }


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getPdiscount() {
        return pdiscount;
    }

    public void setPdiscount(String pdiscount) {
        this.pdiscount = pdiscount;
    }


}