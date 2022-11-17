package com.example.currencyexchangeapp.ui.home;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.currencyexchangeapp.data.model.ApiResponse;
import com.example.currencyexchangeapp.data.model.CurrencyConversion;
import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.data.repository.CurrencyConversionRepository;
import com.example.currencyexchangeapp.utils.Constants;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.util.ArrayList;
import java.util.List;

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
    public LiveData<Rate> getRateC(){
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

    public void setRateC(Rate rateC){
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
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Rate ratas = (Rate) response.body().getRates().values().toArray()[0];
                    Log.d("AMOUNT", "Amount " + ratas.getRate_for_amount());
                    setRateC(ratas);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("errorinrequest", "lanzo este este error: "+t.getMessage());
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
    public void getAllRates(String from, Double amount, String format) {

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

                    ArrayList<Rate> finalRates = new ArrayList<>();
                    List<ExtendedCurrency> currencies = ExtendedCurrency.getAllCurrencies();
                    /**
                     * antes de mandar la lista al recycler view, primero tenemos que añadirles el
                     * id de las banderas de las divisas que estan dentro de la lista
                     * y además tenemos que quitar aquellas divisas que nuestro picker no soporta
                     */

                    for (int i = 0; i < currencies.size(); i ++) {
                        String code = currencies.get(i).getCode();
                        assert response.body() != null;
                        Rate rate_list = response.body().getRates().get(code);
                        if (rate_list != null) {
                            finalRates.add(new Rate(currencies.get(i).getName(), rate_list.getRate(), rate_list.getRate_for_amount(), currencies.get(i).getCode(), currencies.get(i).getFlag()));
                        }
                    }
                    rates.setValue(finalRates);
                } else {
                    Log.i("errorinrequest", "response no fue satisfactoria");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("errorinrequest", "lanzo este este error: "+t.getMessage());
            }
        });
    }
}
