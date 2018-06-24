package com.android.foodmanagementsystem.mData;

/**
 * Created by Techbuzz22 on 04-10-2017.
 */

public class Item {
    private String id;
    private String name;
    private String qty;
    private String price;
    private String higgintime;
    private String date;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHiggintime() {
        return higgintime;
    }

    public void setHiggintime(String higgintime) {
        this.higgintime = higgintime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
