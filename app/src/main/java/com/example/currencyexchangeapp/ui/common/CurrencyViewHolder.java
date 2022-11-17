package com.example.currencyexchangeapp.ui.common;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyexchangeapp.data.model.Rate;
import com.example.currencyexchangeapp.databinding.ItemCurrencyBinding;

import java.util.function.Function;

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
    public void render(Rate rate, Function<Rate, Void> onClickListener) {
        binding.ivFlag.setImageResource(rate.getFlag());
        binding.tvCurrency.setText(rate.currency_name);
        binding.tvMount.setText(rate.rate_for_amount);

        // asignamos evento al item del recycler view
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    onClickListener.apply(rate);
                }
            }
        });
    }
}
