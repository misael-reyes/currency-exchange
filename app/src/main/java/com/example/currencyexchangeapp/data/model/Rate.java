package com.example.currencyexchangeapp.data.model;

public class Rate {

    // properties

    private String currency_name;
    private Double rate, rate_for_amount;

    // setters and getters

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate_for_amount() {
        return rate_for_amount;
    }

    public void setRate_for_amount(Double rate_for_amount) {
        this.rate_for_amount = rate_for_amount;
    }
}
