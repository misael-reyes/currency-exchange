package com.example.currencyexchangeapp.data.network;

import com.example.currencyexchangeapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
