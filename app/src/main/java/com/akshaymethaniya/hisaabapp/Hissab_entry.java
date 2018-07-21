package com.akshaymethaniya.hisaabapp;

/**
 * Created by FOR ORACLE on 03-05-2018.
 */
public class Hissab_entry {
    private String name;
    private String desc;
    private int type;
    private int amount;
    private String date;
    private int status;

    public Hissab_entry(String desc, String name, int type, int amount,String date,int status) {
        this.desc = desc;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.date=date;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
