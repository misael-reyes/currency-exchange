package com.example.currencyexchangeapp.data.network;

import com.example.currencyexchangeapp.data.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * interfaz donde tendremos todas las peticiones a la
 * api de currency
 */
public interface ApiClient {

    @GET("convert")
    Call<ApiResponse> convertCurrency(
            @Query("api_key") String api_key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") Double amount,
            @Query("format") String format
    );

    /*@GET("convert")
    Call<ApiResponse> convertCurrency(@Body ApiBody body);*/
}
