package hamza.m.Model;

import android.graphics.Bitmap;

public class ShopkeeperData {
    private int id;
    private String pName;
    private String pType;
    private String pDesc;
    private String pPrice;
    private String pdiscount;
    private String pImage;
    private String pShop;
    private String lat;
    private String lng;

    public ShopkeeperData() {
    }

    public ShopkeeperData(String pName, String pType, String pDesc, String pPrice, String pdiscount,String pimage,String shop, String lat, String lng) {
        this.pName = pName;
        this.pType = pType;
        this.pDesc = pDesc;
        this.pPrice = pPrice;
        this.pdiscount = pdiscount;
        this.pImage = pimage;
        this.pImage = shop;
        this.lat = lat;
        this.lng = lng;
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

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
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

    public String getpShop() {
        return pShop;
    }

    public void setpShop(String pShop) {
        this.pShop = pShop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}