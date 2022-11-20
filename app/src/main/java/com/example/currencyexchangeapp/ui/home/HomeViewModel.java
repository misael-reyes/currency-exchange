package com.example.currencyexchangeapp.ui.home;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.currencyexchangeapp.data.model.ApiResponse;
import com.example.currencyexchangeapp.data.model.CurrencyConversion;
import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.data.repository.CurrencyConversionRepository;
import com.example.currencyexchangeapp.utils.Constants;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * clase view model para la activity HomeActivity
 */
public class HomeViewModel extends ViewModel {

    private CurrencyConversionRepository conversionRepository;

    public HomeViewModel() {
        conversionRepository = new CurrencyConversionRepository();
    }

    private MutableLiveData<ExtendedCurrency> flag1;

    public LiveData<ExtendedCurrency> getFlag1() {
        if (flag1 == null) flag1 = new MutableLiveData<>();
        return flag1;
    }

    private MutableLiveData<ExtendedCurrency> flag2;

    public LiveData<ExtendedCurrency> getFlag2() {
        if (flag2 == null) flag2 = new MutableLiveData<>();
        return flag2;
    }

    private MutableLiveData<Rate> rateC;

    public LiveData<Rate> getRateC() {
        if (rateC == null)
            rateC = new MutableLiveData<>();
        return rateC;
    }

    // objeto para recibir la listas de las tarifas que debemos mostrar en el recycler view
    private MutableLiveData<List<Rate>> rates;

    public LiveData<List<Rate>> getRates() {
        if (rates == null)
            rates = new MutableLiveData<>();
        return rates;
    }

    /*private MutableLiveData<CurrencyConversion> conversion;
    public LiveData<CurrencyConversion> getConversion() {
        if (conversion == null) conversion = new MutableLiveData<>();
        return conversion;
    }*/

    public void setFlag1(ExtendedCurrency flag1) {
        this.flag1.setValue(flag1);
    }

    public void setFlag2(ExtendedCurrency flag2) {
        this.flag2.setValue(flag2);
    }

    public void setRateC(Rate rateC) {
        this.rateC.setValue(rateC);
    }

    public void converterCurrency(String from, String to, Double amount, String format) {
        //ApiBody body = new ApiBody(from, to, amount, "json");

        Call<ApiResponse> call = conversionRepository.convertCurrencyFromApi(
                Constants.API_KEY,
                from,
                to,
                amount,
                format
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Rate ratas = (Rate) response.body().getRates().values().toArray()[0];
                    Log.d("AMOUNT", "Amount " + ratas.getRate_for_amount());
                    setRateC(ratas);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("errorinrequest", "lanzo este este error: " + t.getMessage());
            }
        });
    }

    /**
     * convertimos el monto de la divisa fuente a todas las demás divisas
     *
     * @param from
     * @param amount
     * @param format
     */
    public void getAllRates(String from, String to, Double amount, String format, Context context) {

        Call<ApiResponse> call = conversionRepository.convertInAllCurrenciesFromApi(
                Constants.API_KEY,
                from,
                amount,
                format
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {

                    ArrayList<Rate> allRates = new ArrayList<>();
                    ArrayList<Rate> finalRates = new ArrayList<>();
                    List<ExtendedCurrency> currencies = ExtendedCurrency.getAllCurrencies();
                    /**
                     * antes de mandar la lista al recycler view, primero tenemos que añadirles el
                     * id de las banderas de las divisas que estan dentro de la lista
                     * y además tenemos que quitar aquellas divisas que nuestro picker no soporta
                     */

                    for (int i = 0; i < currencies.size(); i++) {
                        String code = currencies.get(i).getCode();
                        assert response.body() != null;
                        Rate rate_list = response.body().getRates().get(code);
                        if (rate_list != null) {
                            allRates.add(new Rate(currencies.get(i).getName(), rate_list.getRate(), rate_list.getRate_for_amount(), currencies.get(i).getCode(), currencies.get(i).getFlag()));
                        }
                    }

                    /**
                     * otro filtro que tenemos qeu agregar es que solo se deben mostrar los rates que el usuario haya
                     * seleccionado anteriormente, es decir, como su historial de seleccion
                     *
                     * al ser un historial, tenemos que ver donde guardar esos datos, puede ser con sqlite
                     */

                    // prrimero inicializamos los parametros para usar la base de datos local
                    conversionRepository.initDatabase(context);

                    for (Rate rate : allRates) {
                        if (rate.getCode().equalsIgnoreCase(from))
                            finalRates.add(rate);
                        else if (rate.getCode().equalsIgnoreCase(to))
                            finalRates.add(rate);
                    }
                    // insertamos a from y to a la BD
                    conversionRepository.insertAllRatesInLocal(finalRates);

                    List<Rate> rates_local = conversionRepository.getAllRatesFromLocal();

                    for (Rate rate: rates_local) {
                        assert response.body() != null;
                        rate.setRate_for_amount(Objects.requireNonNull(response.body().getRates().get(rate.getCode())).getRate_for_amount());
                    }

                    rates.setValue(rates_local);

                } else {
                    Log.i("errorinrequest", "response no fue satisfactoria");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("errorinrequest", "lanzo este este error: " + t.getMessage());
            }
        });
    }
}
