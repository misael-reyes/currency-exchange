package com.example.currencyexchangeapp.data.repository;

import com.example.currencyexchangeapp.data.model.CurrencyConversion;
import com.example.currencyexchangeapp.data.network.ApiClient;
import com.example.currencyexchangeapp.data.network.RetrofitHelper;

import retrofit2.Call;

public class CurrencyConversionRepository {

    private final ApiClient apiClient = RetrofitHelper.getRetrofit().create(ApiClient.class);

    /**
     * ********************** PETICIONES A LA API CURRENCY *************
     */

    public Call<String> convertCurrencyFromApi(String api_key, String from, String to, Double amount) {
        return apiClient.convertCurrency(api_key, from, to, amount);
    }
}
