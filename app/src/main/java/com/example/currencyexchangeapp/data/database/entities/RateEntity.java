package com.example.currencyexchangeapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.currencyexchangeapp.data.model.Rate;

/**
 * entidad para guardar datos de un rate en la base de datos local
 */

@Entity(tableName = "rates")
public class RateEntity {

    // atributos de la entidad
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String code;
    private String currency_name;
    private int flag;
    private String rate_for_amount;

    // constructor

    public RateEntity(@NonNull String code, String currency_name, int flag, String rate_for_amount) {
        this.currency_name = currency_name;
        this.code = code;
        this.flag = flag;
        this.rate_for_amount = rate_for_amount;
    }

    // metodos setters and getters para acceder a los datos

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getRate_for_amount() {
        return rate_for_amount;
    }

    public void setRate_for_amount(String rate_for_amount) {
        this.rate_for_amount = rate_for_amount;
    }

    /**
     * metodo para convertir un objeto RateEntity a un objeto Rate
     * @return
     */
    public Rate toRateModel() {
        return new Rate(currency_name, "", rate_for_amount, code, flag);
    }
}
