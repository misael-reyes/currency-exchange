package com.example.currencyexchangeapp.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.currencyexchangeapp.data.model.CurrencyConversion;
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

    public void setFlag1(ExtendedCurrency flag1) {
        this.flag1.setValue(flag1);
    }

    public void setFlag2(ExtendedCurrency flag2) {
        this.flag2.setValue(flag2);
    }

    public void converterCurrency(String from, String to, Double amount) {
        Call<CurrencyConversion>  call = conversionRepository.convertCurrencyFromApi(
                Constants.API_KEY,
                from,
                to,
                amount
        );

        call.enqueue(new Callback<CurrencyConversion>() {
            @Override
            public void onResponse(Call<CurrencyConversion> call, Response<CurrencyConversion> response) {
                //
            }

            @Override
            public void onFailure(Call<CurrencyConversion> call, Throwable t) {
                //
            }
        });
    }

}
