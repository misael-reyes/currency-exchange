package com.example.currencyexchangeapp.ui.home;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.currencyexchangeapp.data.model.CurrencyConversion;
import com.example.currencyexchangeapp.data.repository.CurrencyConversionRepository;
import com.example.currencyexchangeapp.utils.Constants;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

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

    private MutableLiveData<CurrencyConversion> conversion;
    public LiveData<CurrencyConversion> getConversion() {
        if (conversion == null) conversion = new MutableLiveData<>();
        return conversion;
    }

    public void setFlag1(ExtendedCurrency flag1) {
        this.flag1.setValue(flag1);
    }

    public void setFlag2(ExtendedCurrency flag2) {
        this.flag2.setValue(flag2);
    }

    public void converterCurrency(String from, String to, Double amount) {
        Call<String> call = conversionRepository.convertCurrencyFromApi(
                Constants.API_KEY,
                from,
                to,
                amount
        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // your code
                    //conversion.setValue(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject job = jsonObject.getJSONObject("rates");
                        JSONObject jsonObject1 = job.getJSONObject("USD");
                        String amount = jsonObject1.getString("rate_for_amount");
                        System.out.println("el monto es "+amount);

//                        JSONObject jsonObject = new JSONObject(response.body());
//                        JSONArray job = jsonObject.getJSONArray("rates");
//                        JSONObject jsonObject1 = job.getJSONObject(0);
//                        String amount = jsonObject1.getString("rate_for_amount");
//                        System.out.println("el monto es "+amount);
                    } catch (Exception e) {
                        System.out.println("hubo un error "+e.getMessage());
                    }
                    //Log.i("response", response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // error in the request
            }
        });
    }

}
