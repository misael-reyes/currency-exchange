package com.example.currencyexchangeapp.data.model;

public class Rate {

    public String currency_name;
    public String rate;
    public String rate_for_amount;
    private int flag;
    private String code;

    public Rate(String currency_name, String rate, String rate_for_amount) {
        this.currency_name = currency_name;
        this.rate = rate;
        this.rate_for_amount = rate_for_amount;
    }

    public Rate(String currency_name, String rate, String rate_for_amount, String code, int flag) {
        this.currency_name = currency_name;
        this.rate = rate;
        this.rate_for_amount = rate_for_amount;
        this.code = code;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate_for_amount() {
        return rate_for_amount;
    }

    public void setRate_for_amount(String rate_for_amount) {
        this.rate_for_amount = rate_for_amount;
    }
}
