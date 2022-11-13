package com.example.currencyexchangeapp.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mynameismidori.currencypicker.ExtendedCurrency;

/**
 * clase view model para la activity HomeActivity
 */
public class HomeViewModel extends ViewModel {

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

    public void revertCountry(ExtendedCurrency flag1, ExtendedCurrency flag2) {
        this.flag1.setValue(flag1);
        this.flag2.setValue(flag2);
    }
}
