package com.example.currencyexchangeapp.data.model;

import java.util.List;

/**
 * Response object
 */

public class CurrencyConversion {

    // properties

    private String status, updated_date, base_currency_code, base_currency_name;
    private Double amount;
    private Rate rates;

    // setters and getters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getBase_currency_code() {
        return base_currency_code;
    }

    public void setBase_currency_code(String base_currency_code) {
        this.base_currency_code = base_currency_code;
    }

    public String getBase_currency_name() {
        return base_currency_name;
    }

    public void setBase_currency_name(String base_currency_name) {
        this.base_currency_name = base_currency_name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Rate getRates() {
        return rates;
    }

    public void setRates(Rate rates) {
        this.rates = rates;
    }
}
