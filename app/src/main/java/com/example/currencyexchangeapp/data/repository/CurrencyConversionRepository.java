package com.example.currencyexchangeapp.data.repository;

import android.content.Context;

import com.example.currencyexchangeapp.data.database.AppDatabase;
import com.example.currencyexchangeapp.data.database.dao.RateDao;
import com.example.currencyexchangeapp.data.database.entities.RateEntity;
import com.example.currencyexchangeapp.data.model.ApiResponse;
import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.data.network.ApiClient;
import com.example.currencyexchangeapp.data.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;

public class CurrencyConversionRepository {

    private final ApiClient apiClient = RetrofitHelper.getRetrofit().create(ApiClient.class);

    private AppDatabase database;
    private static RateDao rateDao;

    private Context context;

    /**
     * ********************** PETICIONES A LA API CURRENCY *************
     */

    /*public Call<ApiResponse> convertCurrencyFromApi(ApiBody body) {
        return apiClient.convertCurrency(body);
    }*/

    public Call<ApiResponse> convertCurrencyFromApi(String api_key, String from, String to, Double amount, String format) {
        return apiClient.convertCurrency(api_key, from, to, amount, format);
    }

    public Call<ApiResponse> convertInAllCurrenciesFromApi(String api_key, String from, Double amount, String format) {
        return apiClient.convertInAllCurrencies(api_key, from, amount, format);
    }

    public List<Rate> getAllRatesFromLocal() {
        List<RateEntity> list =  rateDao.getAllRates();
        List<Rate> final_list = new ArrayList<>();
        for (RateEntity rate: list) {
            final_list.add(rate.toRateModel());
        }
        return final_list;
    }

    public void insertAllRatesInLocal(List<Rate> list) {
        for (Rate rate: list) {
            rateDao.insertRate(rate.toRateEntity());
        }
    }

    public void deleteAllRatesInLocal() {
        rateDao.deleteAllRates();
    }

    public void deleteRateInLocal(Rate rate) {
        rateDao.deleteRate(rate.toRateEntity());
    }

    // primero tenemos que iniciarlizar estas variables antes de ejecutar los metodos anteriores
    public void initDatabase(Context context) {
        this.context = context;
        database = AppDatabase.getInstance(context);
        rateDao = database.rateDao();
    }
}
