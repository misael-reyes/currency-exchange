package com.example.currencyexchangeapp.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * clase view model para la activity HomeActivity
 */
public class HomeViewModel extends ViewModel {

    /**
     * ejemplo de como usar live data dentro de un view model
     */

    private int contador = 0;

    private MutableLiveData<Integer> mausan;

    public LiveData<Integer> getMausan() {
        if (mausan == null) {
            mausan = new MutableLiveData<>();
        }
        return mausan;
    }

    public void agregar() {
        mausan.setValue(contador);
        contador ++;
    }

}
