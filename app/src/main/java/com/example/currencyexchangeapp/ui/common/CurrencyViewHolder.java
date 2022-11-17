package com.example.currencyexchangeapp.ui.common;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.databinding.ItemCurrencyBinding;

import kotlin.Unit;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    private ItemCurrencyBinding binding;

    public CurrencyViewHolder(View view){
        super(view);
        // inicializamos el binding para poder acceder a los elementos de nuestro item
        binding = ItemCurrencyBinding.bind(view);
    }

    /**
     * método para pintar el item en nuestro recycler view
     */
    @SuppressLint("ResourceType")
    public void render(Rate rate) {
        binding.ivFlag.setImageResource(rate.getFlag());
        binding.tvCurrency.setText(rate.currency_name);
        binding.tvMount.setText(rate.rate_for_amount);
    }
}
