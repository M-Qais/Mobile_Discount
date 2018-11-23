package hamza.m.Model;

public class ListProductData {

    private String pName;
    private String pType;
    private String pDesc;
    private String pPrice;
    private String pdiscount;
    private String pImage;
    private String pShop;

    public ListProductData() {
    }

    public ListProductData(String pName, String pType, String pDesc, String pPrice, String pdiscount, String product_image,String Shop) {
        this.pName = pName;
        this.pType = pType;
        this.pDesc = pDesc;
        this.pPrice = pPrice;
        this.pdiscount = pdiscount;
        this.pImage = product_image;
        this.pShop = Shop;
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

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpShop() {
        return pShop;
    }

    public void setpShop(String pShop) {
        this.pShop = pShop;
    }
}
