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

            }
        });
    }

}
