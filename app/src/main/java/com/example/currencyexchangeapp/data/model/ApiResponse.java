package com.example.currencyexchangeapp.data.model;

import java.util.Map;

public class ApiResponse {

    private String base_currency_code;
    private String base_currency_name;
    private String amount;
    private String updated_date;
    private Map<String, Rate> rates;
    private String status;

    public ApiResponse(String base_currency_code, String base_currency_name, String amount, String updated_date, Map<String, Rate> rates, String status) {
        this.base_currency_code = base_currency_code;
        this.base_currency_name = base_currency_name;
        this.amount = amount;
        this.updated_date = updated_date;
        this.rates = rates;
        this.status = status;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public Map<String, Rate> getRates() {
        return rates;
    }

    public void setRates(Map<String, Rate> rates) {
        this.rates = rates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
