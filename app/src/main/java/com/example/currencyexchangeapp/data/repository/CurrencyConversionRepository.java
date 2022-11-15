package com.example.currencyexchangeapp.data.repository;

import com.example.currencyexchangeapp.data.model.ApiResponse;
import com.example.currencyexchangeapp.data.network.ApiClient;
import com.example.currencyexchangeapp.data.network.RetrofitHelper;

import retrofit2.Call;

public class CurrencyConversionRepository {

    private final ApiClient apiClient = RetrofitHelper.getRetrofit().create(ApiClient.class);

    /**
     * ********************** PETICIONES A LA API CURRENCY *************
     */

    /*public Call<ApiResponse> convertCurrencyFromApi(ApiBody body) {
        return apiClient.convertCurrency(body);
    }*/

    public Call<ApiResponse> convertCurrencyFromApi(String api_key, String from, String to, Double amount, String format) {
        return apiClient.convertCurrency(api_key, from, to, amount, format);
    }
}
